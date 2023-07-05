package com.hostfully.bookings.infrastructure.persistence.jpa;

import com.hostfully.bookings.domain.entity.booking.BookingStatus;
import com.hostfully.bookings.infrastructure.persistence.entity.BookingEntity;
import com.hostfully.bookings.infrastructure.persistence.entity.PropertyEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface BookingJPARepository extends CrudRepository<BookingEntity, Long> {

    List<BookingEntity> findBookingEntitiesByPropertyEqualsAndStartDateIsLessThanEqualAndEndDateIsGreaterThanAndStatusEquals(
            PropertyEntity property,
            Date startDate,
            Date endDate,
            BookingStatus status
    );
}
