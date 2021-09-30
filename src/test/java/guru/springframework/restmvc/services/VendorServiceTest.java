package guru.springframework.restmvc.services;

import guru.springframework.restmvc.api.v1.mapper.VendorMapper;
import guru.springframework.restmvc.api.v1.model.VendorDTO;
import guru.springframework.restmvc.controllers.v1.VendorController;
import guru.springframework.restmvc.domain.Vendor;
import guru.springframework.restmvc.repositories.VendorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class VendorServiceTest {

    public static final long ID = 1L;
    public static final String MAXI = "Maxi";
    public static final String RODA = "Roda";

    @Mock
    VendorRepository vendorRepository;

    VendorService vendorService;

    @BeforeEach
    void setUp() {
        vendorRepository = Mockito.mock(VendorRepository.class);
        vendorService = new VendorServiceImpl(vendorRepository, VendorMapper.INSTANCE);
    }

    @Test
    void getAllVendors() {
        //given
        Vendor vendor = new Vendor();
        vendor.setName(MAXI);

        Vendor vendor1 = new Vendor();
        vendor.setName(RODA);

        List<Vendor> vendors = Arrays.asList(vendor, vendor1);

        when(vendorRepository.findAll()).thenReturn(vendors);

        //when
        List<VendorDTO> allVendors = vendorService.getAllVendors();

        //then
        assertEquals(2, allVendors.size());
        verify(vendorRepository, times(1)).findAll();

    }

    @Test
    void getVendorById() {
        //given
        Vendor vendor = new Vendor();
        vendor.setId(ID);
        vendor.setName(MAXI);

        when(vendorRepository.findById(anyLong())).thenReturn(Optional.of(vendor));

        //when
        VendorDTO vendorById = vendorService.getVendorById(ID);

        //then
        assertNotNull(vendorById);
        assertEquals(MAXI, vendor.getName());
        assertEquals(VendorController.BASE_URL + vendor.getId(), vendorById.getVendorUrl());
        verify(vendorRepository, times(1)).findById(anyLong());
    }

    @Test
    void createNewVendor() {
        //given
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(MAXI);

        Vendor vendor = new Vendor();
        vendor.setId(ID);
        vendor.setName(MAXI);

        when(vendorRepository.save(any(Vendor.class))).thenReturn(vendor);

        //when
        VendorDTO returnedDTO = vendorService.createNewVendor(vendorDTO);

        //then
        assertNotNull(returnedDTO);
        assertEquals(vendorDTO.getName(), returnedDTO.getName());
        assertEquals(VendorController.BASE_URL + vendor.getId(), returnedDTO.getVendorUrl());
        verify(vendorRepository, times(1)).save(any(Vendor.class));
    }

    @Test
    void updateVendor() {
        //given
        Vendor vendor = new Vendor();
        vendor.setId(ID);
        vendor.setName(MAXI);

        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(MAXI);

        when(vendorRepository.save(any(Vendor.class))).thenReturn(vendor);

        //when
        VendorDTO returnDTO = vendorService.updateVendor(ID, vendorDTO);

        //then
        assertEquals(vendorDTO.getName(), returnDTO.getName());
        assertEquals(VendorController.BASE_URL + vendor.getId(),  returnDTO.getVendorUrl());
        verify(vendorRepository, times(1)).save(any(Vendor.class));
    }

    @Test
    void patchVendor() {
        //given
        Vendor vendor = new Vendor();
        vendor.setId(ID);
        vendor.setName(MAXI);

        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(MAXI);

        when(vendorRepository.findById(anyLong())).thenReturn(Optional.of(vendor));
        when(vendorRepository.save(any(Vendor.class))).thenReturn(vendor);

        //when
        VendorDTO returnDTO = vendorService.patchVendor(ID, vendorDTO);

        //then
        assertEquals(vendorDTO.getName(), returnDTO.getName());
        assertEquals(VendorController.BASE_URL + vendor.getId(), returnDTO.getVendorUrl());
        verify(vendorRepository, times(1)).save(any(Vendor.class));
    }

    @Test
    void deleteVendorById() {
        vendorRepository.deleteById(ID);

        verify(vendorRepository, times(1)).deleteById(anyLong());
    }
}