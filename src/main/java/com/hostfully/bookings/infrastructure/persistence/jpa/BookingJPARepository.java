package com.hostfully.bookings.infrastructure.persistence.jpa;

import com.hostfully.bookings.infrastructure.persistence.entity.BookingEntity;
import org.springframework.data.repository.CrudRepository;

public interface BookingJPARepository extends CrudRepository<BookingEntity, Long> {
}
