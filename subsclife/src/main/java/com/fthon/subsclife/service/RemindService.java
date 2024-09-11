package com.fthon.subsclife.service;

import com.fthon.subsclife.dto.PagedItem;
import com.fthon.subsclife.dto.mapper.RemindMapper;
import com.fthon.subsclife.entity.Remind;
import com.fthon.subsclife.entity.Task;
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


    @Transactional
    public void create(SaveRequest saveRequest) {


            Task task = taskService.findTaskById(saveRequest.getTaskId());

            Remind remind = remindMapper.toEntity(saveRequest, task, loginService.getLoginUser());


            remindRepository.save(remind);

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

        List<ListResponse> remindList = reminds.getItems()
                .stream()
                .map(remindMapper::toListResponse)
                .toList();

        return new PagedItem<>(remindList, reminds.getHasNext());
    }

}
