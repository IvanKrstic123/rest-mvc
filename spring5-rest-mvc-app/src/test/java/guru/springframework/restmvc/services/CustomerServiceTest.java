package guru.springframework.restmvc.services;

import guru.springframework.model.CustomerDTO;
import guru.springframework.restmvc.api.v1.mapper.CustomerMapper;
import guru.springframework.restmvc.controllers.v1.CustomerController;
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
        customer.setFirstname(FIRST_NAME);
        customer.setLastname(LAST_NAME);

        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customer));

        //when
        CustomerDTO customerById = customerService.getCustomerById(2L);

        //then
        assertNotNull(customerById);
        assertEquals(FIRST_NAME, customerById.getFirstname());
        assertEquals(LAST_NAME, customerById.getLastname());
    }

    @Test
    void createNewCustomer() {

        //given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname(FIRST_NAME);
        customerDTO.setLastname(LAST_NAME);

        Customer savedCustomer = new Customer();
        savedCustomer.setId(ID);
        savedCustomer.setFirstname(FIRST_NAME);
        savedCustomer.setLastname(LAST_NAME);

        when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);

        //when
        CustomerDTO newCustomer = customerService.createNewCustomer(customerDTO);

        //then
        assertNotNull(newCustomer);
        assertEquals(FIRST_NAME, newCustomer.getFirstname());
        assertEquals(LAST_NAME, newCustomer.getLastname());
        assertEquals(CustomerController.BASE_URL + "1", newCustomer.getCustomerUrl());
    }

    @Test
    void updateCustomer() {
        //given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname(FIRST_NAME);
        customerDTO.setLastname(LAST_NAME);

        Customer customer = new Customer();
        customer.setId(ID);
        customer.setFirstname(FIRST_NAME);
        customer.setLastname(LAST_NAME);

        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        //when
        CustomerDTO returnedDTO = customerService.updateCustomer(ID, customerDTO);

        //then
        assertNotNull(returnedDTO);
        assertEquals(FIRST_NAME, returnedDTO.getFirstname());
        assertEquals(LAST_NAME, returnedDTO.getLastname());
        assertEquals(CustomerController.BASE_URL + "1", returnedDTO.getCustomerUrl());
    }

    @Test
    void patchCustomer() {
        //given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname(FIRST_NAME);
        customerDTO.setLastname(LAST_NAME);

        Customer customer = new Customer();
        customer.setId(ID);
        customer.setFirstname(FIRST_NAME);
        customer.setLastname(LAST_NAME);

        when(customerRepository.save(any(Customer.class))).thenReturn(customer);
        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customer));

        //when
        CustomerDTO returnedDTO = customerService.patchCustomer(ID, customerDTO);

        //then
        assertNotNull(returnedDTO);
        assertEquals(FIRST_NAME, returnedDTO.getFirstname());
        assertEquals(LAST_NAME, returnedDTO.getLastname());
        assertEquals(CustomerController.BASE_URL + "1", returnedDTO.getCustomerUrl());
    }

    @Test
    void deleteCustomerById() {
        customerRepository.deleteById(ID);

        verify(customerRepository, times(1)).deleteById(anyLong());
    }
}