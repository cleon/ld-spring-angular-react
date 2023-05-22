package com.launchdarkly.developer.LDSampleApp.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.math.BigDecimal;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Product {
    @Id
    @Getter @Setter private int id;
    @Getter @Setter private String name;
    @Getter @Setter private String URL;
    @Getter @Setter private BigDecimal price;
    @Getter @Setter private String type;
}
