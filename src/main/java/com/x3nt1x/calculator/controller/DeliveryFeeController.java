package com.x3nt1x.calculator.controller;

import com.x3nt1x.calculator.service.DeliveryFeeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/fee")
public class DeliveryFeeController
{
    private final DeliveryFeeService deliveryFeeService;

    /**
    * GET /?city={city}&vehicle={vehicle}
    * GET /?city={city}&vehicle={vehicle}&time={time}
    *
    * @param city    (Tallinn / Tartu / Pärnu)
    * @param vehicle (Car / Bike / Scooter)
    * @param time    format: yyyy-MM-dd-HH (example: 2023-03-25-16)
    * @return delivery fee based on regional base fee, vehicle type, and weather conditions
    */
    @GetMapping("/")
    public @ResponseBody ResponseEntity<String> calculateDeliveryFee(@RequestParam("city") String city,
                                                                     @RequestParam("vehicle") String vehicle,
                                                                     @RequestParam("time") Optional<String> time)
    {
        return ResponseEntity.ok(deliveryFeeService.calculateDeliveryFee(city, vehicle, time));
    }
}