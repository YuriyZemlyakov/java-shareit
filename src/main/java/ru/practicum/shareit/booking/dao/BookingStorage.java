package ru.practicum.shareit.booking.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.enums.State;

import java.awt.print.Book;
import java.time.LocalDateTime;
import java.util.Collection;

public interface BookingStorage extends JpaRepository<Booking,Long> {
    @Query(value = "select * from bookings b where b.user_id = :userId", nativeQuery = true)
    public Collection<Booking> getAllBookingsByCurrentUser(long userId);
    @Query(value = "select * from bookings b where b.status = 'APPROVED' and b.user_id = :userId and now() " +
            "between b.start_date and b.end_date", nativeQuery = true)
    public Collection<Booking> getCurrentBookings(long userId);
    @Query(value = "select * from bookings b where b.status = 'APPROVED' and b.user_id = :userId " +
            "and now() < b.start_date", nativeQuery = true)
    public Collection<Booking> getAllFutureBookingsByCurrentUser(long userId);
    @Query(value = "select * from bookings b where b.status = 'APPROVED' and b.user_id = :userId " +
            "and now() > b.end_date", nativeQuery = true)
    public Collection<Booking> getAllPastBookingsByCurrentUser(long userId);
    @Query(value = "select * from bookings b where b.status = 'WAITING' and b.user_id = :userId ", nativeQuery = true)
    public Collection<Booking> getAllWaitingBookingsByCurrentUser(long userId);
    @Query(value = "select * from bookings b where b.status = 'REJECTED' and b.user_id = :userId ", nativeQuery = true)
    public Collection<Booking> getAllRejectedBookingsByCurrentUser(long userId);

    @Query("select b from Booking b join b.item i join i.owner o where o.id = :ownerId")
    public Collection<Booking> getOwnerBookings(long ownerId);
    @Query(value = "select * from bookings b join items i on b.item_id = i.id where i.user_id = :ownerId" +
            " and b.status = 'APPROVED' and now() between b.start_date and b.end_date", nativeQuery = true)
    public Collection<Booking> getOwnerCurrentBookings(long ownerId);
    @Query(value = "select * from bookings b join items i on b.item_id = i.id where i.user_id = :ownerId" +
            "and b.status = 'APPROVED' and now() < b.start_date", nativeQuery = true)
    public Collection<Booking> getOwnerFutureBookings(long ownerId);
    @Query(value = "select * from bookings b join items i on b.item_id = i.id where i.user_id = :ownerId" +
            "and b.status = 'APPROVED' and now() > b.end_date", nativeQuery = true)
    public Collection<Booking> getOwnerPastBookings(long ownerId);
    @Query(value = "select * from bookings b join items i on b.item_id = i.id where i.user_id = :ownerId" +
            "and b.status = 'WAITING'", nativeQuery = true)
    public Collection<Booking> getOwnerWaitingBookings(long ownerId);
    @Query(value = "select * from bookings b join items i on b.item_id = i.id where i.user_id = :ownerId" +
            "and b.status = 'REJECTED'", nativeQuery = true)
    public Collection<Booking> getOwnerRejectedBookings(long ownerId);

    @Query(value = "select b.* from Booking b join items i on b.item_id = i.id where i.id = :itemId", nativeQuery = true)
    public Collection<Booking> getBookingsByItem(long itemId);
}
