package com.cab.bookingsystem.model;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

public class BookingRequest {

	private String passengerName;
	private String pickup;
	private String drop;
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") // matches <input type="datetime-local">
	private LocalDateTime pickupDateTime;
	private double distance;


	public String getPassengerName() {
		return passengerName;
	}

	public void setPassengerName(String passengerName) {
		this.passengerName = passengerName;
	}

	public String getPickup() {
		return pickup;
	}

	public void setPickup(String pickup) {
		this.pickup = pickup;
	}

	public String getDrop() {
		return drop;
	}

	public void setDrop(String drop) {
		this.drop = drop;
	}

	public LocalDateTime getPickupDateTime() {
		return pickupDateTime;
	}

	public void setPickupDateTime(LocalDateTime pickupDateTime) {
		this.pickupDateTime = pickupDateTime;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}






}
