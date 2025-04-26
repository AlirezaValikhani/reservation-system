package com.project.reservation.service.impl;

import com.project.reservation.domain.AvailableSlots;
import com.project.reservation.domain.Reservations;
import com.project.reservation.domain.User;
import com.project.reservation.exception.BadRequestException;
import com.project.reservation.repository.AvailableSlotsRepository;
import com.project.reservation.repository.ReservationsRepository;
import com.project.reservation.service.ReservationsService;
import com.project.reservation.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationsServiceImpl implements ReservationsService {

    private static final Boolean RESERVE_FLAG = true;
    private static final Boolean CANCEL_FLAG = false;

    private static final String THERE_IS_NO_AVAILABLE_SLOTS = "There is no available slots!";
    private static final String RESERVED_SUCCESSFULLY = "Reserved successfully!";
    private static final String CANCELED_SUCCESSFULLY = "Reservation has been cancelled!";
    private static final String RESERVATION_NOT_FOUND_MESSAGE = "Reservation not found";
    private static final String AUTHORIZATION = "Authorization";

    private final ReservationsRepository reservationsRepository;
    private final AvailableSlotsRepository availableSlotsRepository;
    private final HttpServletRequest request;
    private final JwtUtil jwtUtil;

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public String reserve() {

        Long userId = jwtUtil.extractUserId(request.getHeader(AUTHORIZATION).substring(7));

        AvailableSlots availableSlots = availableSlotsRepository.findTheNearestTime();

        if (availableSlots != null) {

            availableSlots.setIsReserved(RESERVE_FLAG);
            reservationsRepository.save(new Reservations(
                    new User(userId),
                    availableSlots));

            return RESERVED_SUCCESSFULLY;
        } else return THERE_IS_NO_AVAILABLE_SLOTS;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public String cancelReservation(Long id) {

        Reservations reservations = reservationsRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(RESERVATION_NOT_FOUND_MESSAGE));

        reservationsRepository.deleteById(id);
        reservations.getAvailableSlots().setIsReserved(CANCEL_FLAG);
        availableSlotsRepository.save(reservations.getAvailableSlots());

        return CANCELED_SUCCESSFULLY;
    }
}
