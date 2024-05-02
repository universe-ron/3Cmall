package com.willy.malltest.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.willy.malltest.dto.UserDto;
import com.willy.malltest.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Service
public class SessionServiceImpl implements SessionService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public SessionServiceImpl(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void saveSession(String key, UserDto userDto) {

        redisTemplate.opsForValue().set(key, userDto, 1440, TimeUnit.MINUTES);
    }

    public UserDto getSession(String key) {

        Object sessionData = redisTemplate.opsForValue().get(key);
        if (sessionData != null) {
            return (UserDto) sessionData;
        } else {
            return null;
        }
    }
    public void deleteSession(String key) {
        redisTemplate.delete(key);
    }

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static byte[] serialize(UserDto user) throws IOException {
        return objectMapper.writeValueAsBytes(user);
    }

    public static UserDto deserialize(byte[] data) throws IOException {
        return objectMapper.readValue(data, UserDto.class);
    }
}
