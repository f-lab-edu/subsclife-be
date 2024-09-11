package com.fthon.subsclife.service;

import com.fthon.subsclife.dto.RemindDto;
import com.fthon.subsclife.dto.mapper.RemindMapper;
import com.fthon.subsclife.entity.Remind;
import com.fthon.subsclife.entity.Subscribe;
import com.fthon.subsclife.entity.Task;
import com.fthon.subsclife.entity.User;
import com.fthon.subsclife.repository.RemindRepository;
import com.fthon.subsclife.repository.SubscribeRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class RemindService {

    private final RemindRepository remindRepository;

    private final RemindMapper remindMapper;

    private final LoginService loginService;

    private final TaskService taskService;

    //타 Service를 사용하면 결합도 관련 까리한데...?
    private final UserService userService;

    //타 Repository 사용해도될까?
    private final SubscribeRepository subscribeRepository;

    @Transactional
    public void create(RemindDto.SaveRequest saveRequest) {

        //login 정보 가져오기
        Long userId = loginService.getLoginUserId();

        //User 정보 가져오기
        User user = userService.findUserByIdWithSubscribesAndTask(userId);

        //Task 정보 가져오기
        Task task = taskService.findTaskById(saveRequest.getTaskId());

        //Remind 정보 생성
        Remind remind = remindMapper.toEntity(saveRequest, task, loginService.getLoginUser());

        //회고작성한 Task구독 취소
        Subscribe subscribe = user.unsubscribe(task.getId());
        subscribeRepository.delete(subscribe);

        remindRepository.save(remind);

    }

    @Transactional(readOnly = true)
    public RemindDto.DetailResponse getRemindDetail(Long remindId) {


        Remind remind = remindRepository.findById(remindId)
                .orElseThrow(() -> new NoSuchElementException("찾으려는 회고가 없습니다."));


        Task task = taskService.findTaskByIdWithSubscribes(remind.getTask().getId());


        RemindDto.DetailResponse remindDetailResponse = remindMapper.toDetailResponse(remind, task);


        return remindDetailResponse;
    }
}
