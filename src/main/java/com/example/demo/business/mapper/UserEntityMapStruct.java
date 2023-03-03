package com.example.demo.business.mapper;

import com.example.demo.business.repository.model.UserEntity;
import com.example.demo.model.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserEntityMapStruct {
    @Mapping(source = "orderEntityList", target = "orderDtoList")
    UserDto entityToDto(UserEntity userEntity);

    @Mapping(source = "orderDtoList", target = "orderEntityList")
    UserEntity dtoToEntity(UserDto userDto);
}
