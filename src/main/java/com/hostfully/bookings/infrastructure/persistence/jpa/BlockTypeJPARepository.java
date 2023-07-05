package com.hostfully.bookings.infrastructure.persistence.jpa;

import com.hostfully.bookings.infrastructure.persistence.entity.BlockTypeEntity;
import org.springframework.data.repository.CrudRepository;

public interface BlockTypeJPARepository extends CrudRepository<BlockTypeEntity, Long> {
}
