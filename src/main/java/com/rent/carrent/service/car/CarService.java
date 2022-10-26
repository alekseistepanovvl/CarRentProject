package com.rent.carrent.service.car;

import com.rent.carrent.dto.car.CarCreateRequestDto;
import com.rent.carrent.dto.car.CarDto;

import java.util.List;

public interface CarService {

    List<CarDto> getCarList();

    CarDto addCar(CarCreateRequestDto carDto);
}
