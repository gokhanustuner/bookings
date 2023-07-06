package com.hostfully.bookings.infrastructure.persistence.repository;

import com.hostfully.bookings.domain.entity.booking.Booking;
import com.hostfully.bookings.domain.entity.booking.BookingStatus;
import com.hostfully.bookings.domain.entity.property.Property;
import com.hostfully.bookings.domain.exception.BookingNotFoundException;
import com.hostfully.bookings.domain.repository.BookingRepository;
import com.hostfully.bookings.domain.value.BookingId;
import com.hostfully.bookings.domain.value.BookingPeriod;
import com.hostfully.bookings.infrastructure.mapper.EntityMapper;
import com.hostfully.bookings.infrastructure.persistence.entity.BookingEntity;
import com.hostfully.bookings.infrastructure.persistence.entity.PropertyEntity;
import com.hostfully.bookings.infrastructure.persistence.jpa.BookingJPARepository;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public final class BookingRepositoryImpl implements BookingRepository {

    private final BookingJPARepository bookingJPARepository;

    private final EntityMapper<BookingEntity, Booking> bookingEntityMapper;

    private final EntityMapper<PropertyEntity, Property> propertyEntityMapper;

    private final EntityManagerFactory entityManagerFactory;

    @Autowired
    public BookingRepositoryImpl(
            final BookingJPARepository bookingJPARepository,
            final EntityMapper<BookingEntity, Booking> bookingEntityMapper,
            final EntityMapper<PropertyEntity, Property> propertyEntityMapper,
            final EntityManagerFactory entityManagerFactory
    ) {
        this.bookingJPARepository = bookingJPARepository;
        this.bookingEntityMapper = bookingEntityMapper;
        this.propertyEntityMapper = propertyEntityMapper;
        this.entityManagerFactory = entityManagerFactory;
    }
    @Override
    public Booking save(final Booking booking) {
        return bookingEntityMapper.JPAEntityToDomainEntity(
                bookingJPARepository.save(
                    bookingEntityMapper.domainEntityToJPAEntity(booking)
                )
        );
    }

    @Override
    public void saveAll(List<Booking> bookings) {
        bookingJPARepository.saveAll(bookingEntityMapper.domainEntityToJPAEntity(bookings));
    }

    @Override
    public Booking findById(BookingId bookingId) {
        final BookingEntity bookingEntity = bookingJPARepository.findById(bookingId.value()).orElseThrow(
                () -> new BookingNotFoundException(
                        String.format("Booking with id %s not found", bookingId)
                )
        );

        return bookingEntityMapper.JPAEntityToDomainEntity(bookingEntity);
    }

    @Override
    public Booking findActiveBookingById(BookingId bookingId) {
        final BookingEntity bookingEntity =
                bookingJPARepository.findBookingEntitiesByIdIsAndStatusEquals(
                        bookingId.value(),
                        BookingStatus.ACTIVE
                );

        if (bookingEntity == null)
            throw new BookingNotFoundException(String.format("Booking with id %s not found", bookingId));

        return bookingEntityMapper.JPAEntityToDomainEntity(bookingEntity);
    }

    @Override
    public Booking findCancelledBookingById(BookingId bookingId) {
        final BookingEntity bookingEntity =
                bookingJPARepository.findBookingEntitiesByIdIsAndStatusEquals(
                        bookingId.value(),
                        BookingStatus.CANCELLED
                );

        if (bookingEntity == null)
            throw new BookingNotFoundException(String.format("Booking with id %s not found", bookingId));

        return bookingEntityMapper.JPAEntityToDomainEntity(bookingEntity);
    }

    @Override
    public List<Booking> findAll() {
        return bookingEntityMapper.JPAEntityToDomainEntity(
                (List<BookingEntity>) bookingJPARepository.findAll()
        );
    }

    @Override
    public List<Booking> findActiveBookingsByPropertyAndBookingPeriod(Property property, BookingPeriod bookingPeriod) {
        return bookingEntityMapper.JPAEntityToDomainEntity(
                bookingJPARepository
                        .findBookingEntitiesByPropertyEqualsAndStartDateIsLessThanAndEndDateIsGreaterThanAndStatusEquals(
                            propertyEntityMapper.domainEntityToJPAEntity(property),
                            bookingPeriod.endDate(),
                            bookingPeriod.startDate(),
                            BookingStatus.ACTIVE
                        )
        );
    }
}
