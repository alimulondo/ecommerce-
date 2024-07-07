package com.ali.ecommerce.dto;

import com.ali.ecommerce.data.UserType;

public record User(String id, String name, String email, UserType userType) {
}
