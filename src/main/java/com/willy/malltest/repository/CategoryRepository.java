package com.willy.malltest.repository;

import com.willy.malltest.model.CartItems;

import com.willy.malltest.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, String> {
    Category findByCategoryId(String categoryId);
}
