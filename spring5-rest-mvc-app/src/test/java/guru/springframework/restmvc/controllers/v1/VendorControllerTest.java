package guru.springframework.restmvc.controllers.v1;

import guru.springframework.restmvc.api.v1.model.VendorDTO;
import guru.springframework.restmvc.controllers.RestResponseEntityExceptionHandler;
import guru.springframework.restmvc.services.ResourceNotFoundException;
import guru.springframework.restmvc.services.VendorService;
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
class VendorControllerTest extends AbstractRestControllerTest{

    public static final long ID = 1L;
    public static final String MAXI = "Maxi";
    public static final String RODA = "Roda";

    @Mock
    VendorService vendorService;

    @InjectMocks
    VendorController vendorController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(vendorController)
                .setControllerAdvice(new RestResponseEntityExceptionHandler())
                .build();
    }

    @Test
    void testListVendors() throws Exception {

        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(MAXI);

        VendorDTO vendorDTO1 = new VendorDTO();
        vendorDTO.setName(RODA);

        when(vendorService.getAllVendors()).thenReturn(Arrays.asList(vendorDTO, vendorDTO1));

        mockMvc.perform(get(VendorController.BASE_URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vendors", hasSize(2)));

    }

    @Test
    void getVendorById() throws Exception {

        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(MAXI);
        vendorDTO.setVendorUrl(VendorController.BASE_URL + 3);

        when(vendorService.getVendorById(anyLong())).thenReturn(vendorDTO);

        mockMvc.perform(get(VendorController.BASE_URL + "3")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(MAXI)))
                .andExpect(jsonPath("$.vendor_url", equalTo(vendorDTO.getVendorUrl())));
    }

    @Test
    void getVendorByIdNotFound() throws Exception {
        when(vendorService.getVendorById(anyLong())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get(VendorController.BASE_URL + "3")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void createNewVendor() throws Exception {
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(MAXI);
        vendorDTO.setVendorUrl(VendorController.BASE_URL + ID);

        when(vendorService.createNewVendor(any(VendorDTO.class))).thenReturn(vendorDTO);

        mockMvc.perform(post(VendorController.BASE_URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(vendorDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", equalTo(MAXI)))
                .andExpect(jsonPath("$.vendor_url", equalTo(vendorDTO.getVendorUrl())));

    }

    @Test
    void updateVendorByDTO() throws Exception {
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(MAXI);
        vendorDTO.setVendorUrl(VendorController.BASE_URL + ID);

        when(vendorService.updateVendor(anyLong(), any(VendorDTO.class))).thenReturn(vendorDTO);

        mockMvc.perform(put(VendorController.BASE_URL + "1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(vendorDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(vendorDTO.getName())))
                .andExpect(jsonPath("$.vendor_url", equalTo(vendorDTO.getVendorUrl())));
    }

    @Test
    void patchVendor() throws Exception {
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(MAXI);
        vendorDTO.setVendorUrl(VendorController.BASE_URL + ID);

        when(vendorService.patchVendor(anyLong(), any(VendorDTO.class))).thenReturn(vendorDTO);

        mockMvc.perform(patch(VendorController.BASE_URL + "1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(vendorDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(vendorDTO.getName())))
                .andExpect(jsonPath("$.vendor_url", equalTo(vendorDTO.getVendorUrl())));
    }

    @Test
    void deleteVendor() throws Exception {
        mockMvc.perform(delete(VendorController.BASE_URL + "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(vendorService).deleteVendorById(anyLong());
    }
}