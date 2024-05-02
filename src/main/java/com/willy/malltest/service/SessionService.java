package com.willy.malltest.service;

import com.willy.malltest.dto.UserDto;

public interface SessionService {
    void saveSession(String key, UserDto userDto);
    UserDto getSession(String sessionId);
    void deleteSession(String key);
}
