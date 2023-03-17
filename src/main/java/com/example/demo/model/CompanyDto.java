package com.example.demo.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

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
}
