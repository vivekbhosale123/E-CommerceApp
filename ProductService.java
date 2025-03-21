package com.vivek.Vivek.Ecommerce.project.Service.Interf;

import java.io.IOException;
import java.math.BigDecimal;

import org.springframework.web.multipart.MultipartFile;

import com.vivek.Vivek.Ecommerce.project.Dto.Response;

public interface ProductService {

	Response createProduct(Long categoryId,MultipartFile image,String name,String description,BigDecimal price)throws IOException ;
	
	Response updateProduct(Long productId,Long categoryId,MultipartFile image,String name,String description,BigDecimal price)throws IOException;

	Response deleteProduct(Long productId);
	
	Response getProductById(Long productId);
	
	Response getAllProducts();	
	
	Response getProductByCategory(Long categoryId);
	
	Response searchProduct(String searchValue);
}
