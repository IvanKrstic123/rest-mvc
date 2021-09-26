package guru.springframework.restmvc.services;

import guru.springframework.restmvc.api.v1.mapper.CustomerMapper;
import guru.springframework.restmvc.api.v1.model.CustomerDTO;
import guru.springframework.restmvc.bootstrap.Bootstrap;
import guru.springframework.restmvc.controllers.v1.CustomerController;
import guru.springframework.restmvc.domain.Customer;
import guru.springframework.restmvc.repositories.CategoryRepository;
import guru.springframework.restmvc.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
public class CustomerServiceImplIT {

    public static final String FIRSTNAME = "Fred";
    public static final String LASTNAME = "Mayers";

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CategoryRepository categoryRepository;

    private CustomerService customerService;


    @BeforeEach
    void setUp() throws Exception {
        Bootstrap bootstrap = new Bootstrap(categoryRepository, customerRepository);
        bootstrap.run(); // load data

        customerService = new CustomerServiceImpl(customerRepository, CustomerMapper.INSTANCE);
    }

    @Test
    void patchCustomerUpdateFirstname() {
        //given
        Customer existingCustomer = customerRepository.findAll().get(0);

        CustomerDTO requestDTO = new CustomerDTO();
        requestDTO.setFirstname(FIRSTNAME);

        CustomerDTO returnedDTO = customerService.patchCustomer(existingCustomer.getId(), requestDTO);

        assertEquals(existingCustomer.getId(), returnedDTO.getId());
        assertEquals(FIRSTNAME, returnedDTO.getFirstname());
        assertEquals(existingCustomer.getLastname(), returnedDTO.getLastname());
        assertEquals(CustomerController.BASE_URL + existingCustomer.getId(), returnedDTO.getCustomerUrl());

    }

    @Test
    void patchCustomerUpdateLastname() {
        //given
        Customer existingCustomer = customerRepository.findAll().get(0);

        CustomerDTO requestDTO = new CustomerDTO();
        requestDTO.setLastname(LASTNAME);

        CustomerDTO returnedDTO = customerService.patchCustomer(existingCustomer.getId(), requestDTO);

        assertNotNull(existingCustomer);
        assertEquals(existingCustomer.getId(), returnedDTO.getId());
        assertEquals(LASTNAME, returnedDTO.getLastname());
        assertEquals(existingCustomer.getLastname(), returnedDTO.getLastname());
        assertEquals(CustomerController.BASE_URL + existingCustomer.getId(), returnedDTO.getCustomerUrl());
    }
}
