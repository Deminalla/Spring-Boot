package com.example.demo.business.repository.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "client")
public class ClientEntity {
    @Id
    @Column(name = "id_number")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "surname", nullable = false)
    private String surname;

    @Column(name = "email")
    private String email;

    @Column(name = "age", nullable = false)
    private Integer age;

    @OneToMany
    @JoinColumn(name = "client_id_number") // indicates that this entity is the owner of the relationship (the referenced table has a column with a foreign key that is declared in name)
    private List<OrderEntity> orderEntityList;
}
