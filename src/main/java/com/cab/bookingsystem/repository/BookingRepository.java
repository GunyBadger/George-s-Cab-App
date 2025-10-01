package com.cab.bookingsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cab.bookingsystem.model.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {


}
