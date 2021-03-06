package guru.springframework.restmvc.controllers.v1;

import guru.springframework.model.CustomerDTO;
import guru.springframework.restmvc.controllers.RestResponseEntityExceptionHandler;
import guru.springframework.restmvc.services.CustomerService;
import guru.springframework.restmvc.services.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest extends AbstractRestControllerTest{

    public static final String FIRST_NAME = "Ivan";
    public static final String LAST_NAME = "Krstic";
    public static final String FIRST_NAME1 = "Stojan";
    public static final String LAST_NAME1 = "Novakovic";

    @Mock
    CustomerService customerService;

    @InjectMocks
    CustomerController customerController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(customerController)
                .setControllerAdvice(new RestResponseEntityExceptionHandler())
                .build();
    }

    @Test
    void testListCustomers() throws Exception {
        CustomerDTO customer = new CustomerDTO();
        customer.setFirstname(FIRST_NAME);
        customer.setLastname(LAST_NAME);

        CustomerDTO customer1 = new CustomerDTO();
        customer1.setFirstname(FIRST_NAME1);
        customer1.setLastname(LAST_NAME1);

        List<CustomerDTO> customersList = Arrays.asList(customer, customer1);

        when(customerService.getAllCustomers()).thenReturn(customersList);

        mockMvc.perform(get(CustomerController.BASE_URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customers", hasSize(2)));
    }

    @Test
    void testGetCustomerById() throws Exception {
        CustomerDTO customer = new CustomerDTO();
        customer.setFirstname(FIRST_NAME);
        customer.setLastname(LAST_NAME);

        when(customerService.getCustomerById(anyLong())).thenReturn(customer);

        mockMvc.perform(get(CustomerController.BASE_URL + "3")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname", equalTo(FIRST_NAME)));
    }

    @Test
    void testGetCustomerByIdNotFound() throws Exception {

        when(customerService.getCustomerById(anyLong())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get(CustomerController.BASE_URL + "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void createNewCustomer() throws Exception {
        CustomerDTO customer = new CustomerDTO();
        customer.setFirstname(FIRST_NAME);
        customer.setLastname(LAST_NAME);
        customer.setCustomerUrl(CustomerController.BASE_URL + "2");

        when(customerService.createNewCustomer(any(CustomerDTO.class))).thenReturn(customer);

        mockMvc.perform(post(CustomerController.BASE_URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customer)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstname", equalTo(FIRST_NAME)))
                .andExpect(jsonPath("$.customerUrl ", equalTo(CustomerController.BASE_URL + "2")));

    }

    @Test
    void updateCustomerByDTO() throws Exception {
        CustomerDTO customer = new CustomerDTO();
        customer.setFirstname(FIRST_NAME);
        customer.setLastname(LAST_NAME);
        customer.setCustomerUrl(CustomerController.BASE_URL + "2");

        when(customerService.updateCustomer(anyLong(), any(CustomerDTO.class))).thenReturn(customer);

        mockMvc.perform(put(CustomerController.BASE_URL + "1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname", equalTo(FIRST_NAME)))
                .andExpect(jsonPath("$.lastname", equalTo(LAST_NAME)))
                .andExpect(jsonPath("$.customerUrl ", equalTo(CustomerController.BASE_URL + "2")));
    }

    @Test
    void testPatchCustomer() throws Exception {
        //given
        CustomerDTO returnDTO = new CustomerDTO();
        returnDTO.setFirstname(FIRST_NAME);
        returnDTO.setLastname(LAST_NAME);
        returnDTO.setCustomerUrl(CustomerController.BASE_URL + "2");

        when(customerService.patchCustomer(anyLong(), any(CustomerDTO.class))).thenReturn(returnDTO);

        mockMvc.perform(patch(CustomerController.BASE_URL + "2")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(returnDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname", equalTo(FIRST_NAME)))
                .andExpect(jsonPath("$.lastname", equalTo(LAST_NAME)))
                .andExpect(jsonPath("$.customerUrl ", equalTo(CustomerController.BASE_URL + "2")));
    }

    @Test
    void testDeleteCustomerById() throws Exception {

        mockMvc.perform(delete(CustomerController.BASE_URL + "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(customerService).deleteCustomerById(anyLong());
    }


}