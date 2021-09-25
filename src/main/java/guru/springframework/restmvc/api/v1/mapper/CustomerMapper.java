package guru.springframework.restmvc.api.v1.mapper;

import guru.springframework.restmvc.api.v1.model.CustomerDTO;
import guru.springframework.restmvc.domain.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    @Mapping(source = "id", target = "id")
    CustomerDTO customerToCustomerDTO(Customer customer);
}
