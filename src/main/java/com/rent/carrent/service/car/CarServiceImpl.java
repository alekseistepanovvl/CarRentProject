package com.rent.carrent.service.car;

import com.rent.carrent.dto.car.CarCreateRequestDto;
import com.rent.carrent.dto.car.CarDto;
import com.rent.carrent.exception.CarCreationRequestValidationException;
import com.rent.carrent.exception.CarNotFoundException;
import com.rent.carrent.mapper.CarMapper;
import com.rent.carrent.model.Car;
import com.rent.carrent.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
        checkRegisterNumberAlreadyExists(carDto.getUniqueIdentifier());
        Car model = mapper.toModel(carDto);
        Car savedCar = repository.save(model);
        return mapper.toDto(savedCar);
    }

    @Override
    @Transactional
    public CarDto updateCar(String id, CarCreateRequestDto carDto) {
        Car car = repository.findById(id)
                .orElseThrow(() -> new CarNotFoundException("Car with id %s doesn't exist", id));
        if (!car.getUniqueIdentifier().equals(carDto.getUniqueIdentifier())) {
            checkRegisterNumberAlreadyExists(carDto.getUniqueIdentifier());
        }
        mapper.toModel(carDto, car);
        return mapper.toDto(car);
    }

    private void checkRegisterNumberAlreadyExists(String uniqueIdentifier) {
        boolean existsCarByUniqueIdentifier = repository.existsCarByUniqueIdentifier(uniqueIdentifier);
        if (existsCarByUniqueIdentifier) {
            throw new CarCreationRequestValidationException("Car's register number already exists in store");
        }
    }
}
