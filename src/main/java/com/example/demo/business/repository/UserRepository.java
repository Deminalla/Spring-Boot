package com.example.demo.business.repository;

import com.example.demo.business.repository.model.UserEntity;
import org.hibernate.metamodel.model.convert.spi.JpaAttributeConverter;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaAttributeConverter<UserEntity, Long> {

}
