package com.hostfully.bookings.infrastructure.persistence.jpa;

import com.hostfully.bookings.infrastructure.persistence.entity.PropertyEntity;
import org.springframework.data.repository.CrudRepository;

public interface PropertyJPARepository extends CrudRepository<PropertyEntity, Long> {
}
