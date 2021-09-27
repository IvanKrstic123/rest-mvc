package guru.springframework.restmvc.bootstrap;

import guru.springframework.restmvc.domain.Category;
import guru.springframework.restmvc.domain.Customer;
import guru.springframework.restmvc.domain.Vendor;
import guru.springframework.restmvc.repositories.CategoryRepository;
import guru.springframework.restmvc.repositories.CustomerRepository;
import guru.springframework.restmvc.repositories.VendorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements CommandLineRunner {

    private CategoryRepository categoryRepository;
    private CustomerRepository customerRepository;
    private VendorRepository vendorRepository;

    public Bootstrap(CategoryRepository categoryRepository, CustomerRepository customerRepository, VendorRepository vendorRepository) {
        this.categoryRepository = categoryRepository;
        this.customerRepository = customerRepository;
        this.vendorRepository = vendorRepository;
    }

    // called on startup
    @Override
    public void run(String... args) throws Exception {

        loadCategories();
        loadCustomers();
        loadVendors();
    }

    private void loadVendors() {
        Vendor vendor = new Vendor();
        vendor.setName("Maxi");

        Vendor vendor1 = new Vendor();
        vendor1.setName("Roda");

        Vendor vendor2 = new Vendor();
        vendor2.setName("Mercator");

        Vendor vendor3 = new Vendor();
        vendor3.setName("Matijevic");

        vendorRepository.save(vendor);
        vendorRepository.save(vendor1);
        vendorRepository.save(vendor2);
        vendorRepository.save(vendor3);

        System.out.println("Vendors Data Loaded : " + vendorRepository.count());
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
        customer.setFirstname("Ivan");
        customer.setLastname("Krstic");

        Customer customer1 = new Customer();
        customer1.setId(2L);
        customer1.setFirstname("Stojan");
        customer1.setLastname("Novakovic");

        customerRepository.save(customer);
        customerRepository.save(customer1);

        System.out.println("Customer Data Loaded! : " + customerRepository.count());
    }
}
