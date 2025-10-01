package com.cab.bookingsystem.controller;

import java.time.format.DateTimeFormatter;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import com.cab.bookingsystem.model.Booking;
import com.cab.bookingsystem.model.BookingRequest;
import com.cab.bookingsystem.repository.BookingRepository;

@Controller
@RequestMapping("/book")
public class BookingController {

    private final RestTemplate rest = new RestTemplate();
    private final BookingRepository repo;

    // constructor injection
    public BookingController(BookingRepository repo) {
        this.repo = repo;
    }



    //this calls the fare micro-service and processes the booking
    private String processBooking(BookingRequest request) {

    	Double fare = rest.getForObject(
    			"http://localhost:8080/fare?distance={d}",
    			Double.class,
    			request.getDistance()
    			);

    	if (fare == null) {
    		throw new RuntimeException("Fare service is down");
    	}

    	//build and save entity
    	Booking b = new Booking();
    	b.setPassengerName(request.getPassengerName());
    	b.setPickup(request.getPickup());
    	b.setDropLocation(request.getDrop());
    	b.setPickupDateTime(request.getPickupDateTime());//this maps to dropLocation column
    	b.setDistance(request.getDistance());
    	b.setFare(fare);

    	Booking saved = repo.save(b);


    	//format the response
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    	String when = (saved.getPickupDateTime() != null)
    			? saved.getPickupDateTime().format(formatter)
    			: "now";


    	return "Booking number" + saved.getId()
    			+ " confirmed for " + saved.getPassengerName()
    			+ " from " + saved.getPickup()
    			+" to " + saved.getDropLocation()
    			+ "at" + when
    			+ ", covering " + saved.getDistance() + " km. Fare: $"
    			+ String.format("%.2f", saved.getFare());
    }


    // this posts the JSON request
    @ResponseBody
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> bookCabJson(@RequestBody BookingRequest request) {
    	return ResponseEntity.ok(processBooking(request));
    }


    /*
    // this posts the form request - Original method before refactoring to have the data sent back to home page in the Booking Details card
    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<String> bookCabForm(BookingRequest request) {
    	return ResponseEntity.ok(processBooking(request));
    }

    */


    	/*


//created for static understanding

    	 public ResponseEntity<String> bookCab(@RequestBody BookingRequest request) {

        // 1) call fare microservice
        Double fare = rest.getForObject(
                "http://localhost:8080/fare?distance={d}",
                Double.class,
                request.getDistance()
        );
        if (fare == null) fare = 0.0;

        // 2) build and save entity
        Booking b = new Booking();
        b.setPassengerName(request.getPassengerName());
        b.setPickup(request.getPickup());
        b.setDropLocation(request.getDrop());   // use dropLocation column
        b.setDistance(request.getDistance());
        b.setFare(fare);

        Booking saved = repo.save(b);

        // 3) return response
        String response = "Booking number" + saved.getId() + " confirmed for " +
                saved.getPassengerName() + " from " + saved.getPickup() +
                " to " + saved.getDropLocation() + ", covering " +
                saved.getDistance() + " km. Fare: $" + String.format("%.2f", saved.getFare());

        return ResponseEntity.ok(response);
    }

    */

    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String bookCabForm(
    		BookingRequest request,
            org.springframework.web.servlet.mvc.support.RedirectAttributes redirect) {
        String msg = processBooking(request);          // create then save then format the message
        redirect.addFlashAttribute("response", msg);   // store the data for the next request
        return "redirect:/";                           // instead of showing all the listings in a java format I return the listing to the home page in the Booking Details card
    }




    // get booking by id
    @ResponseBody
    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBooking(@PathVariable Long id) {
    			return repo.findById(id)
				.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
    }


	// get all bookings
    @ResponseBody
    @GetMapping
    public java.util.List<Booking> getAllBookings() {
    	return repo.findAll();
    }





}
