package com.vivek.Vivek.Ecommerce.project.Service.Interf;

import com.vivek.Vivek.Ecommerce.project.Dto.CategoryDto;
import com.vivek.Vivek.Ecommerce.project.Dto.Response;

public interface CategoryService {

	Response createCategory(CategoryDto categoryRequest);
	
	Response updateCategory(Long categoryId,CategoryDto categoryRequest);

	Response getAllCategories();

	Response getCategoryById(Long categoryId);

	Response deleteCategory(Long categoryId);

}
