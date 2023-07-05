package com.hostfully.bookings.infrastructure.persistence.repository;

import com.hostfully.bookings.domain.entity.Booking;
import com.hostfully.bookings.domain.exception.BookingNotFoundException;
import com.hostfully.bookings.domain.repository.BookingRepository;
import com.hostfully.bookings.domain.value.BookingId;
import com.hostfully.bookings.infrastructure.mapper.EntityMapper;
import com.hostfully.bookings.infrastructure.persistence.entity.BookingEntity;
import com.hostfully.bookings.infrastructure.persistence.jpa.BookingJPARepository;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public final class BookingRepositoryImpl implements BookingRepository {

    private final BookingJPARepository bookingJPARepository;

    private final EntityMapper<BookingEntity, Booking> bookingEntityMapper;

    private final EntityManagerFactory entityManagerFactory;

    @Autowired
    public BookingRepositoryImpl(
            final BookingJPARepository bookingJPARepository,
            final EntityMapper<BookingEntity, Booking> bookingEntityMapper,
            final EntityManagerFactory entityManagerFactory
    ) {
        this.bookingJPARepository = bookingJPARepository;
        this.bookingEntityMapper = bookingEntityMapper;
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
    public Booking findById(BookingId bookingId) {
        final List<Object[]> bookingQueryResultList = entityManagerFactory
                .createEntityManager()
                .createQuery(
                        "SELECT b, p FROM BookingEntity b, PropertyEntity p WHERE b.property = p AND b.id = :bookingId"
                )
                .setParameter("bookingId", bookingId.value())
                .getResultList();

        final BookingEntity bookingEntity =
                (BookingEntity) Arrays
                        .stream(
                            bookingQueryResultList
                                    .stream()
                                    .findFirst()
                                    .orElseThrow(
                                        () -> new BookingNotFoundException(
                                            String.format("Booking with id %s not found", bookingId)
                                        )
                                    )
                        )
                        .findFirst()
                        .orElseThrow();

        return bookingEntityMapper.JPAEntityToDomainEntity(bookingEntity);
    }

    @Override
    public List<Booking> findAll() {
        return bookingEntityMapper.JPAEntityToDomainEntity(
                (List<BookingEntity>) bookingJPARepository.findAll()
        );
    }
}
