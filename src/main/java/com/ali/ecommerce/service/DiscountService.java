package com.ali.ecommerce.service;

import com.ali.ecommerce.dto.*;
import com.ali.ecommerce.shop.Cart;
import com.ali.ecommerce.shop.Item;
import com.ali.ecommerce.shop.Product;
import com.ali.ecommerce.shop.ProductType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class DiscountService {

    @Autowired
    private List<Product> products;

    private final List<User> users;

    private final UserService userservice;

    private final Function<Item, Product> getProduct = item -> products.stream()
            .filter(product1 -> product1.productId() == item.productId())
            .findFirst()
            .get();

    DiscountService(List<User> users, UserService userservice){
        this.users = users;
        this.userservice = userservice;
    }


    public Optional<Long> getDiscount(String userId, Cart cart){
        var result = users.stream().filter(element -> Objects.equals(element.id(), userId)).toList();

        if(result.size() != 1) return Optional.empty();

        User user = result.getFirst();

        var serviceUser = userservice.getUser(user);

        if(serviceUser.isEmpty()) return Optional.empty();

        switch (serviceUser.get()){
            case Employee  employee-> {
                return getEmployeeDiscount(cart);
            }

            case Affiliate affiliate -> {
                return getAffiliateDiscount(cart);
            }

            case Customer customer -> {
                return getCustomerDiscount(customer, cart);
            }
        }
    }

    private Optional<Long> getCustomerDiscount(Customer customer, Cart cart) {
        var currentDate = LocalDate.now();
        int customerLifeTimeInMonths = Period.between(customer.createdAt(), currentDate).getMonths();
        int customerLifeTimeInYears = Period.between(customer.createdAt(), currentDate).getYears();
        int totalCustomerLifeTimeInMonths = (customerLifeTimeInYears * 12) + customerLifeTimeInMonths;

        if(totalCustomerLifeTimeInMonths > 24){
            var nonGroceryTotalPrice = getNonGroceryTotalPrice(cart);
            var discount = Math.round( nonGroceryTotalPrice * ((double) 5/100)); // 2 percent discount on non-grocery items
            return Optional.of(discount);
        }

        var allItemTotalPrice = Math.round(getAllItemTotalPrice(cart));

        if(allItemTotalPrice >= 100) {
            var reminder = allItemTotalPrice % 100;
            var discountableAmount = allItemTotalPrice - reminder;
            var discount = (discountableAmount/100) * 5;
            return Optional.of((discount));
        }


        return Optional.of(0L);
    }

    private Optional<Long> getAffiliateDiscount(Cart cart) {
        var nonGroceryTotalPrice = getNonGroceryTotalPrice(cart);
        var discount = Math.round( nonGroceryTotalPrice * ((double) 10 /100)); // 10 percent discount on non-grocery items
        return Optional.of(discount);
    }

    private Optional<Long> getEmployeeDiscount(Cart cart) {

        var nonGroceryTotalPrice = getNonGroceryTotalPrice(cart);
        var discount = Math.round( nonGroceryTotalPrice * ((double) 30 /100)); // 30 percent discount on non-grocery items
        return Optional.of(discount);
    }

    private double getNonGroceryTotalPrice(Cart cart) {
        var prices = checkoutPriceItemTypes(cart);
        return prices.entrySet().stream()
                .filter(price -> !price.getKey().equals(ProductType.GROCERY))
                .mapToDouble(Map.Entry::getValue)
                .sum();
    }

    private double getAllItemTotalPrice(Cart cart) {
        var prices = checkoutPriceItemTypes(cart);
        return prices.values().stream()
                .mapToDouble(v -> v)
                .sum();
    }

    private Map<ProductType, Double> checkoutPriceItemTypes(Cart cart) {
        List<Item> items = cart.Items();
        Map<ProductType, Double> itemCheckOutPrice = new EnumMap<>(ProductType.class);
        var totalPrices = items.stream()
                .map(item -> {
                    var product = getProduct.apply(item);
                    Map<Long, Double> price = new HashMap<>();
                    ItemPrice itemPrice = new ItemPrice(product, (item.quantity() * product.price()));
                    price.put(item.productId(), (item.quantity() * product.price()));
                    return itemPrice;
                }).collect(Collectors.groupingBy(item -> item.product.type()));

        totalPrices.forEach((key, value) -> itemCheckOutPrice.put(key, value.stream().mapToDouble(ItemPrice::totalPrice).sum()));

        return itemCheckOutPrice;
    }

    private  record ItemPrice(Product product, double totalPrice) {}

}
