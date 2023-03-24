package com.example.demo.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel(value = "Model of company")
public class CompanyDto {
    @ApiModelProperty(value = "Unique id of the company")
    @NotNull
    private Long id;

    @ApiModelProperty(value = "Name of the company")
    @NotNull
    private String name;

    @ApiModelProperty(value = "City in which the company is located in")
    @NotNull
    private String city;

    @ApiModelProperty(value = "Password of the user")
    @Min(value = 8, message = "password length should not be less that 8 characters")
    private String password;

    @ApiModelProperty(value = "List of the company's accepted orders")
    private List<OrderDto> orderDtoList;
}
