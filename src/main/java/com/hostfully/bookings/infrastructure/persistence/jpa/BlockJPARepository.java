package com.hostfully.bookings.infrastructure.persistence.jpa;

import com.hostfully.bookings.infrastructure.persistence.entity.BlockEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlockJPARepository extends CrudRepository<BlockEntity, Long> {

}
