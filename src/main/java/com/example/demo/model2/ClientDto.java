package com.example.demo.model2;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ClientDto {
    @NotNull(message = "client id cannot be null")
    private Long id;

    @NotNull(message = "client name cannot be null")
    private String name;

    @NotNull(message = "client surname cannot be null")
    private String surname;

    // the dto classes do not have to be identical to the one in the original service
    // as long as the field names are the same, and you have the one that you need

//    @NotNull(message = "client age cannot be null")
//    private Integer age;
}
