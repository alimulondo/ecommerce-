package com.ali.ecommerce.dto;

import java.time.LocalDate;

public record Customer(String id, String userId, LocalDate createdAt, LocalDate updatedAt) implements DiscountUser {

}
