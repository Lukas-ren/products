package com.fitting.productservice.service;

import com.fitting.productservice.dto.ProductRequest;
import com.fitting.productservice.dto.ProductResponse;
import com.fitting.productservice.entity.Category;
import com.fitting.productservice.entity.Product;
import com.fitting.productservice.exception.ResourceNotFoundException;
import com.fitting.productservice.repository.CategoryRepository;
import com.fitting.productservice.repository.ProductRepository;
import com.fitting.productservice.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ProductService — Tests unitarios")
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    // ── Test 1: crear producto exitosamente ────────────────────────────────────
    @Test
    @DisplayName("Crear producto — categoría existe → retorna ProductResponse")
    void create_WhenCategoryExists_ShouldReturnProductResponse() {
        // Arrange
        Category category = Category.builder()
                .id(1L)
                .name("Camisetas")
                .description("Camisetas y poleras")
                .build();

        ProductRequest request = ProductRequest.builder()
                .name("Camiseta Básica Blanca")
                .description("Algodón 100%")
                .price(new BigDecimal("19.99"))
                .stock(100)
                .size("M")
                .color("Blanco")
                .categoryId(1L)
                .build();

        Product savedProduct = Product.builder()
                .id(1L)
                .name("Camiseta Básica Blanca")
                .description("Algodón 100%")
                .price(new BigDecimal("19.99"))
                .stock(100)
                .size("M")
                .color("Blanco")
                .category(category)
                .build();

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);

        // Act
        ProductResponse response = productService.create(request);

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getName()).isEqualTo("Camiseta Básica Blanca");
        assertThat(response.getPrice()).isEqualByComparingTo("19.99");
        assertThat(response.getCategory().getId()).isEqualTo(1L);
        assertThat(response.getCategory().getName()).isEqualTo("Camisetas");
        verify(productRepository, times(1)).save(any(Product.class));
    }

    // ── Test 2: crear producto con categoría inexistente ───────────────────────
    @Test
    @DisplayName("Crear producto — categoría inexistente → lanza ResourceNotFoundException")
    void create_WhenCategoryNotExists_ShouldThrowResourceNotFoundException() {
        // Arrange
        ProductRequest request = ProductRequest.builder()
                .name("Camiseta Básica")
                .price(new BigDecimal("19.99"))
                .stock(100)
                .categoryId(99L)
                .build();

        when(categoryRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> productService.create(request))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("99");

        verify(productRepository, never()).save(any(Product.class));
    }

    // ── Test 3: listar todos los productos ─────────────────────────────────────
    @Test
    @DisplayName("Listar productos — retorna lista completa")
    void findAll_ShouldReturnAllProducts() {
        // Arrange
        Category category = Category.builder()
                .id(1L)
                .name("Camisetas")
                .build();

        List<Product> products = List.of(
                Product.builder().id(1L).name("Camiseta Blanca")
                        .price(new BigDecimal("19.99")).stock(50)
                        .category(category).build(),
                Product.builder().id(2L).name("Camiseta Negra")
                        .price(new BigDecimal("24.99")).stock(30)
                        .category(category).build()
        );

        when(productRepository.findAll()).thenReturn(products);

        // Act
        List<ProductResponse> responses = productService.findAll();

        // Assert
        assertThat(responses).isNotNull();
        assertThat(responses).hasSize(2);
        assertThat(responses.get(0).getName()).isEqualTo("Camiseta Blanca");
        assertThat(responses.get(1).getName()).isEqualTo("Camiseta Negra");
        verify(productRepository, times(1)).findAll();
    }
}