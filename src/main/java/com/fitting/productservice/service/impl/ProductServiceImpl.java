package com.fitting.productservice.service.impl;



import com.fitting.productservice.dto.ProductRequest;
import com.fitting.productservice.dto.CategoryResponse;
import com.fitting.productservice.dto.ProductResponse;
import com.fitting.productservice.entity.Category;
import com.fitting.productservice.entity.Product;
import com.fitting.productservice.exception.ResourceNotFoundException;
import com.fitting.productservice.repository.CategoryRepository;
import com.fitting.productservice.repository.ProductRepository;
import com.fitting.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public ProductResponse create(ProductRequest request) {
        log.info("Creando producto: {}", request.getName());

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoría", request.getCategoryId()));

        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .stock(request.getStock())
                .size(request.getSize())
                .color(request.getColor())
                .category(category)
                .build();

        Product saved = productRepository.save(product);
        log.info("Producto creado con ID: {}", saved.getId());
        return toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponse findById(Long id) {
        log.debug("Buscando producto con ID: {}", id);
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto", id));
        return toResponse(product);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> findAll() {
        log.debug("Listando todos los productos");
        return productRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> findByCategory(Long categoryId) {
        log.debug("Buscando productos por categoría ID: {}", categoryId);
        if (!categoryRepository.existsById(categoryId)) {
            throw new ResourceNotFoundException("Categoría", categoryId);
        }
        return productRepository.findByCategoryId(categoryId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> searchByName(String name) {
        log.debug("Buscando productos por nombre: {}", name);
        return productRepository.searchByName(name)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public ProductResponse update(Long id, ProductRequest request) {
        log.info("Actualizando producto con ID: {}", id);

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto", id));

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoría", request.getCategoryId()));

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        product.setSize(request.getSize());
        product.setColor(request.getColor());
        product.setCategory(category);

        Product updated = productRepository.save(product);
        log.info("Producto actualizado: {}", updated.getId());
        return toResponse(updated);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        log.info("Eliminando producto con ID: {}", id);
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Producto", id);
        }
        productRepository.deleteById(id);
        log.info("Producto eliminado: {}", id);
    }

    // ── Mapper interno ──────────────────────────────────────────────────────────
    private ProductResponse toResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stock(product.getStock())
                .size(product.getSize())
                .color(product.getColor())
                .category(CategoryResponse.builder()
                        .id(product.getCategory().getId())
                        .name(product.getCategory().getName())
                        .description(product.getCategory().getDescription())
                        .build())
                .build();
    }
}