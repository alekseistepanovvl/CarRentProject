package com.rent.carrent.service.car

import com.rent.carrent.dto.car.CarCreateRequestDto
import com.rent.carrent.dto.car.CarDto
import com.rent.carrent.exception.CarCreationRequestValidationException
import com.rent.carrent.exception.CarNotFoundException
import com.rent.carrent.mapper.CarMapper
import com.rent.carrent.model.Car
import com.rent.carrent.repository.CarRepository
import spock.lang.Specification
import spock.lang.Subject

class CarServiceImplTest extends Specification {
    CarRepository repository = Mock()
    CarMapper mapper = Mock()

    String REGISTER_NUMBER = 'C000'
    String CAR_ID = 'carId'

    @Subject
    CarService service = new CarServiceImpl(repository, mapper)

    def "Getting car list"() {
        given:
        def carList = [new Car()]
        def expected = [new CarDto()]
        1 * repository.findAll() >> carList
        1 * mapper.toDtoList(carList) >> expected

        when:
        def result = service.getCarList()

        then:
        expected == result
    }

    def "Add new car" () {
        given:
        CarCreateRequestDto requestDto = new CarCreateRequestDto(uniqueIdentifier: REGISTER_NUMBER)
        Car model = new Car()
        Car savedModel = new Car()
        def expected  = new CarDto()
        1 * repository.existsCarByUniqueIdentifier(REGISTER_NUMBER) >> false
        1 * mapper.toModel(requestDto) >> model
        1 * repository.save(model) >> savedModel
        1 * mapper.toDto(savedModel) >> expected


        when:
        def result = service.addCar(requestDto)

        then:
        expected == result
    }

    def "Car with taken register number cant be created"() {
        given:
        CarCreateRequestDto requestDto = new CarCreateRequestDto(uniqueIdentifier: REGISTER_NUMBER)
        1 * repository.existsCarByUniqueIdentifier(REGISTER_NUMBER) >> true

        when:
        service.addCar(requestDto)

        then:
        def e = thrown(CarCreationRequestValidationException)
        e.message == 'Car\'s register number already exists in store'
    }

    def "Update car"() {
        given:
        CarCreateRequestDto requestDto = new CarCreateRequestDto(uniqueIdentifier: REGISTER_NUMBER)
        Car car = new Car(uniqueIdentifier: REGISTER_NUMBER)
        CarDto expected = new CarDto()
        1 * repository.findById(CAR_ID) >> Optional.of(car)
        1 * mapper.toModel(requestDto, car)
        1 * mapper.toDto(car) >> expected

        when:
        def result = service.updateCar(CAR_ID, requestDto)

        then:
        expected == result
    }

    def "If update car with already taken register number, then exception will be thrown"() {
        given:
        String newRegisterNumber = 'C001'
        CarCreateRequestDto requestDto = new CarCreateRequestDto(uniqueIdentifier: newRegisterNumber)
        Car car = new Car(uniqueIdentifier: REGISTER_NUMBER)
        1 * repository.findById(CAR_ID) >> Optional.of(car)
        1 * repository.existsCarByUniqueIdentifier(newRegisterNumber) >> true

        when:
        service.updateCar(CAR_ID, requestDto)

        then:
        def e = thrown(CarCreationRequestValidationException)
        e.message == 'Car\'s register number already exists in store'
    }

    def "If update car with non existing car id, then exception will be"() {
        given:
        CarCreateRequestDto requestDto = new CarCreateRequestDto(uniqueIdentifier: REGISTER_NUMBER)
        1 * repository.findById(CAR_ID) >> Optional.empty()

        when:
        service.updateCar(CAR_ID, requestDto)

        then:
        def e = thrown(CarNotFoundException)
        e.message == 'Car with id %s doesn\'t exist'
    }

    def "Delete car"() {
        given:
        Car car = new Car()
        1 * repository.findById(CAR_ID) >> Optional.of(car)

        when:
        service.deleteCar(CAR_ID)

        then:
        1 * repository.delete(car)
    }

    def " Cant delete non existing car, exception will be thrown"() {
        given:
        1 * repository.findById(CAR_ID) >> Optional.empty()

        when:
        service.deleteCar(CAR_ID)

        then:
        def e = thrown(CarNotFoundException)
        e.message == 'Car with id %s doesn\'t exist'
    }

}
