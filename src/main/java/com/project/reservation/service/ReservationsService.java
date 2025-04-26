package com.project.reservation.service;

import org.springframework.web.bind.annotation.PathVariable;

public interface ReservationsService {

    String reserve();

    String cancelReservation(@PathVariable Long id);
}
