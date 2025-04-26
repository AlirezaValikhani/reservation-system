package com.project.reservation.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "reservations", schema = "reservation")
public class Reservations {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id",
            foreignKey = @ForeignKey(name = "reservations_user_fk"))
    private User user;

    @OneToOne
    @JoinColumn(name = "available_slot_id",
            foreignKey = @ForeignKey(name = "reservations_available_slots_fk"))
    private AvailableSlots availableSlots;

    public Reservations(User user, AvailableSlots availableSlots) {
        this.user = user;
        this.availableSlots = availableSlots;
    }
}
