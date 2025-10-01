package com.cab.bookingsystem.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/fare")
public class FareController {

	private static final double RATE_PER_KM = 15.0;

	@GetMapping
	public double calculate(@RequestParam double distance) {
		return Math.round(distance*RATE_PER_KM*100.0)/100.0;
	}


}
