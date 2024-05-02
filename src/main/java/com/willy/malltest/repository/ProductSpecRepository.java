package com.willy.malltest.repository;


import com.willy.malltest.model.Product;
import com.willy.malltest.model.ProductSpec;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductSpecRepository extends JpaRepository<ProductSpec, String> {

  // 在 ProductSpecRepository 中添加方法
  @Query("SELECT p.product.productId FROM ProductSpec p WHERE p.specId = :specId")
  Optional<String> findProductIdBySpecId(String specId);

  List<ProductSpec> findProductSpecByProduct(Product product);

ProductSpec findProductSpecBySpecId(String specId);


}



