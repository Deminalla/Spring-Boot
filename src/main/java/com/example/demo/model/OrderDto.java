package com.example.demo.model;

import com.example.demo.business.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderDto {
    @NotNull
    private Long id;

    @NotNull
    private Long userId;

    @NotNull
    private LocalDateTime date;

    @NotNull
    @Positive (message = "price has to be positive")
    private BigDecimal price;

    @NotNull
    private Long companyId;

    @NotNull
    @Pattern(regexp = "^(NOT_STARTED|IN_PROGRESS|FINISHED)$", message = "valid statuses: NOT_STARTED, IN_PROGRESS, FINISHED")
    private OrderStatus status;
}
