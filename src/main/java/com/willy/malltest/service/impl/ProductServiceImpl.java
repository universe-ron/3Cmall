package com.willy.malltest.service.impl;

import com.willy.malltest.dto.ProductDto;
import com.willy.malltest.model.Category;
import com.willy.malltest.model.Product;
import com.willy.malltest.model.ProductPhoto;
import com.willy.malltest.model.ProductSpec;
import com.willy.malltest.repository.CategoryRepository;
import com.willy.malltest.repository.ProductPhotoRepository;
import com.willy.malltest.repository.ProductRepository;
import com.willy.malltest.repository.ProductSpecRepository;
import com.willy.malltest.service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductSpecRepository productSpecRepository;
    @Autowired
    private ProductPhotoRepository productPhotoRepository;


    @Override
    public List<Product> getAllProducts() {

        return productRepository.findAll();

    }

    @Override
    public List<Product> getProductByCategoryId(String categoryId) {

        Category category = categoryRepository.findByCategoryId(categoryId);
        return productRepository.findProductsByCategory(category);
    }

    @Override
    public Product findProductById(String productId) {

        return productRepository.findById(productId).get();
    }

    @Override
    public Product insertProduct(Product p, String categoryId) {
        Category cat = categoryRepository.findByCategoryId(categoryId);
        p.setCategory(cat);
        System.out.println(new Date());
        p.setSetupDate(new Date());
        p.setModifyDate(new Date());
        p.setSalesStatus(1);

        return productRepository.save(p);
    }

    @Override
    public Product findProductByProductId(String productId) {

        return productRepository.findProductsByProductId(productId);
    }

    @Override
    public void saveProduct(Product product) {

        productRepository.save(product);
    }

    @Override
    public List<ProductSpec> findProductSpecByProductId(String productId) {

        Product product = productRepository.findProductsByProductId(productId);
        return productSpecRepository.findProductSpecByProduct(product);
    }

    @Override
    public Page<ProductDto> findProductByPage(Integer pageNumber) {
        Pageable page = PageRequest.of(pageNumber, 6);

        Page<Product> products = productRepository.findSalesStatusOnSale(page);

        Page<ProductDto> productDtos = products.map(p -> {
            ProductDto pt = new ProductDto();
            BeanUtils.copyProperties(p, pt);

            List<ProductSpec> productSpecs = p.getProductSpecs();
            if (productSpecs != null && !productSpecs.isEmpty()) {
                List<String> specIds = new ArrayList<>();
                productSpecs.forEach(spec -> specIds.add(spec.getSpecId()));
                pt.setSpecIds(specIds);

                List<ProductPhoto> productPhotos = productSpecs.get(0).getProductPhotos();
                if (productPhotos != null && productPhotos.size() != 0) {
                    ProductPhoto firstPhoto = productPhotos.get(0);
                    pt.setPhotoId(firstPhoto.getPhotoId());
                }
            }
            return pt;
        });
        return productDtos;
    }

    @Override
    public Page<ProductDto> findFilterProductByPage(Integer pageNumber, String productName) {
        Pageable page = PageRequest.of(pageNumber, 6);
        Page<Product> products = productRepository.findByProductName(productName, page);

        return products.map(p -> {
            ProductDto pt = new ProductDto();
            BeanUtils.copyProperties(p, pt);

            List<ProductSpec> productSpecs = p.getProductSpecs();    //加入productSpec
            if (productSpecs != null && !productSpecs.isEmpty()) {
                List<String> specIds = new ArrayList<>();
                productSpecs.forEach(spec -> specIds.add(spec.getSpecId()));
                pt.setSpecIds(specIds);

                List<ProductPhoto> productPhotos = productSpecs.get(0).getProductPhotos();
                if (productPhotos != null && !productPhotos.isEmpty()) {
                    ProductPhoto firstPhoto = productPhotos.get(0);
                    pt.setPhotoId(firstPhoto.getPhotoId());
                }
            }
            return pt;
        });
    }

    @Override
    public byte[] findProductPhotoById(Integer id) {
        ProductPhoto productPhoto = productPhotoRepository.findById(id).get();
        if (productPhoto == null) {
            return null;
        }
        return productPhoto.getPhotoFile();

    }

    @Override
    public byte[] findProductPhotoByProductSpecId(String specId) {
        ProductSpec productSpec = productSpecRepository.findById(specId).get();
        List<ProductPhoto> productPhotos = productPhotoRepository.findByProductSpec(productSpec);
        if (productPhotos == null) {
            return null;
        }
        return productPhotos.get(0).getPhotoFile();

    }

    @Override
    public Page<ProductDto> findProductsByCategoryId(String categoryId, Integer pageNumber) {
        Pageable page = PageRequest.of(pageNumber, 6);
        Page<Product> products = productRepository.findByCategoryCategoryId(categoryId, page); // 根据产品类别ID进行过滤

        Page<ProductDto> productDtos = products.map(p -> {
            ProductDto pt = new ProductDto();
            pt.setProductId(p.getProductId()); // 设置productId
            pt.setProductName(p.getProductName()); // 设置productName
            pt.setPrice(p.getPrice());
            pt.setProductDescription(p.getProductDescription());

            // 设置photoId
            List<ProductSpec> productSpecs = p.getProductSpecs();
            if (productSpecs != null && !productSpecs.isEmpty()) {
                List<String> specIds = new ArrayList<>();
                productSpecs.forEach(spec -> specIds.add(spec.getSpecId()));
                pt.setSpecIds(specIds);

                List<ProductPhoto> productPhotos = productSpecs.get(0).getProductPhotos();
                if (productPhotos != null && productPhotos.size() != 0) {
                    ProductPhoto firstPhoto = productPhotos.get(0);
                    pt.setPhotoId(firstPhoto.getPhotoId());
                }
            }
            return pt;
        });
        return productDtos;
    }

    @Override
    public String insertProductPhoto(String specId, MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return "ProductPhoto is empty";
            }
            ProductPhoto productPhoto = new ProductPhoto();
            productPhoto.setPhotoFile(file.getBytes());
            productPhoto.setProductSpec(productSpecRepository.findProductSpecBySpecId(specId));
            productPhotoRepository.save(productPhoto);
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }

        return "success insert photo";


    }

    @Override
    public List<ProductPhoto> findAllProductPhotos() {
        return productPhotoRepository.findAll();

    }

    @Override
    public ProductSpec insertProductSpec(ProductSpec productSpec) {
        String specId = productSpec.getSpecId();
        Product product = productRepository.findProductsByProductId(specId.substring(0, 5));
        productSpec.setDeleted(false);
        productSpec.setProduct(product);
        productSpecRepository.save(productSpec);
        return productSpec;
    }

    @Override
    public ProductPhoto findProductPhotoByPhotoId(Integer photoId) {
        Optional<ProductPhoto> optional = productPhotoRepository.findById(photoId);

        return optional.orElse(null);
    }

    @Override
    public ProductSpec findProductSpecBySpecId(String specId) {
        return productSpecRepository.findProductSpecBySpecId(specId);
    }

    @Override
    public Page<ProductDto> findFilterCategoryAndProductByPage(Integer pageNumber, String productName, String categoryId) {
        Pageable page = PageRequest.of(pageNumber, 6);
        Page<Product> products = productRepository.findByCategoryAndProductName(categoryId, productName, page);
        return products.map(p -> {
            ProductDto pt = new ProductDto();
            BeanUtils.copyProperties(p, pt);
            List<ProductSpec> productSpecs = p.getProductSpecs();
            if (productSpecs != null && !productSpecs.isEmpty()) {
                List<String> specIds = new ArrayList<>();
                productSpecs.forEach(spec -> specIds.add(spec.getSpecId()));
                pt.setSpecIds(specIds);
                List<ProductPhoto> productPhotos = productSpecs.get(0).getProductPhotos();
                if (productPhotos != null && !productPhotos.isEmpty()) {
                    ProductPhoto firstPhoto = productPhotos.get(0);
                    pt.setPhotoId(firstPhoto.getPhotoId());
                }
            }
            return pt;
        });
    }

    @Override
    public ProductSpec updateProductSpec(ProductSpec productSpec) {
        return productSpecRepository.save(productSpec);
    }

    @Override
    public String deleteProduct(String productId) {
        Product product = productRepository.findProductsByProductId(productId);
        productRepository.delete(product);
        return "success delete product";
    }

    @Override
    public String deleteProductSpec(String specId) {
        ProductSpec productSpec = productSpecRepository.findProductSpecBySpecId(specId);
        productSpecRepository.delete(productSpec);
        return "success delete productSpec";
    }

    @Override
    public void changeStockQuantity(String specId, int quantity) {
        Product product = productRepository.findProductsByProductId(specId.substring(0, 5));
        ProductSpec productSpec = productSpecRepository.findProductSpecBySpecId(specId);
        productSpec.setProduct(product);
        productSpec.setStockQuantity(quantity);
        productSpecRepository.save(productSpec);
    }

    @Override
    public String updateSpecPhoto(String specId, MultipartFile file) throws IOException {
        ProductSpec productSpec = productSpecRepository.findProductSpecBySpecId(specId);
        List<ProductPhoto> productPhotos = productPhotoRepository.findByProductSpec(productSpec);

        // 檢查 productPhotos 列表是否不為空
        if (!productPhotos.isEmpty()) {
            productPhotos.get(0).setPhotoFile(file.getBytes());
            productPhotoRepository.save(productPhotos.get(0));
            return "success update photo";
        } else {
            return "no product photos found";
        }
    }


    @Override
    public List<Product> filterCheckProductSalesStatus(List<Product> products) {
        List<Product> filteredProducts = new ArrayList<>();

        for (Product product : products) {
            if (product.getSalesStatus() != null) {
                filteredProducts.add(product);
            }
        }
        return filteredProducts;
    }

    @Override
    public Product updateProduct(Product p, String pId) {
        Product existingProduct = productRepository.findProductsByProductId(pId);
        String pIdReplace = pId.replace("\\s", "");
        String categoryId = String.valueOf(pIdReplace.charAt(0));
        Category cat = categoryRepository.findByCategoryId(categoryId);


        if (existingProduct != null) {
            existingProduct.setCategory(cat);
            existingProduct.setProductDescription(p.getProductDescription());
            existingProduct.setProductName(p.getProductName());
            existingProduct.setPrice(p.getPrice());
            existingProduct.setCapacity(p.getCapacity());
            existingProduct.setChip(p.getChip());
            existingProduct.setWifi(p.getWifi());
            existingProduct.setSize(p.getSize());
            existingProduct.setCpu(p.getCpu());
            existingProduct.setMemory(p.getMemory());
            existingProduct.setProductDisk(p.getProductDisk());
            existingProduct.setModifyDate(new Date());
            productRepository.save(existingProduct);
            System.out.println("Product updated successfully: " + existingProduct);
        }
        System.out.println(existingProduct);
        return existingProduct;
    }

    @Override
    public ResponseEntity<byte[]> showImage(Integer photoId) {
        Optional<ProductPhoto> optional = productPhotoRepository.findById(photoId);

        ProductPhoto photos = optional.orElse(null);


        byte[] photoByte = new byte[0];
        if (photos != null) {
            photoByte = photos.getPhotoFile();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);

        return new ResponseEntity<byte[]>(photoByte, headers, HttpStatus.OK);

    }

    @Override
    public Product changeProductSalesStatus(String productId) {
        Product product = productRepository.findByProductId(productId);
        if (product.getSalesStatus() == 1) {
            product.setSalesStatus(0);
            product.setModifyDate(new Date());
        } else {
            product.setSalesStatus(1);
            product.setModifyDate(new Date());
        }

        productRepository.save(product);
        return product;
    }

    @Override
    public String changeProductSpecQuantity(String specId, Integer quantity) {
        ProductSpec productSpec = productSpecRepository.findProductSpecBySpecId(specId);
        productSpec.setStockQuantity(quantity);
        productSpecRepository.save(productSpec);
        return "success change quantity";
    }
}
