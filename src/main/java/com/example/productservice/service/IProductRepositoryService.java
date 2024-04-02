package com.example.productservice.service;

import com.example.productservice.enums.Language;
import com.example.productservice.repository.entity.Product;
import com.example.productservice.request.ProductCreateRequest;
import com.example.productservice.request.ProductUpdateRequest;

import java.util.List;

public interface IProductRepositoryService {
    Product createProduct(Language language, ProductCreateRequest productCreateRequest);
    Product getProduct(Language language, Long productId);
    List<Product> getProducts(Language language);
    Product updateProduct(Language language, Long productId, ProductUpdateRequest productUpdateRequest);
    Product deleteProduct(Language language, Long productId);

}
