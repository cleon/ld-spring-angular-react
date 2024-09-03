package com.launchdarkly.developer.LDSampleApp.web;

import com.launchdarkly.developer.LDSampleApp.model.Product;
import com.launchdarkly.developer.LDSampleApp.model.ProductRepository;

import com.launchdarkly.sdk.LDContext;
import com.launchdarkly.sdk.ContextKind;
import com.launchdarkly.sdk.server.LDClient;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.function.Supplier;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/api", produces = "application/json")
@CrossOrigin(origins = { "http://localhost:4200", "http://localhost:3000" })
class ProductController {

    private static final String DISCOUNT_PRICING_FLAG = "discount-pricing";

    @Getter
    @Setter
    private LDClient client;
    @Getter
    @Setter
    private Supplier<LDContext> userContext;
    @Getter
    @Setter
    private ProductRepository repository;

    @GetMapping(path = "/product/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable int id) {
        return this.repository.findById(id)
                .map(this::discountPrice)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    private Product discountPrice(final Product product) {
        var productContext = LDContext.builder(ContextKind.of("product"), String.valueOf(product.getId()))
                .set("type", product.getType())
                .set("price", product.getPrice().doubleValue())
                .set("name", product.getName())
                .build();
        var context = LDContext.createMulti(userContext.get(), productContext);
        var discountFlagValue = client.doubleVariation(DISCOUNT_PRICING_FLAG, context, 0);
        var discountPercent = new BigDecimal(discountFlagValue);
        var discountAmount = product.getPrice().multiply(discountPercent).setScale(2, RoundingMode.HALF_UP);
        var adjustedPrice = product.getPrice().subtract(discountAmount);
        product.setPrice(adjustedPrice);
        return product;
    }
}
