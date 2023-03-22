package com.example.demo.business.mapper;

import com.example.demo.business.repository.model.CompanyEntity;
import com.example.demo.model.CompanyDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CompanyEntityMapStruct {
    CompanyEntity dtoToEntity(CompanyDto companyDto);
    CompanyDto entityToDto(CompanyEntity companyEntity);
}