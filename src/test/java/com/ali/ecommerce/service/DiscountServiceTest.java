package com.ali.ecommerce.service;

import com.ali.ecommerce.shop.Cart;
import com.ali.ecommerce.shop.Item;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class DiscountServiceTest {

    @Autowired
    DiscountService discountService;

    @Test
    void test30PercentDiscountWhenUserIsEmployee(){
        // Create
        List<Item> items = List.of(
                new Item(2, 5),
                new Item(1, 3),
                new Item(3, 3)
        );
        Cart cart = new Cart(2, items);

        // process
        var discount = discountService.getDiscount("123455", cart).orElse(null);

        // Assert
        Assertions.assertEquals(1950, discount);

    }

    @Test
    void test10PercentDiscountWhenUserIsAffiliate(){
        // Create
        List<Item> items = List.of(
                new Item(2, 5),
                new Item(1, 3),
                new Item(3, 3)
        );
        Cart cart = new Cart(3, items);

        // process
        var discount = discountService.getDiscount("487585", cart).orElse(null);

        // Assert
        Assertions.assertEquals(650, discount);
    }

    @Test
    void test45PercentDiscountWhenUserIsCustomerMoreThan2YearsOldWithTheStore(){
        // Create
        List<Item> items = List.of(
                new Item(2, 5),
                new Item(1, 3),
                new Item(3, 3)
        );
        Cart cart = new Cart(5, items);

        // process
        var discount = discountService.getDiscount("908797", cart).orElse(null);

        // Assert
        Assertions.assertEquals(325, discount);

    }

    @Test
    void test5DollarDiscountWhenUserIsCustomerLessOrEqualToTwoYearsOldWithTheStore(){
        // Create
        List<Item> items = List.of(
                new Item(2, 5),
                new Item(1, 3),
                new Item(3, 3)
        );
        Cart cart = new Cart(7, items);

        // process
        var discount = discountService.getDiscount("234566", cart).orElse(null);

        // Assert
        Assertions.assertEquals(325, discount);
    }

    @Test
    void test0PercentDiscountWhenUserIsEmployeeAndOnlyGroceryIsBought(){
        // Create
        List<Item> items = List.of(
                new Item(1, 8),
                new Item(5, 13),
                new Item(6, 10)
        );
        Cart cart = new Cart(11, items);

        // process
        var discount = discountService.getDiscount("123455", cart).orElse(null);

        // Assert
        Assertions.assertEquals(0, discount);

    }

    @Test
    void test0PercentDiscountOfferedWhenUserIsAffiliateAndOnlyGroceryIsBought(){
        // Create
        List<Item> items = List.of(
                new Item(1, 8),
                new Item(5, 10),
                new Item(6, 10)
        );
        Cart cart = new Cart(10, items);

        // process
        var discount = discountService.getDiscount("487585", cart).orElse(null);

        // Assert
        Assertions.assertEquals(0, discount);
    }

    @Test
    void test0PercentDiscountOfferedWhenUserIsCustomerMoreThanTwoYearsOldWithTheStoreAndOnlyGroceryIsBought(){
        // Create
        List<Item> items = List.of(
                new Item(1, 8),
                new Item(5, 13),
                new Item(6, 15)
        );
        Cart cart = new Cart(10, items);

        // process
        var discount = discountService.getDiscount("487585", cart).orElse(null);

        // Assert
        Assertions.assertEquals(0, discount);
    }

    @Test
    void testEmptyIsReturnedIfUserDoesNotExist(){
        // Create
        List<Item> items = List.of(
                new Item(1, 8),
                new Item(5, 10),
                new Item(6, 10)
        );
        Cart cart = new Cart(10, items);

        // process
        var discount = discountService.getDiscount("1112233", cart).orElse(null);

        // Assert
        Assertions.assertNull(discount);
    }

    @Test
    void testUserExistsButDoesNotBelongToRequirementCategory(){
        // Create
        List<Item> items = List.of(
                new Item(1, 8),
                new Item(5, 10),
                new Item(6, 10)
        );
        Cart cart = new Cart(10, items);

        // process
        var discount = discountService.getDiscount("908669", cart).orElse(null);

        // Assert
        Assertions.assertNull(discount);
    }
}