package com.vivek.Vivek.Ecommerce.project.Service.Impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.vivek.Vivek.Ecommerce.project.Dto.CategoryDto;
import com.vivek.Vivek.Ecommerce.project.Dto.Response;
import com.vivek.Vivek.Ecommerce.project.Entities.Category;
import com.vivek.Vivek.Ecommerce.project.Exception.NotFoundException;
import com.vivek.Vivek.Ecommerce.project.Mapper.EntityDtoMapper;
import com.vivek.Vivek.Ecommerce.project.Repository.CategoryRepository;
import com.vivek.Vivek.Ecommerce.project.Service.Interf.CategoryService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService{
	
	private final CategoryRepository categoryRepository;
	
	private final EntityDtoMapper entityDtoMapper;
	
	public Response createCategory(CategoryDto categoryRequest) {
	
		Category category=new Category();
		
		category.setName(categoryRequest.getName());
		
		categoryRepository.save(category);
		
		return Response.builder()
				.status(200)
				.message("category added successfully")
				.build();
	}

	@Override
	public Response updateCategory(Long categoryId, CategoryDto categoryRequest) {
	
		Category category = categoryRepository.findById(categoryId).orElseThrow(()-> new NotFoundException("category not found"));
		
		category.setName(categoryRequest.getName());
		
		categoryRepository.save(category);
		
		return Response.builder()
				.status(200)
				.message("category updated successfully")
				.build();
	}

	@Override
	public Response getAllCategories() {
	
		List<Category> categories = categoryRepository.findAll();
		
		List<CategoryDto> categoryList = categories.stream()
				.map(entityDtoMapper::mapCategoryToBasic)
				.collect(Collectors.toList());
		
		return Response.builder()
				.status(200)
				.categoryList(categoryList)
				.build();
	}

	@Override
	public Response getCategoryById(Long categoryId) {
		Category category = categoryRepository.findById(categoryId).orElseThrow(()-> new NotFoundException("category not found"));
        CategoryDto categoryDto = entityDtoMapper.mapCategoryToBasic(category);
		
        return Response.builder()
        		.status(200)
        		.category(categoryDto)
        		.build();
	}

	@Override
	public Response deleteCategory(Long categoryId) {
		Category category = categoryRepository.findById(categoryId).orElseThrow(()-> new NotFoundException("category not found"));
        categoryRepository.delete(category);
        
		return Response.builder()
				.status(200)
				.message("deleted successfully")
				.build();
	}

}
