package com.willy.malltest.controller;


import com.willy.malltest.dto.ProductDto;
import com.willy.malltest.model.Product;
import com.willy.malltest.model.ProductPhoto;
import com.willy.malltest.model.ProductSpec;
import com.willy.malltest.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(allowCredentials = "true", origins = {"http://localhost:5173", "http://localhost:3000", "http://127.0.0.1:5173"})
public class ProductController {

    @Autowired
    private ProductService productService;
    private Product product;


    @GetMapping("/products/getAllProducts")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/products/getProductByCategoryId")
    public List<Product> getOnlineProductByCategoryId(@RequestParam String categoryId) {
        List<Product> products = productService.getProductByCategoryId(categoryId);
        return productService.filterCheckProductSalesStatus(products);
    }

    @GetMapping("/products/getProductById")
    public Product getProductById(String productId) {
        return productService.findProductById(productId);
    }

    @PostMapping("/products/insertPhone")
    public Product insertPhone(@RequestBody Product p) {
        String categoryId = "A";
        return productService.insertProduct(p, categoryId);
    }

    @PostMapping("/products/insertMac")
    public Product insertMac(@RequestBody Product p) {
        String categoryId = "B";
        return productService.insertProduct(p, categoryId);
    }

    @PostMapping("/products/insertPad")
    public Product insertPad(@RequestBody Product p) {
        String categoryId = "C";
        return productService.insertProduct(p, categoryId);
    }

    @PutMapping("/products/updateProduct/{productId}")
    public Product updateProduct(@PathVariable String productId, @RequestBody Product updatedProduct) {
        return productService.updateProduct(updatedProduct, productId);
    }

    @GetMapping("/products/findProductByProductId")
    public Product findProductByProductId(@RequestParam String productId) {
        return productService.findProductByProductId(productId);
    }

    @GetMapping("/products/findProductByProductId1")
    public Product findProductByProductId1(@RequestParam String productId) {
        return productService.findProductByProductId(productId);
    }

    @GetMapping("/products/findProductSpecByProductId")
    public List<ProductSpec> findProductSpecByProductId(String productId) {
        return productService.findProductSpecByProductId(productId);
    }

    // 根據商品頁面取得商品Dto
    @GetMapping("/products/{pageNumber}")
    public Page<ProductDto> findProductByPage(@PathVariable Integer pageNumber) {
        return productService.findProductByPage(pageNumber);
    }

    //模糊查詢產品
    @GetMapping("/products/findFilterProductByPage/{pageNumber}")
    public Page<ProductDto> findFilterProductByPage(@PathVariable Integer pageNumber, @RequestParam String productName) {
        return productService.findFilterProductByPage(pageNumber, productName);
    }

    @GetMapping(path = "/product/photo/{id}", produces = "image/*")
    public byte[] findProductPhotoById(@PathVariable Integer id) {

        return productService.findProductPhotoById(id);
    }


    @GetMapping("/products/findProductsByCategoryId")   //搜尋不同category的商品用DTO
    public Page<ProductDto> findProductsByCategoryId(@RequestParam String categoryId, @RequestParam Integer pageNumber) {
        return productService.findProductsByCategoryId(categoryId, pageNumber);
    }

    @PostMapping("/products/insertProductPhoto")
    public String insertProductPhoto(@RequestParam String specId, @RequestBody MultipartFile file) {
        return productService.insertProductPhoto(specId, file);
    }

    // 獲取所有產品圖片的 Controller 端
    @GetMapping("/photos/image/{photoId}")
    public ResponseEntity<byte[]> showImage(@PathVariable Integer photoId) {
        return productService.showImage(photoId);
    }

    @PutMapping("/products/productSalesStatus")
    public Product productSalesStatus(@RequestParam String productId) {
        return productService.changeProductSalesStatus(productId);
    }
    @PostMapping("/products/insertProductSpec")
    public String insertProductSpec(@RequestBody ProductSpec productSpec) {
        productService.insertProductSpec(productSpec);
        String specId = productSpec.getSpecId();
        return "success insert productSpec "+specId;
    }

    @PutMapping("/products/updateProductSpec")
    public String updateProductSpec(@RequestParam String specId, @RequestParam Integer stockQuantity) {
      productService.changeStockQuantity(specId, stockQuantity);
      return "success update productSpec "+specId;
    }

    //用specId調用照片
    @GetMapping(path = "/productSpec/photo/{specId}", produces = "image/*")
    public byte[] findProductPhotoBySpecId(@PathVariable String specId) {

        return productService.findProductPhotoByProductSpecId(specId);
    }

    //模糊查詢分類產品名稱
    @GetMapping("/products/searchProduct/{pageNumber}")
    public Page<ProductDto> findFilterCategoryAndProductByPage(@PathVariable Integer pageNumber, @RequestParam String productName, @RequestParam String categoryId) {
        return productService.findFilterCategoryAndProductByPage(pageNumber, productName, categoryId);
    }

    @GetMapping("/products/findProductSpecBySpecId/{specId}")
    public ProductSpec findColorBySpecId(@PathVariable String specId) {
        return productService.findProductSpecBySpecId(specId);
    }

    @DeleteMapping("/products/deleteProduct")
    public String deleteProduct(@RequestParam String productId) {
        return  productService.deleteProduct(productId);

    }

    @DeleteMapping("/products/deleteProductSpec")
    public String deleteProductSpec(@RequestParam String specId) {
        return productService.deleteProductSpec(specId);
    }

    @PutMapping("/products/changeSpecPhoto")
    public String changeSpecPhoto(@RequestParam String specId, @RequestParam MultipartFile file) throws IOException {
        productService.updateSpecPhoto(specId, file);
        return "success change specPhoto";

    }

}