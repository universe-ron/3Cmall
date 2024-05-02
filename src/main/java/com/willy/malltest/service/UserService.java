package com.willy.malltest.service;

import com.willy.malltest.dto.UserDto;
import com.willy.malltest.model.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserService {
    User addUsers(User users);
    UserDto addUsersOAuth2(String id, String name, String email, String providerName);
    boolean checkIfUsernameExist(String email);
    UserDto login(String email, String password);
    UserDto loginOAuth2(String email);
    User updateLastloginTime(Long id);

    @Transactional
    UserDto addAdmin(String id, String name, String email);

    @Transactional
    User banUser(Long id);

    String deleteUser(Long id);

    User unbanUser(Long id);

    List<User> getAllUsers();

    String findEmailById(Long id);

    String findNameById(Long id);
}
