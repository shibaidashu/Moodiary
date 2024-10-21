package com.example.demo.product.Controller;

import com.example.demo.common.DTO.ProductDTO;
import com.example.demo.product.Service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/products")
@Api(tags = "商品相关接口")
public class ProductController {

    @Autowired
    private ProductService productService;

    // 使用泛型确保类型安全
    @Autowired
    private RedisTemplate<String, List<ProductDTO>> redisTemplate;

    // 缓存的key
    private static final String PRODUCTS_CACHE_KEY = "all_products";

    @GetMapping
    @ApiOperation("查询所有商品")
    public List<ProductDTO> getAllProducts() {
        List<ProductDTO> productList = null;

        // 先尝试从缓存中获取
        try {
            productList = redisTemplate.opsForValue().get(PRODUCTS_CACHE_KEY);
            if (productList != null && !productList.isEmpty()) {
                System.out.println("从缓存中取出数据：" + productList);
                return productList; // 如果缓存中有数据，直接返回
            }
        } catch (Exception e) {
            // Redis 连接失败，输出日志，继续从数据库查询
            System.err.println("Redis 连接失败，继续查询数据库：" + e.getMessage());
        }

        // 缓存中没有数据，查询数据库
        productList = productService.getAllProducts();

        // 查询完数据库后，将结果存入缓存
        if (productList != null && !productList.isEmpty()) {
            // 设置缓存过期时间
            redisTemplate.opsForValue().set(PRODUCTS_CACHE_KEY, productList, 15, TimeUnit.MINUTES);
        }

        return productList;
    }
}
