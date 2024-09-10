package com.fthon.subsclife.service;


import com.fthon.subsclife.entity.User;
import com.fthon.subsclife.exception.AuthenticationException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SimpleLoginService implements LoginService {

    private final HttpServletRequest request;
    private final UserService userService;

    /**
     * header 정보를 읽어 로그인한 사용자의 ID를 반환
     */
    @Override
    public Long getLoginUserId() {
        String userInfo = request.getHeader("user-id");

        if (userInfo == null) {
            throw new AuthenticationException("사용자 정보가 없습니다.");
        }

        try {
            return Long.parseLong(userInfo);
        } catch (NumberFormatException e) {
            throw new AuthenticationException("사용자 정보 값이 올바르지 않습니다.");
        }
    }

    /**
     * 단순 사용자 정보만 필요할 경우 사용
     */
    @Override
    public User getLoginUser() {
        return userService.findUserById(getLoginUserId());
    }
}
