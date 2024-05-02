package com.willy.malltest.repository;

import com.willy.malltest.model.ProductPhoto;
import com.willy.malltest.model.ProductSpec;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductPhotoRepository extends JpaRepository<ProductPhoto, Integer> {
    List<ProductPhoto> findByProductSpec(ProductSpec productSpec);
}
