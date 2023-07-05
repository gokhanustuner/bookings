package com.hostfully.bookings.infrastructure.mapper;

import com.hostfully.bookings.domain.entity.Booking;
import com.hostfully.bookings.domain.entity.Property;
import com.hostfully.bookings.domain.value.BookingId;
import com.hostfully.bookings.domain.value.BookingPeriod;
import com.hostfully.bookings.infrastructure.persistence.entity.BookingEntity;
import com.hostfully.bookings.infrastructure.persistence.entity.PropertyEntity;
import org.springframework.stereotype.Component;

@Component
public class BookingEntityMapper implements EntityMapper<BookingEntity, Booking> {
    @Override
    public Booking JPAEntityToDomainEntity(final BookingEntity JPABookingEntity) {
        return Booking.of(
                BookingId.of(JPABookingEntity.getId()),
                BookingPeriod.of(JPABookingEntity.getStartDate(), JPABookingEntity.getEndDate()),
                Property.of(JPABookingEntity.getPropertyId(), JPABookingEntity.getPropertyName())
        );
    }

    @Override
    public BookingEntity domainEntityToJPAEntity(final Booking domainBookingEntity) {
        final BookingEntity bookingEntity = new BookingEntity();
        final PropertyEntity propertyEntity = new PropertyEntity();

        if (domainBookingEntity.getBookingId() != null) {
            bookingEntity.setId(domainBookingEntity.getBookingId().value());
        }
        bookingEntity.setStartDate(domainBookingEntity.getBookingPeriod().startDate());
        bookingEntity.setEndDate(domainBookingEntity.getBookingPeriod().endDate());

        propertyEntity.setId(domainBookingEntity.getPropertyId().value());
        bookingEntity.setProperty(propertyEntity);

        return bookingEntity;
    }
}
