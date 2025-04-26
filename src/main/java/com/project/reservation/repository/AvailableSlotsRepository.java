package com.project.reservation.repository;

import com.project.reservation.domain.AvailableSlots;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AvailableSlotsRepository extends JpaRepository<AvailableSlots, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
              select asl from AvailableSlots asl 
                          where asl.startTime > CURRENT_TIMESTAMP
                          and asl.isReserved = false
                          order by asl.startTime asc limit 1
            """)
    AvailableSlots findTheNearestTime();
}
