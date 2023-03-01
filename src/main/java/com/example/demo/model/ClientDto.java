package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Email;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ClientDto {
//    @ApiModelProperty(value = ".....")
    @NotNull(message = "client id cannot be null")
    private Long id;

    @NotNull(message = "client name cannot be null")
    private String name;

    @NotNull(message = "client surname cannot be null")
    private String surname;

    @Email(regexp = "^([A-Za-z0-9]+)@([a-z]+)\\.([a-z]+)$", message = "invalid email address") // without regex its text@text
    // some letters or numbers + @ +  some small letters + . + some small letters
    private String email;

    @NotNull(message = "client age cannot be null")
    private Integer age;

    private List<OrderDto> orderDtoList;
}
