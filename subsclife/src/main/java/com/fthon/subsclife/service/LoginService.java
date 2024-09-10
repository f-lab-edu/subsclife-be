package com.fthon.subsclife.service;

import com.fthon.subsclife.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface LoginService {

    Long getLoginUserId();

    User getLoginUser();
}
