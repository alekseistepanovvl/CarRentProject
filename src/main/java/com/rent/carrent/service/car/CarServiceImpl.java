package com.rent.carrent.service.car;

import com.rent.carrent.dto.car.CarCreateRequestDto;
import com.rent.carrent.dto.car.CarDto;
import com.rent.carrent.exception.CarCreationRequestValidationException;
import com.rent.carrent.mapper.CarMapper;
import com.rent.carrent.model.Car;
import com.rent.carrent.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CarServiceImpl implements CarService {
    private final CarRepository repository;
    private final CarMapper mapper;

    @Override
    public List<CarDto> getCarList() {
        List<Car> carList = repository.findAll();
        return mapper.toDtoList(carList);
    }

    @Override
    @Transactional
    public CarDto addCar(CarCreateRequestDto carDto) {
        boolean existsCarByUniqueIdentifier = repository.existsCarByUniqueIdentifier(carDto.getUniqueIdentifier());
        if (existsCarByUniqueIdentifier) {
            throw new CarCreationRequestValidationException("Car's register number already exists in store");
        }
        Car model = mapper.toModel(carDto);
        Car savedCar = repository.save(model);
        return mapper.toDto(savedCar);
    }
}
