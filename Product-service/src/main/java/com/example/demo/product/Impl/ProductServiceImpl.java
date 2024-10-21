package com.example.demo.product.Impl;

import com.example.demo.common.DTO.ProductDTO;
import com.example.demo.product.Mapper.ProductMapper;
import com.example.demo.product.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public List<ProductDTO> getAllProducts() {
        return productMapper.getAllProducts();
    }
}
