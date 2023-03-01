package com.example.demo.business.repository.model;

import com.example.demo.business.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "`order`") // !!!
public class OrderEntity {
    @Id
    @Column(name = "order_number")
    private Long orderNr;

    @Column(name = "client_id_number", nullable = false)
    private Long clientId;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime date;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "company_number", nullable = false)
    private Long companyNr;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name = "paid", nullable = false)
    private Short paid;
}
