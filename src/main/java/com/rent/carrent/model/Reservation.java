package com.rent.carrent.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "reservation")
public class Reservation {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id")
    private String id;

    @ManyToOne
    @JoinColumn(name="user_id", nullable = false, updatable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name="car_id", nullable = false, updatable = false)
    private Car car;

    @Column(name = "reservation_start")
    private LocalDateTime reservationStart;

    @Column(name = "reservation_end")
    private LocalDateTime reservationEnd;
}
