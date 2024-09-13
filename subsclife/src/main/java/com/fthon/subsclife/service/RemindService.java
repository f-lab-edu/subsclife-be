package com.fthon.subsclife.service;

import com.fthon.subsclife.dto.PagedItem;
import com.fthon.subsclife.dto.mapper.RemindMapper;
import com.fthon.subsclife.entity.Remind;
import com.fthon.subsclife.entity.Task;
import com.fthon.subsclife.entity.User;
import com.fthon.subsclife.exception.DuplicateRequestException;
import com.fthon.subsclife.repository.RemindRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

import static com.fthon.subsclife.dto.RemindDto.*;

@Service
@RequiredArgsConstructor
public class RemindService {

    private final RemindRepository remindRepository;

    private final RemindMapper remindMapper;

    private final LoginService loginService;

    private final TaskService taskService;

    private final UserService userService;
    private final SubscribeService subscribeService;


    @Transactional
    public void create(SaveRequest saveRequest) {

        //login 정보 가져오기
        Long userId = loginService.getLoginUserId();

        //User 정보 가져오기
        User user = userService.findUserByIdWithSubscribes(userId);

        //Task 정보 가져오기
        Task task = taskService.findTaskById(saveRequest.getTaskId());

        //Remind 정보 생성
        Remind remind = remindMapper.toEntity(saveRequest, task, loginService.getLoginUser());

        if (isWrittenRemind(user, task)) {
            throw new DuplicateRequestException("이미 회고가 작성된 태스크입니다.");
        }

        subscribeService.unsubscribeTask(userId, task.getId());
        remindRepository.save(remind);
    }

    private static boolean isWrittenRemind(User user, Task task) {
        return user.getReminds().stream()
                .anyMatch(r -> r.getTask().getId().equals(task.getId()));
    }

    @Transactional(readOnly = true)
    public DetailResponse getRemindDetail(Long remindId) {


        Remind remind = remindRepository.findById(remindId)
                .orElseThrow(() -> new NoSuchElementException("찾으려는 회고가 없습니다."));


        Task task = taskService.findTaskByIdWithSubscribes(remind.getTask().getId());


        DetailResponse remindDetailResponse = remindMapper.toDetailResponse(remind, task);


        return remindDetailResponse;
    }


    @Transactional(readOnly = true)
    public PagedItem<ListResponse> getRemindList(Cursor cursor) {
        Long userId = loginService.getLoginUserId();

        PagedItem<Remind> reminds = remindRepository.searchRemindList(userId, cursor);

        List<Long> taskIds = reminds.getItems().stream()
                .map(r -> r.getTask().getId())
                .toList();

        List<Task> tasks = taskService.findTasksByIdsWithReminds(taskIds);

        List<ListResponse> remindList = reminds.getItems()
                .stream()
                .map(r -> remindMapper.toListResponse(r, getRemindCountByTaskId(tasks, r.getTask().getId())))
                .toList();

        return new PagedItem<>(remindList, reminds.getHasNext());
    }




    private Integer getRemindCountByTaskId(List<Task> tasks, Long taskId) {
        for (Task task : tasks) {
            if (task.getId().equals(taskId)) {
                return task.getReminds().size();
            }
        }
        return 0;
    }
}
