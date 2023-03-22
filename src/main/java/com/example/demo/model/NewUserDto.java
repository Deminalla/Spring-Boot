package com.example.demo.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel(value = "Model of new user information")
public class NewUserDto {
    @ApiModelProperty(value = "Username of the user")
    @NotBlank(message = "username cannot be empty")
    private String username;

    @ApiModelProperty(value = "Email of the user")
    @Email(regexp = "^([A-Za-z0-9]+)@([a-z]+)\\.([a-z]+)$", message = "invalid email address") // without regex its text@text
    private String email;

    @ApiModelProperty(value = "Password of the user")
    @Min(value = 8, message = "password length should not be less that 8 characters")
    private String password;
}
