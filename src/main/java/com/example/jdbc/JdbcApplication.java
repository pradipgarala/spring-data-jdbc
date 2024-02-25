package com.example.jdbc;

import lombok.*;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.annotation.Id;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Set;

@SpringBootApplication
public class JdbcApplication {

	public static void main(String[] args) {
		SpringApplication.run(JdbcApplication.class, args);
	}

	@Bean
	ApplicationRunner applicationRunner(CustomerRepository customerRepository) {
		return a -> {
			var profile = new CustomerProfile(null, "alice", "secret");

			Set<CustomerOrder> customerOrders = Set.of(new CustomerOrder(null, "book"),
					new CustomerOrder(null, "pen"));

			var customer = new Customer(null, "a", null, customerOrders);

			customerRepository.save(customer);

			customerRepository.findAll()
					.forEach(System.out::println);
		};
	}
}

//record CustomerOrder(@Id Integer id, String name) {}
@Data
@NoArgsConstructor
@AllArgsConstructor
class CustomerOrder {
	@Id
	private Integer id;
	private String name;
}

record CustomerProfile(@Id Integer id, String username, String password) {}
record Customer(@Id Integer id, String name, CustomerProfile customerProfile, Set<CustomerOrder> orders) {}

interface CustomerRepository extends ListCrudRepository<Customer, Integer> {}
