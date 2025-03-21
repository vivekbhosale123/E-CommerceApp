package com.vivek.Vivek.Ecommerce.project.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vivek.Vivek.Ecommerce.project.Dto.CategoryDto;
import com.vivek.Vivek.Ecommerce.project.Dto.Response;
import com.vivek.Vivek.Ecommerce.project.Service.Interf.CategoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> createCategory(@RequestBody CategoryDto categoryDto) {
        return ResponseEntity.ok(categoryService.createCategory(categoryDto));
    }

    @GetMapping("/get-all")
    public ResponseEntity<Response> getAllCategory() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @PutMapping("/update/{categoryId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> updateCategory(
            @PathVariable("categoryId") Long categoryId, // Explicitly name the variable
            @RequestBody CategoryDto categoryDto) {
        return ResponseEntity.ok(categoryService.updateCategory(categoryId, categoryDto));
    }

    @DeleteMapping("/delete/{categoryId}")
    public ResponseEntity<Response> deleteCategory(
            @PathVariable("categoryId") Long categoryId) { // Explicitly name the variable
        return ResponseEntity.ok(categoryService.deleteCategory(categoryId));
    }

    @GetMapping("/get-category-by-id/{categoryId}")
    public ResponseEntity<Response> getCategoryById(
            @PathVariable("categoryId") Long categoryId) { // Explicitly name the variable
        return ResponseEntity.ok(categoryService.getCategoryById(categoryId));
    }
}
