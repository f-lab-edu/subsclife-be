package com.fthon.subsclife.controller;

import com.fthon.subsclife.dto.RemindDto;
import com.fthon.subsclife.dto.mapper.RemindMapper;
import com.fthon.subsclife.service.RemindService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/reminds")
@RequiredArgsConstructor
public class RemindController {

        private final RemindService remindService;

        //1. 회고 생성
        @PostMapping("/create")
        public ResponseEntity<HttpStatus> create(@RequestBody @Valid RemindDto.RemindRequest remindRequest) {
                remindService.create(remindRequest);

                return ResponseEntity.status(HttpStatus.CREATED).build();

        }



}
