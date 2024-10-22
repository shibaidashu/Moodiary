package com.example.demo.product.Service;

import com.example.demo.common.DTO.ProductDTO;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    List<ProductDTO> getAllProducts();

    List<ProductDTO> searchProducts(String keyword) throws IOException;

    void importAllProductsToElasticsearch() throws IOException;
}
