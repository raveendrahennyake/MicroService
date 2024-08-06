package com.example.demo.D.T.O;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class InventoryDTO {
    private int id;
    private int itemId;
    private int productId;
    private int quantity;
}
