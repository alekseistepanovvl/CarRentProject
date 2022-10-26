package com.rent.carrent.repository;

import com.rent.carrent.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car, String> {

    boolean existsCarByUniqueIdentifier(String uniqueIdentifier);
}
