package com.fitting.productservice.service;



import com.fitting.productservice.dto.CategoryRequest;
import com.fitting.productservice.dto.CategoryResponse;
import java.util.List;

public interface CategoryService {

    CategoryResponse create(CategoryRequest request);

    CategoryResponse findById(Long id);

    List<CategoryResponse> findAll();

    CategoryResponse update(Long id, CategoryRequest request);

    void delete(Long id);
}