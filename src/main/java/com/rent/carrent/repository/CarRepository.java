package com.rent.carrent.repository;

import com.rent.carrent.model.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface CarRepository extends JpaRepository<Car, String> {

    boolean existsCarByUniqueIdentifier(String uniqueIdentifier);

    @Query("select c from Car c left join c.reservationList r on " +
            " :start between r.reservationStart and r.reservationEnd " +
            "   or :end between r.reservationStart and r.reservationEnd" +
            " where r.id is null")
    Page<Car> findAvailableCarToRent(LocalDateTime start, LocalDateTime end, Pageable pageable);
}
