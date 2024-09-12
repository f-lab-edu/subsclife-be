package com.fthon.subsclife.service;

import com.fthon.subsclife.entity.User;
import com.fthon.subsclife.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    //TODO 중복되는 메소드 추출하기

    @Transactional(readOnly = true)
    public User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("사용자를 찾을 수 없습니다."));
    }

    @Transactional(readOnly = true)
    public User findUserByIdWithSubscribes(Long id) {
        return userRepository.findByIdWithSubscribes(id)
                .orElseThrow(() -> new NoSuchElementException("사용자를 찾을 수 없습니다."));
    }

    @Transactional(readOnly = true)
    public User findUserByIdWithSubscribesAndTask(Long id) {
        return userRepository.findByIdWithSubscribesAndTask(id)
                .orElseThrow(() -> new NoSuchElementException("사용자를 찾을 수 없습니다."));
    }

    @Transactional(readOnly = true)
    public List<User> findUserByTaskIdWhoReminded(Long taskId) {
        return userRepository.findUsersWhoRemindedByTaskId(taskId);
    }


}
