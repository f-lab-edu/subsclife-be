package com.fthon.subsclife.service;

import com.fthon.subsclife.dto.RemindDto;
import com.fthon.subsclife.dto.mapper.RemindMapper;
import com.fthon.subsclife.entity.Remind;
import com.fthon.subsclife.entity.Task;
import com.fthon.subsclife.repository.RemindRepository;
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


    @Transactional
    public void create(RemindDto.SaveRequest saveRequest) {


            Task task = taskService.findTaskById(saveRequest.getTaskId());

            Remind remind = remindMapper.toEntity(saveRequest, task, loginService.getLoginUser());


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
