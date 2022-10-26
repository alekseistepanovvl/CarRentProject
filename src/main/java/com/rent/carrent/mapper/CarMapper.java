package com.rent.carrent.mapper;

import com.rent.carrent.dto.car.CarCreateRequestDto;
import com.rent.carrent.dto.car.CarDto;
import com.rent.carrent.model.Car;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper
public interface CarMapper {

    CarDto toDto(Car model);

    List<CarDto> toDtoList(List<Car> modelList);

    Car toModel(CarCreateRequestDto carCreateRequestDto);

    void toModel(CarCreateRequestDto carCreateRequestDto, @MappingTarget Car model);
}
