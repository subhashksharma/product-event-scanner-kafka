package com.product.event.producteventscanner.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Product {

    @NotNull(message = "Product Id should not be null")
    private Long productId;

    @NotBlank(message="Product Name should not be blank")
    private String productName;


}
