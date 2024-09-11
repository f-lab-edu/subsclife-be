package com.fthon.subsclife.controller;

import com.fthon.subsclife.dto.RemindDto;
import com.fthon.subsclife.dto.mapper.RemindMapper;
import com.fthon.subsclife.entity.Remind;
import com.fthon.subsclife.service.RemindService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/reminds")
@RequiredArgsConstructor
public class RemindController {

        private final RemindService remindService;

        //1. 회고 생성
        @PostMapping
        public ResponseEntity<HttpStatus> create(@RequestBody @Valid RemindDto.SaveRequest SaveRequest) {
                remindService.create(SaveRequest);

                return ResponseEntity.status(HttpStatus.CREATED).build();

        }

        @GetMapping("/{remindId}")
        public ResponseEntity<RemindDto.DetailResponse> detail(
                @RequestHeader("user-id") Long userId,
                @PathVariable("remindId") Long remindId) {

                return new ResponseEntity<>(remindService.getRemindDetail(remindId), HttpStatus.OK);

        }



}
