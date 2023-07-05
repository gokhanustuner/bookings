package com.hostfully.bookings.infrastructure.persistence.jpa;

import com.hostfully.bookings.infrastructure.persistence.entity.BlockReasonEntity;
import org.springframework.data.repository.CrudRepository;

public interface BlockReasonJPARepository extends CrudRepository<BlockReasonEntity, Long> {
}
