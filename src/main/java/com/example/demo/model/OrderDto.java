package com.example.demo.model;

import com.example.demo.business.enums.OrderStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value = "Model of order")
public class OrderDto {
    @ApiModelProperty(value = "Unique id of the order")
    @NotNull
    private Long id;

    @ApiModelProperty(value = "Id of the user who requested the order")
    @NotNull
    private Long userId;

    @ApiModelProperty(value = "Date and time when the order was made")
    @NotNull
    private LocalDateTime date;

    @ApiModelProperty(value = "Price of the order")
    @NotNull
    @Positive (message = "price has to be positive")
    private BigDecimal price;

    @ApiModelProperty(value = "The id of the company who accepted the order")
    @NotNull
    private Long companyId;

    @ApiModelProperty(value = "Status of the order")
    @NotNull
    @Pattern(regexp = "^(NOT_STARTED|IN_PROGRESS|FINISHED)$", message = "valid statuses: NOT_STARTED, IN_PROGRESS, FINISHED")
    private OrderStatus status;
}
