package com.project.reservation.controller;

import com.project.reservation.service.impl.ReservationsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reserve/api")
public class ReserveController {

    private final ReservationsServiceImpl reservationsService;

    @PostMapping("/reservations")
    public ResponseEntity<String> reserve() {
        return ResponseEntity.ok()
                .body(reservationsService.reserve());
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<String> cancelReservation(@PathVariable Long id) {
        return ResponseEntity.ok()
                .body(reservationsService.cancelReservation(id));
    }
}
