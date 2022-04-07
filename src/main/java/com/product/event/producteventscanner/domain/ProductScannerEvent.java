package com.product.event.producteventscanner.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ProductScannerEvent {

    private Long productEventId;
    @NotNull
    @Valid
    private Product product;

}
