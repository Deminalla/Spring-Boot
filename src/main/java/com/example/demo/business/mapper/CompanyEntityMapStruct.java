package com.example.demo.business.mapper;

import com.example.demo.business.repository.model.CompanyEntity;
import com.example.demo.model.CompanyDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CompanyEntityMapStruct {
    @Mapping(source = "orderDtoList", target = "orderEntityList")
    CompanyEntity dtoToEntity(CompanyDto companyDto);
    @Mapping(source = "orderEntityList", target = "orderDtoList")
    CompanyDto entityToDto(CompanyEntity companyEntity);
}