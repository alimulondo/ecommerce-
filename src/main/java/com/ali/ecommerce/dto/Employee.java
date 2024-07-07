package com.ali.ecommerce.dto;

public record Employee(String id, String userId, String role) implements DiscountUser {

}
