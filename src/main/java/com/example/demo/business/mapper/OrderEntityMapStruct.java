package com.example.demo.business.mapper;

import com.example.demo.business.repository.model.OrderEntity;
import com.example.demo.model.OrderDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderEntityMapStruct {
    OrderDto entityToDto (OrderEntity orderEntity);

    OrderEntity dtoToEntity (OrderDto orderDto);
}
