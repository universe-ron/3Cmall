package com.willy.malltest.service.impl;

import com.willy.malltest.dto.UserDto;
import com.willy.malltest.model.ThirdParty;
import com.willy.malltest.model.User;
import com.willy.malltest.repository.ThirdPartyRepository;
import com.willy.malltest.repository.UsersRepository;
import com.willy.malltest.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UsersRepository userRepository;

    @Autowired
    private ThirdPartyRepository thirdPartyRepository;

    @Autowired
    private PasswordEncoder pwdEncoder;

    // 加密後存資料庫
    @Override
    @Transactional
    public User addUsers(User users)
    {
        String encodedPwd = pwdEncoder.encode(users.getPassword()); // 加密
        users.setPassword(encodedPwd);
        return userRepository.save(users);
    }

    @Override
    @Transactional
    public UserDto addUsersOAuth2(String id, String name, String email, String providerName) {
        User user = new User();
        user.setUserName(name);
        user.setEmail(email);
        user.setAuthentication(2);
        Date today = new Date();
        user.setRegisterDate(today);
        user.setLastLoginTime(today);

        ThirdParty thirdParty = new ThirdParty();
        thirdParty.setProviderId(id);
        thirdParty.setProviderName(providerName);
        thirdParty.setUser(user);

        userRepository.save(user);
        thirdPartyRepository.save(thirdParty);

        User dbUser = userRepository.findByEmail(email);
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(dbUser,userDto);
        return userDto;
    }

    // check user exist
    @Override
    public boolean checkIfUsernameExist(String email) {
        User dbUser = userRepository.findByEmail(email);
        return dbUser != null; //已存在
    }
    // check login
    @Override
    public UserDto login(String email, String password)
    {
        User dbUser = userRepository.findByEmail(email);

        if(dbUser!=null)
        {
            // 你要比對的字串, 資料庫裡面的字串
            if (pwdEncoder.matches(password, dbUser.getPassword()))
            {
                UserDto userDto = new UserDto();
                BeanUtils.copyProperties(dbUser,userDto);

                return userDto;
            }
        }

        return null;
    }
    @Override
    public UserDto loginOAuth2(String email){
        User dbUser = userRepository.findByEmail(email);
        updateLastloginTime(dbUser.getUserId());
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(dbUser,userDto);
        return userDto;
    }



    @Override
    public User updateLastloginTime(Long id) {
        User user = userRepository.findByUserId(id);

        Date currentDate = new Date();
        user.setLastLoginTime(currentDate);
        userRepository.save(user);
        return user;
    }

    @Override
    @Transactional
    public UserDto addAdmin(String id, String name, String email) {
        User user = new User();
        user.setUserName(name);
        user.setEmail(email);
        user.setAuthentication(0);
        Date today = new Date();
        user.setRegisterDate(today);
        user.setLastLoginTime(today);

        ThirdParty thirdParty = new ThirdParty();
        thirdParty.setProviderId(id);
        thirdParty.setProviderName(name);
        thirdParty.setUser(user);

        userRepository.save(user);
        thirdPartyRepository.save(thirdParty);

        User dbUser = userRepository.findByEmail(email);
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(dbUser,userDto);
        return userDto;
    }

    public User banUser(Long id) {
        User user = userRepository.findByUserId(id);
        user.setAuthentication(3);
        userRepository.save(user);
        return user;
    }

    public User unbanUser(Long id) {
        User user = userRepository.findByUserId(id);
        user.setAuthentication(2);
        userRepository.save(user);
        return user;
    }

    public String deleteUser(Long id) {
        userRepository.deleteById(id);
        return "delete success NO." + id+" User";
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        List<User> normalUsers = userRepository.findByAuthentication(2);
        List<User> bannedUsers = userRepository.findByAuthentication(3);

        users.addAll(normalUsers);
        users.addAll(bannedUsers);

        return users;
    }


    public String findEmailById(Long id){
        User user = userRepository.findByUserId(id);
        return user.getEmail();
    }

    public String findNameById(Long id){
        User user = userRepository.findByUserId(id);
        return user.getUserName();
    }

}
