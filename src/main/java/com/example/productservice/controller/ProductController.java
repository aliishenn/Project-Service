package com.example.productservice.controller;

import com.example.productservice.enums.Language;
import com.example.productservice.exception.enums.FriendlyMessageCodes;
import com.example.productservice.exception.utils.FriendlyMessageUtils;
import com.example.productservice.repository.entity.Product;
import com.example.productservice.request.ProductCreateRequest;
import com.example.productservice.request.ProductUpdateRequest;
import com.example.productservice.response.FriendlyMessage;
import com.example.productservice.response.InternalApiResponse;
import com.example.productservice.response.ProductReponse;
import com.example.productservice.service.IProductRepositoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/1.0/product")
@RequiredArgsConstructor
class ProductController {
    private final IProductRepositoryService productRepositoryService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/{language}/vreate")
    public InternalApiResponse<ProductReponse> createProduct(@PathVariable("language")Language language,
                                                             @RequestBody ProductCreateRequest productCreateRequest){
        log.debug("[{}][crateProduct] -> request:{}",this.getClass().getSimpleName(),productCreateRequest);
        Product product = productRepositoryService.createProduct(language,productCreateRequest);
        ProductReponse productReponse= convertProductResponse(product);
        log.debug("[{}][crateProduct] -> request:{}",this.getClass().getSimpleName(),productReponse);
        return InternalApiResponse.<ProductReponse>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.SUCCESS))
                        .description(FriendlyMessageUtils.getFriendlyMessage(language,FriendlyMessageCodes.PRODUCT_SUCCESSFULY_CREATED))
                        .build())
                .httpStatus(HttpStatus.CREATED)
                .hasError(false)
                .payload(productReponse)
                .build();
    }
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{language}/get/{productId}")
    public InternalApiResponse<ProductReponse>getProduct(@PathVariable("language")Language language,
                                                         @PathVariable("productId")Long productId){
        log.debug("[{}][getProduct] -> request productId: {}",this.getClass().getSimpleName(),productId);
        Product product = productRepositoryService.getProduct(language,productId);
        ProductReponse productReponse = convertProductResponse(product);
        log.debug("[{}][getProduct] -> request: {}",this.getClass().getSimpleName(),productReponse);
        return InternalApiResponse.<ProductReponse>builder()
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(productReponse)
                .build();
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/{language}/update/{productId}")
    public InternalApiResponse<ProductReponse> updateProduct(@PathVariable("language") Language language,
                                                             @PathVariable("productId")Long productId,
                                                             @RequestBody ProductUpdateRequest productUpdateRequest){
        log.debug("[{}][getProduct] -> request: {} {}",this.getClass().getSimpleName(),productId,productUpdateRequest);
        Product product = productRepositoryService.updateProduct(language,productId,productUpdateRequest);
        ProductReponse productReponse = convertProductResponse(product);
        log.debug("[{}][getProduct] -> request: {} {}",this.getClass().getSimpleName(),productId,productReponse);
        return InternalApiResponse.<ProductReponse>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(language,FriendlyMessageCodes.SUCCESS))
                        .description(FriendlyMessageUtils.getFriendlyMessage(language,FriendlyMessageCodes.PRODUCT_SUCCESSFULLY_UPDATED))
                        .build())
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(productReponse)
                .build();
    }

    private static ProductReponse convertProductResponse(Product product) {
        return ProductReponse.builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .quantity(product.getQuantity())
                .price(product.getPrice())
                .productCreatedDate(product.getProductCreatedDate().getTime())
                .productUpdatedDate(product.getProductUpdatedDate().getTime())
                .build();
    }
}
