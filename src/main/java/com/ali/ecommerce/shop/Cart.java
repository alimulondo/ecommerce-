package com.ali.ecommerce.shop;

import java.util.List;

public record Cart(long id, List<Item> Items) {
}


