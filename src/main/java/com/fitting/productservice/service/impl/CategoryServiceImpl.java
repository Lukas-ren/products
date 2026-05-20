package com.fitting.productservice.service.impl;



import com.fitting.productservice.dto.CategoryRequest;
import com.fitting.productservice.dto.CategoryResponse;

import com.fitting.productservice.entity.Category;
import com.fitting.productservice.exception.BusinessException;
import com.fitting.productservice.exception.ResourceNotFoundException;
import com.fitting.productservice.repository.CategoryRepository;
import com.fitting.productservice.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public CategoryResponse create(CategoryRequest request) {
        log.info("Creando categoría: {}", request.getName());

        if (categoryRepository.existsByNameIgnoreCase(request.getName())) {
            throw new BusinessException("Ya existe una categoría con el nombre: " + request.getName());
        }

        Category category = Category.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();

        Category saved = categoryRepository.save(category);
        log.info("Categoría creada con ID: {}", saved.getId());
        return toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryResponse findById(Long id) {
        log.debug("Buscando categoría con ID: {}", id);
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría", id));
        return toResponse(category);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponse> findAll() {
        log.debug("Listando todas las categorías");
        return categoryRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public CategoryResponse update(Long id, CategoryRequest request) {
        log.info("Actualizando categoría con ID: {}", id);

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría", id));

        // Verificar nombre duplicado (ignorando la misma entidad)
        categoryRepository.findByNameIgnoreCase(request.getName())
                .filter(existing -> !existing.getId().equals(id))
                .ifPresent(existing -> {
                    throw new BusinessException("Ya existe otra categoría con el nombre: " + request.getName());
                });

        category.setName(request.getName());
        category.setDescription(request.getDescription());

        Category updated = categoryRepository.save(category);
        log.info("Categoría actualizada: {}", updated.getId());
        return toResponse(updated);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        log.info("Eliminando categoría con ID: {}", id);
        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Categoría", id);
        }
        categoryRepository.deleteById(id);
        log.info("Categoría eliminada: {}", id);
    }

    // ── Mapper interno ──────────────────────────────────────────────────────────
    private CategoryResponse toResponse(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .build();
    }
}