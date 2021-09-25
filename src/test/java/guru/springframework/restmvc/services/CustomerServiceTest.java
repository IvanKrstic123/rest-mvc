package guru.springframework.restmvc.services;

import guru.springframework.restmvc.api.v1.mapper.CustomerMapper;
import guru.springframework.restmvc.api.v1.model.CustomerDTO;
import guru.springframework.restmvc.domain.Customer;
import guru.springframework.restmvc.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerServiceTest {

    public static final long ID = 1L;
    public static final String FIRST_NAME = "Ivan";
    public static final String LAST_NAME = "Krstic";

    @Mock
    CustomerRepository customerRepository;

    CustomerService customerService;

    @BeforeEach
    void setUp() {
        customerRepository = Mockito.mock(CustomerRepository.class);
        customerService = new CustomerServiceImpl(customerRepository, CustomerMapper.INSTANCE);
    }

    @Test
    void getAllCustomersTest() {
        //given
        List<Customer> customers = Arrays.asList(new Customer(), new Customer(), new Customer());

        when(customerRepository.findAll()).thenReturn(customers);

        //when
        List<CustomerDTO> allCustomers = customerService.getAllCustomers();

        //then
        assertEquals(3, allCustomers.size());
        verify(customerRepository, times(1)).findAll();

    }

    @Test
    void getCustomersByIdTest() {

        //given
        Customer customer = new Customer();
        customer.setId(ID);
        customer.setFirstName(FIRST_NAME);
        customer.setLastName(LAST_NAME);

        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customer));

        //when
        CustomerDTO customerById = customerService.getCustomerById(2L);

        //then
        assertEquals(ID, customerById.getId());
        assertEquals(FIRST_NAME, customerById.getFirstName());
        assertEquals(LAST_NAME, customerById.getLastName());
    }
}