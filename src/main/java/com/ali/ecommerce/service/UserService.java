package com.ali.ecommerce.service;

import com.ali.ecommerce.data.UserType;
import com.ali.ecommerce.dto.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

    private final List<Employee> employees;

    private final List<Customer> customers;

    private final List<Affiliate> affiliates;

    UserService(List<Employee> employees, List<Customer> customers, List<Affiliate> affiliates){
        this.employees = employees;
        this.customers = customers;
        this.affiliates = affiliates;
    }


    public Optional<DiscountUser> getUser(User user) {

        UserType userType = user.userType();
        DiscountUser discountUser;

        switch (userType){
            case CUSTOMER -> {
                discountUser = getCustomerUser(user.id()).orElse(null);
                assert discountUser != null;
                return Optional.of(discountUser);
            }
            case AFFILIATE -> {
                discountUser = getAffiliateUser(user.id()).orElse(null);
                assert discountUser != null;
                return Optional.of(discountUser);
            }
            case EMPLOYEE -> {
                discountUser = getEmployeeUser(user.id()).orElse(null);
                assert discountUser != null;
                return Optional.of(discountUser);
            }

            default -> {
                return Optional.empty();
            }

        }

    }

    private Optional<Employee> getEmployeeUser(String id) {
        return  employees.stream().filter(employee -> Objects.equals(employee.userId(), id)).findFirst();
    }

    private Optional<Affiliate>  getAffiliateUser(String id) {
        return  affiliates.stream().filter(affiliate -> Objects.equals(affiliate.userId(), id)).findFirst();
    }

    private Optional<Customer>  getCustomerUser(String id) {
        return  customers.stream().filter(customer -> Objects.equals(customer.userId(), id)).findFirst();
    }
}
