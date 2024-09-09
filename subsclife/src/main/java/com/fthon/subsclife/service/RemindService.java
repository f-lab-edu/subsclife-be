package com.fthon.subsclife.service;

import com.fthon.subsclife.dto.RemindDto;
import com.fthon.subsclife.dto.mapper.RemindMapper;
import com.fthon.subsclife.entity.Remind;
import com.fthon.subsclife.repository.RemindRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RemindService {

    private final RemindRepository remindRepository;

    private final RemindMapper remindMapper;


    @Transactional
    public void create(RemindDto.RemindRequest remindRequest) {

            Remind remind = remindMapper.toEntity(remindRequest);

            remindRepository.save(remind);

    }
}
