package com.app.api.controller;

import com.app.api.model.Revenue;
import com.app.api.service.RevenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class RevenueController {
    private final RevenueService service;

    @Autowired
    public RevenueController(RevenueService service) {
        this.service = service;
    }

    @PostMapping("/revenue")
    public ResponseEntity<?> addRevenue(@RequestParam("expense") double expense,
                                        @RequestParam("revenue") double revenue,
                                        @RequestParam("date") String date)
    {
        Long id = System.currentTimeMillis() / 1000L;
        Revenue revenueObj = new Revenue(id.toString(), LocalDate.parse(date), revenue, expense, revenue-expense);
        return new ResponseEntity<>(service.insertRevenue(revenueObj), HttpStatus.CREATED);
    }

    @GetMapping("/revenue")
    public ResponseEntity<?> getAllRevenue() {
        return new ResponseEntity<>(service.getAllRevenue(), HttpStatus.OK);
    }

    @DeleteMapping("/revenue/{id}")
    public ResponseEntity<?> deleteRevenue(@PathVariable String id) {
        return new ResponseEntity<>(service.deleteById(id), HttpStatus.OK);
    }
}
