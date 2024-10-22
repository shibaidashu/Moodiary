package com.example.demo.product.Impl;

import cn.hutool.json.JSONUtil;
import com.example.demo.common.DTO.ProductDTO;
import com.example.demo.product.Mapper.ProductMapper;
import com.example.demo.product.Service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    // 定时清空 Elasticsearch 索引中的所有文档，每15分钟执行一次
    @Scheduled(fixedRate = 15 * 60 * 1000)  // 15分钟
    public void clearElasticsearchDocuments() throws IOException {
        // 创建 DeleteByQueryRequest
        DeleteByQueryRequest request = new DeleteByQueryRequest("product_index");
        request.setQuery(QueryBuilders.matchAllQuery());

        // 执行删除操作
        BulkByScrollResponse response = restHighLevelClient.deleteByQuery(request, RequestOptions.DEFAULT);
        System.out.println("Elasticsearch 索引中的所有文档已清空。");
        importAllProductsToElasticsearch();
    }

    // 查询所有商品
    @Override
    public List<ProductDTO> getAllProducts() {
        return productMapper.getAllProducts();
    }

    // 将商品数据导入 Elasticsearch
    public void importAllProductsToElasticsearch() throws IOException {
        List<ProductDTO> products = getAllProducts();  // 直接调用现有方法获取所有商品
        for (ProductDTO product : products) {
            try{
            // 将 ProductDTO 对象转换为 JSON 字符串
            String doc = JSONUtil.toJsonStr(product);
            // 准备Request对象
            IndexRequest request = new IndexRequest("product_index").id(String.valueOf(product.getProductId()));
            // 准备Json文档
            request.source(doc, XContentType.JSON);
            // 发送请求
            restHighLevelClient.index(request, RequestOptions.DEFAULT);
        }catch (Exception e){
            }
        }
        System.out.println("商品信息已导入到 Elasticsearch 索引库。");
    }

    // 根据关键词搜索商品
    public List<ProductDTO> searchProducts(String keyword) throws IOException {
        // 构建搜索请求
        SearchRequest searchRequest = new SearchRequest("product_index");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder()
                .query(QueryBuilders.multiMatchQuery(keyword, "productName", "productDescription"));
        searchRequest.source(searchSourceBuilder);

        // 执行搜索请求
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        // 处理搜索结果
        List<ProductDTO> productList = new ArrayList<>();
        for (SearchHit hit : searchResponse.getHits().getHits()) {
            ProductDTO product = new ProductDTO();
            product.setProductId(Integer.parseInt(hit.getId()));
            product.setProductName((String) hit.getSourceAsMap().get("productName"));
            product.setProductDescription((String) hit.getSourceAsMap().get("productDescription"));
            product.setPointsCost((Integer) hit.getSourceAsMap().get("pointsCost"));
            product.setStock((Integer) hit.getSourceAsMap().get("stock"));
            productList.add(product);
        }

        return productList;
    }
}
