package com.project.reservation.repository;

import com.project.reservation.domain.AvailableSlots;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvailableSlotsRepository extends JpaRepository<AvailableSlots, Long> {
}
