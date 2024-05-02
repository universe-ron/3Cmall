package com.willy.malltest.repository;


import com.willy.malltest.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UsersRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    User findByUserId(Long id);

    @Query("SELECT u FROM User u WHERE u.email = ?1 AND u.phone = ?2")
    User findByEmailAndPhone(String email, String phone);

    List<User> findByAuthentication(Integer authentication);

}
