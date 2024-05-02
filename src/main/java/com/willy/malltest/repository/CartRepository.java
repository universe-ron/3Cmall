package com.willy.malltest.repository;

import com.willy.malltest.model.CartItems;
import com.willy.malltest.model.Product;
import com.willy.malltest.model.ProductSpec;
import com.willy.malltest.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<CartItems,Integer> {
    public List<CartItems> findCartByUser(User userId);
    public CartItems findByUserAndProductSpec(User user, ProductSpec spec);
    @Transactional
    void deleteByUser(User user);
}
