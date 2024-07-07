package com.ali.ecommerce.dto;

public sealed interface DiscountUser permits Employee, Affiliate, Customer {

}
