package com.launchdarkly.developer.LDSampleApp;

import com.launchdarkly.developer.LDSampleApp.model.Product;
import com.launchdarkly.developer.LDSampleApp.model.ProductRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.math.BigDecimal;

@Component
class Initializer implements CommandLineRunner {

    private final ProductRepository repository;

    public Initializer(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... strings) {
        this.repository.saveAll(products());
    }

    private List<Product> products() {
        return List.of(
                new Product(1,"Chair", "https://images.pexels.com/photos/7193570/pexels-photo-7193570.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2", new BigDecimal("90.00"), "furniture"),
                new Product(2, "Camera", "http://ecx.images-amazon.com/images/I/61%2BABMMN5zL._SL1500_.jpg", new BigDecimal("600.00"), "electronics"),
                new Product(3, "Shoes", "https://images.pexels.com/photos/6050919/pexels-photo-6050919.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2", new BigDecimal("130.00"), "clothing"),
                new Product(4,"Soda", "https://images.pexels.com/photos/4113627/pexels-photo-4113627.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2", new BigDecimal("6.00"), "food"),
                new Product(5, "Utensils", "https://images.pexels.com/photos/4397817/pexels-photo-4397817.jpeg", new BigDecimal("40.00"), "housewares")
        );
    }
}