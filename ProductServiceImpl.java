package com.vivek.Vivek.Ecommerce.project.Service.Impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.vivek.Vivek.Ecommerce.project.Dto.ProductDto;
import com.vivek.Vivek.Ecommerce.project.Dto.Response;
import com.vivek.Vivek.Ecommerce.project.Entities.Category;
import com.vivek.Vivek.Ecommerce.project.Entities.Product;
import com.vivek.Vivek.Ecommerce.project.Exception.NotFoundException;
import com.vivek.Vivek.Ecommerce.project.Mapper.EntityDtoMapper;
import com.vivek.Vivek.Ecommerce.project.Repository.CategoryRepository;
import com.vivek.Vivek.Ecommerce.project.Repository.ProductRepository;
import com.vivek.Vivek.Ecommerce.project.Service.Interf.ProductService;
import com.vivek.Vivek.Ecommerce.project.Services.AwsS3Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final EntityDtoMapper entityDtoMapper;
    private final AwsS3Service awsS3Service;

    @Override
    public Response createProduct(Long categoryId, MultipartFile image, String name, String description, BigDecimal price) throws IOException {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new NotFoundException("Category not found"));
        String imageToS3 = awsS3Service.saveImageToS3(image);
        
        Product product = new Product();
        product.setCategory(category);
        product.setPrice(price);
        product.setName(name);
        product.setImageUrl(imageToS3);
        product.setDescription(description);
        
        productRepository.save(product);
        
        return Response.builder()
                .status(200)
                .message("Product added successfully")
                .build();
    }

    @Override
    public Response updateProduct(Long productId, Long categoryId, MultipartFile image, String name, String description, BigDecimal price) throws IOException {
        Product product = productRepository.findById(productId).orElseThrow(() -> new NotFoundException("Product not found"));
        Category category = null;
        String productImageUrl = null;

        if (categoryId != null) {
            category = categoryRepository.findById(categoryId).orElseThrow(() -> new NotFoundException("Category not found"));
        }

        if (image != null && !image.isEmpty()) {
            productImageUrl = awsS3Service.saveImageToS3(image);
        }

        if (category != null) product.setCategory(category);
        if (name != null) product.setName(name);
        if (price != null) product.setPrice(price);
        if (description != null) product.setDescription(description);
        if (productImageUrl != null) product.setImageUrl(productImageUrl);

        try {
            productRepository.save(product);
        } catch (OptimisticLockingFailureException e) {
            return Response.builder()
                    .status(500)
                    .message("Product was modified or deleted by another transaction. Please refresh and try again.")
                    .build();
        }

        return Response.builder()
                .status(200)
                .message("Product updated successfully")
                .build();
    }

    @Override
    public Response deleteProduct(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new NotFoundException("Product not found"));
        productRepository.delete(product);
        return Response.builder()
                .status(200)
                .message("Product deleted successfully")
                .build();
    }

    @Override
    public Response getProductById(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new NotFoundException("Product not found"));
        ProductDto productDto = entityDtoMapper.mapProductToBasic(product);
        return Response.builder()
                .status(200)
                .product(productDto)
                .build();
    }

    @Override
    public Response getAllProducts() {
        List<Product> productList = productRepository.findAll(Sort.by(Direction.DESC, "id"));
        List<ProductDto> productDtos = productList.stream()
                .map(entityDtoMapper::mapProductToBasic)
                .collect(Collectors.toList());
        return Response.builder()
                .status(200)
                .productList(productDtos)
                .build();
    }

    @Override
    public Response getProductByCategory(Long categoryId) {
        List<Product> products = productRepository.findByCategoryId(categoryId);
        if (products.isEmpty()) {
            throw new NotFoundException("No products found for this category");
        }
        List<ProductDto> productDtos = products.stream()
                .map(entityDtoMapper::mapProductToBasic)
                .collect(Collectors.toList());
        return Response.builder()
                .status(200)
                .productList(productDtos)
                .build();
    }

    @Override
    public Response searchProduct(String searchValue) {
        List<Product> products = productRepository.findByNameContainingOrDescriptionContaining(searchValue, searchValue);
        if (products.isEmpty()) {
            throw new NotFoundException("No products found matching the search criteria");
        }
        List<ProductDto> productDtos = products.stream()
                .map(entityDtoMapper::mapProductToBasic)
                .collect(Collectors.toList());
        return Response.builder()
                .status(200)
                .productList(productDtos)
                .build();
    }
}
