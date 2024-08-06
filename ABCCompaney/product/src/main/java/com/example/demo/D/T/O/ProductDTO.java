package com.example.demo.D.T.O;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class ProductDTO {
    private int id;
    private int productId;
    private String productName;
    private String description;
    private int forSale;
}
