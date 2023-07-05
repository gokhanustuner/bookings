package com.hostfully.bookings.infrastructure.persistence.jpa;

import com.hostfully.bookings.domain.entity.block.BlockStatus;
import com.hostfully.bookings.infrastructure.persistence.entity.BlockEntity;
import com.hostfully.bookings.infrastructure.persistence.entity.PropertyEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface BlockJPARepository extends CrudRepository<BlockEntity, Long> {
    List<BlockEntity> findBlockEntitiesByPropertyEqualsAndStartDateIsLessThanEqualAndEndDateIsGreaterThanAndBlockStatusEquals(
            PropertyEntity property,
            Date startDate,
            Date endDate,
            BlockStatus blockStatus
    );

    BlockEntity findBlockEntitiesByIdIsAndBlockStatusEquals(
            Long id,
            BlockStatus blockStatus
    );
}
