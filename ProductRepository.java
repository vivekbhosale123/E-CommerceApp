package com.vivek.Vivek.Ecommerce.project.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vivek.Vivek.Ecommerce.project.Entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByCategoryId(Long categoryId);

    List<Product> findByNameContainingOrDescriptionContaining(String name, String description); // Fix parameter name
}
