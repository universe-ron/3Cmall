package com.willy.malltest.repository;

import com.willy.malltest.model.Category;
import com.willy.malltest.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, String> {

    @Query("SELECT p FROM Product p INNER JOIN p.category c WHERE c.categoryId = :Id")
    List<Product> findByCategoryCategoryId(String Id);

    List<Product> findProductsByCategory(Category category);


    @Query("SELECT p FROM Product p WHERE p.productId = :productId")
    Product findProductsByProductId(String productId);

    @Query("SELECT p FROM Product p WHERE p.salesStatus = 1 AND p.category.categoryId = :categoryId")
    Page<Product> findByCategoryCategoryId(String categoryId, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE REPLACE(p.productName, ' ', '') LIKE %:productName% AND p.salesStatus = 1")
    Page<Product> findByProductName(String productName, Pageable pageable);

    @Query("SELECT p FROM Product p INNER JOIN p.category c WHERE c.categoryId = :categoryId AND p.salesStatus = 1 AND REPLACE(p.productName, ' ', '') LIKE %:productName%")
    Page<Product> findByCategoryAndProductName(@Param("categoryId") String categoryId, @Param("productName") String productName, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.salesStatus = 1")
    Page<Product> findSalesStatusOnSale(Pageable pageable);

    Product findByProductId(String productId);
}
