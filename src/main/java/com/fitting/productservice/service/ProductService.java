package com.fitting.productservice.service;



import com.fitting.productservice.dto.ProductRequest;
import com.fitting.productservice.dto.ProductResponse;

import java.util.List;

public interface ProductService {

    ProductResponse create(ProductRequest request);

    ProductResponse findById(Long id);

    List<ProductResponse> findAll();

    List<ProductResponse> findByCategory(Long categoryId);

    List<ProductResponse> searchByName(String name);

    ProductResponse update(Long id, ProductRequest request);

    void delete(Long id);
}