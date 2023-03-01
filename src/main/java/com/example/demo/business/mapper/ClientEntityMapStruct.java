package com.example.demo.business.mapper;

import com.example.demo.business.repository.model.ClientEntity;
import com.example.demo.model.ClientDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClientEntityMapStruct {
    @Mapping(source = "orderEntityList", target = "orderDtoList")
    ClientDto entityToDto(ClientEntity clientEntity);

    @Mapping(source = "orderDtoList", target = "orderEntityList")
    ClientEntity dtoToEntity(ClientDto clientDto);
}
