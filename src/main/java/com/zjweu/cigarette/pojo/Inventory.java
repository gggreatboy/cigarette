package com.zjweu.cigarette.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Inventory {
    private int inventoryId;
    private int inventoryComId;
    private String inventoryComName;
    private int inventoryNumber;
    private String inventoryComProvider;
}
