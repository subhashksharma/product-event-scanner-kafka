package com.product.event.producteventscanner.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ProductScannerEvent {

    private Long productEventId;
    private Product product;

}
