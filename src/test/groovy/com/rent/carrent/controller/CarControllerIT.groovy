package com.rent.carrent.controller

import com.rent.carrent.dto.car.CarCreateRequestDto
import com.rent.carrent.dto.car.CarDto
import com.rent.carrent.dto.error.ErrorDto
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class CarControllerIT extends BaseControllerSpecification {

    @Value('http://localhost:${local.server.port}/api/v1/car')
    String serviceCarUrl

    String model = 'carModel'
    String make = 'make'
    String uniqueIdentifier = 'C789'

    def "Get list of cars"() {
        when:
        ResponseEntity<List<CarDto>> result = restTemplate.exchange(serviceCarUrl, HttpMethod.GET, null, new ParameterizedTypeReference<List<CarDto>>() {})

        then:
        result.statusCode == HttpStatus.OK
        def carList = result.body
        carList.size() == 10
    }

    def "Create car"() {
        given:
        CarCreateRequestDto car = new CarCreateRequestDto(
                'model': model,
                'make': make,
                'uniqueIdentifier': uniqueIdentifier
        )

        when:
        ResponseEntity<CarDto> result = restTemplate.postForEntity(serviceCarUrl, car, CarDto.class)

        then:
        result.statusCode == HttpStatus.OK
        def carDto = result.body
        carDto.id
        carDto.make == make
        carDto.model == model
        carDto.uniqueIdentifier == uniqueIdentifier
    }

    def "Cant create car without make"() {
        given:
        CarCreateRequestDto car = new CarCreateRequestDto(
                'model': model,
                'uniqueIdentifier': uniqueIdentifier
        )

        when:
        ResponseEntity<ErrorDto> result = restTemplate.postForEntity(serviceCarUrl, car, ErrorDto.class)

        then:
        result.statusCode == HttpStatus.BAD_REQUEST
        result.body.errorMessage.trim() == 'make:make name must me filled'
    }

    def "Cant create car without model"() {
        given:
        CarCreateRequestDto car = new CarCreateRequestDto(
                'make': make,
                'uniqueIdentifier': uniqueIdentifier
        )

        when:
        ResponseEntity<ErrorDto> result = restTemplate.postForEntity(serviceCarUrl, car, ErrorDto.class)

        then:
        result.statusCode == HttpStatus.BAD_REQUEST
        result.body.errorMessage.trim() == 'model:model name must be filled'
    }

    def "Cant create car without register number"() {
        given:
        CarCreateRequestDto car = new CarCreateRequestDto(
                'make': make,
                'model': model
        )

        when:
        ResponseEntity<ErrorDto> result = restTemplate.postForEntity(serviceCarUrl, car, ErrorDto.class)

        then:
        result.statusCode == HttpStatus.BAD_REQUEST
        result.body.errorMessage.trim() == 'uniqueIdentifier:Register number  must be filled'
    }

    def "Cant create car with register number that dont match patter"() {
        given:
        CarCreateRequestDto car = new CarCreateRequestDto(
                'make': make,
                'model': model,
                'uniqueIdentifier': 'C84C'
        )

        when:
        ResponseEntity<ErrorDto> result = restTemplate.postForEntity(serviceCarUrl, car, ErrorDto.class)

        then:
        result.statusCode == HttpStatus.BAD_REQUEST
        result.body.errorMessage.trim() == 'uniqueIdentifier:Car\'s register number have to math CXXX pattern'
    }


}
