package guru.springframework.restmvc.bootstrap;

import guru.springframework.restmvc.domain.Category;
import guru.springframework.restmvc.domain.Customer;
import guru.springframework.restmvc.repositories.CategoryRepository;
import guru.springframework.restmvc.repositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements CommandLineRunner {

    private CategoryRepository categoryRepository;
    private CustomerRepository customerRepository;

    public Bootstrap(CategoryRepository categoryRepository, CustomerRepository customerRepository) {
        this.categoryRepository = categoryRepository;
        this.customerRepository = customerRepository;
    }

    // called on startup
    @Override
    public void run(String... args) throws Exception {

        loadCategories();
        loadCustomers();
    }

    private void loadCategories() {
        Category fruits = new Category();
        fruits.setName("Fruits");

        Category dried = new Category();
        dried.setName("Dried");

        Category fresh = new Category();
        fresh.setName("Fresh");

        Category exotic = new Category();
        exotic.setName("Exotic");

        Category nuts = new Category();
        nuts.setName("Nuts");

        categoryRepository.save(fruits);
        categoryRepository.save(dried);
        categoryRepository.save(fresh);
        categoryRepository.save(exotic);
        categoryRepository.save(nuts);

        System.out.println("Data Loaded : " + categoryRepository.count());
    }

    private void loadCustomers() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("Ivan");
        customer.setLastName("Krstic");

        Customer customer1 = new Customer();
        customer1.setId(2L);
        customer1.setFirstName("Stojan");
        customer1.setLastName("Novakovic");

        customerRepository.save(customer);
        customerRepository.save(customer1);

        System.out.println("Customer Data Loaded! : " + customerRepository.count());
    }
}
