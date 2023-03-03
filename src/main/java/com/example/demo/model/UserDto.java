package com.example.demo.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Email;
import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel(value = "Model of user")
public class UserDto {
    @ApiModelProperty(value = "Unique id of the user")
    @NotNull(message = "user id cannot be null")
    private Long id;

    @ApiModelProperty(value = "Username of the user")
    @NotBlank(message = "username cannot be empty")
    private String username;

    @ApiModelProperty(value = "Email of the user")
    @Email(regexp = "^([A-Za-z0-9]+)@([a-z]+)\\.([a-z]+)$", message = "invalid email address") // without regex its text@text
    // some letters or numbers + @ +  some small letters + . + some small letters
    private String email;

    @ApiModelProperty(value = "Password of the user")
    @Min(value = 8, message = "password length should not be less that 8 characters")
    private String password;

    @ApiModelProperty(value = "Balance of the user")
    @NotNull(message = "user balance cannot be null")
    private BigDecimal balance;

    @ApiModelProperty(value = "List of the user's orders")
    private List<OrderDto> orderDtoList;
}
