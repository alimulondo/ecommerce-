package com.ali.ecommerce.data;

import com.ali.ecommerce.dto.Affiliate;
import com.ali.ecommerce.dto.Customer;
import com.ali.ecommerce.dto.Employee;
import com.ali.ecommerce.dto.User;
import com.ali.ecommerce.shop.Product;
import com.ali.ecommerce.shop.ProductType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

@Configuration
public class DataDumpConfig {

    @Bean(name="productDump")
    List<Product> sampleProducts(){
       return List.of(
                new Product(1, 5, "Oranges", ProductType.GROCERY),
                new Product(2, 1000, "Iphone", ProductType.ELECTRONICS),
                new Product(3, 500, "Sofa", ProductType.FURNITURE),
                new Product(4, 90, "Ipod", ProductType.ELECTRONICS),
                new Product(5, 6, "Peach", ProductType.GROCERY),
                new Product(6, 8, "Strew Berries", ProductType.GROCERY)
        );
    }

    @Bean(name="userDump")
    List<User> sampleUsers(){
        return List.of(
                new User("123455", "Ali", "ali@gmail.com", UserType.EMPLOYEE),
                new User("234566", "llama", "llama@gmail.com", UserType.CUSTOMER),
                new User("487585", "sora", "sora@gmail.com", UserType.AFFILIATE),
                new User("895794", "mars", "mars@gmail.com", UserType.EMPLOYEE),
                new User("908797", "falcon", "falcon@gmail.com", UserType.CUSTOMER),
                new User("908669", "falcon", "falcon@gmail.com", UserType.OTHER)

        );
    }

    @Bean(name="employeeDump")
    List<Employee> sampleEmployeeUsers(){
        return List.of(
                new Employee("1234", "123455", "Admin"),
                new Employee("1235", "895794", "Store Keeper")
        );
    }

    @Bean(name="affiliateDump")
    List<Affiliate> sampleAffiliateUsers(){
        return List.of(
                new Affiliate("1122", "487585")
        );
    }

    @Bean(name="customerDump")
    List<Customer> sampleCustomerUsers(){
        return List.of(
                new Customer(
                        "4422",
                        "908797",
                        LocalDate.of(2021, 9, 6),
                        LocalDate.of(2021, 9, 6)
                ),
                new Customer(
                        "3322",
                        "234566",
                        LocalDate.of(2024, 3, 6),
                        LocalDate.of(2024, 3, 6)
                )
        );
    }
}
