package com.example.demo.model;

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
public class UserDto {
//    @ApiModelProperty(value = ".....")
    @NotNull(message = "user id cannot be null")
    private Long id;

    @NotBlank(message = "username cannot be empty")
    private String username;

    @Email(regexp = "^([A-Za-z0-9]+)@([a-z]+)\\.([a-z]+)$", message = "invalid email address") // without regex its text@text
    // some letters or numbers + @ +  some small letters + . + some small letters
    private String email;

    @Min(value = 8, message = "password length should not be less that 8 characters")
    private String password;

    @NotNull(message = "user balance cannot be null")
    private BigDecimal balance;

    private List<OrderDto> orderDtoList;
}
