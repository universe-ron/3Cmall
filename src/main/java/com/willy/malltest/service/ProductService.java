package com.willy.malltest.service;

import com.willy.malltest.dto.ProductDto;
import com.willy.malltest.model.Product;
import com.willy.malltest.model.ProductPhoto;
import com.willy.malltest.model.ProductSpec;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public interface ProductService {

   List<Product> getAllProducts() ;

   List<Product> getProductByCategoryId(String categoryId) ;

   Product findProductById(String productId) ;

   Product insertProduct(Product product,String categoryId) ;

   Product findProductByProductId(String productId);

   void saveProduct(Product product) ;

   List<ProductSpec> findProductSpecByProductId(String productId);
    // 根據頁碼搜尋商品和二次封裝
   Page<ProductDto> findProductByPage(Integer pageNumber) ;
    //模糊查詢產品名稱 用DTO封裝
   Page<ProductDto> findFilterProductByPage(Integer pageNumber, String productName) ;

    // 根據圖片ID搜尋商品圖片
   byte[] findProductPhotoById(Integer id) ;
    // 根據specId搜尋第一張商品圖片
   byte[] findProductPhotoByProductSpecId(String specId) ;

    // 根据产品类别ID分页搜索商品并进行二次封装
   Page<ProductDto> findProductsByCategoryId(String categoryId, Integer pageNumber) ;
   String  insertProductPhoto(String specId, MultipartFile file) ;
   List<ProductPhoto> findAllProductPhotos() ;

    ProductPhoto findProductPhotoByPhotoId(Integer photoId) ;

   ProductSpec findProductSpecBySpecId(String specId);
    //模糊查詢分類的產品名稱 用DTO封裝
   Page<ProductDto> findFilterCategoryAndProductByPage(Integer pageNumber, String productName, String categoryId) ;
   ProductSpec updateProductSpec(ProductSpec productSpec) ;

   String deleteProduct(String productId);

   String deleteProductSpec(String specId) ;
   void changeStockQuantity(String specId, int quantity);


   String updateSpecPhoto(String specId, MultipartFile file) throws IOException ;

  ProductSpec insertProductSpec(ProductSpec productSpec);

  List<Product> filterCheckProductSalesStatus(List<Product> products);

  Product updateProduct(Product p,String pId);

  ResponseEntity<byte[]> showImage(Integer photoId);

  Product changeProductSalesStatus(String productId);

  String changeProductSpecQuantity(String specId,Integer quantity);






}

