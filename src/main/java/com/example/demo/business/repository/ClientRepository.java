package com.example.demo.business.repository;

import com.example.demo.business.repository.model.ClientEntity;
import org.hibernate.metamodel.model.convert.spi.JpaAttributeConverter;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaAttributeConverter<ClientEntity, Long> {

}
