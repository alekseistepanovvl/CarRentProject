package com.rent.carrent.service.reservation

import com.rent.carrent.dto.reservation.ReservationRequestDto
import com.rent.carrent.dto.reservation.UserReservationList
import com.rent.carrent.exception.CarReservationException
import com.rent.carrent.exception.NoAvailableCarForReservationException
import com.rent.carrent.exception.UserNotFoundException
import com.rent.carrent.mapper.ReservationMapper
import com.rent.carrent.mapper.UserReservationListMapper
import com.rent.carrent.model.Car
import com.rent.carrent.model.Reservation
import com.rent.carrent.model.User
import com.rent.carrent.repository.CarRepository
import com.rent.carrent.repository.ReservationRepository
import com.rent.carrent.repository.UserRepository
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDateTime

class ReservationServiceImplTest extends Specification {

    UserRepository userRepository = Mock()
    CarRepository carRepository = Mock()
    ReservationRepository reservationRepository = Mock()
    ReservationMapper mapper = Mock()
    UserReservationListMapper userReservationListMapper = Mock()

    String USER_ID = 'userId'
    User USER = new User()

    @Subject
    ReservationService service = new ReservationServiceImpl(userRepository, carRepository, reservationRepository,
        mapper, userReservationListMapper)

    def "Get upcoming reservation list"() {
        given:
        User user = new User(reservationList: [])
        UserReservationList expected = new UserReservationList()
        1 * userRepository.getUpcomingReservationList(USER_ID) >> user
        1 * userReservationListMapper.toDtoList(user.getReservationList(), user) >> expected

        when:
        def result = service.getUpcomingReservationList(USER_ID)

        then:
        expected == result
    }

    def "If user not exist then exception will be"() {
        given:
        1 * userRepository.getUpcomingReservationList(USER_ID) >> null

        when:
        service.getUpcomingReservationList(USER_ID)

        then:
        def e = thrown(UserNotFoundException)
        e.message == 'User with %s not found'
    }

    def "Reserve car with start date after end date will throw exception"() {
        given:
        ReservationRequestDto requestDto = new ReservationRequestDto(startReservation: LocalDateTime.now().plusHours(10),
                endReservation: LocalDateTime.now())

        when:
        service.reserveCar(USER_ID, requestDto)

        then:
        def e = thrown(CarReservationException)
        e.message == 'End reservation time must be after start reservation time'
    }

    def "Reservation must be taken up to 24 hours ahead"() {
        given:
        ReservationRequestDto requestDto = new ReservationRequestDto(startReservation: LocalDateTime.now().plusHours(2),
                endReservation: LocalDateTime.now().plusHours(4))

        when:
        service.reserveCar(USER_ID, requestDto)

        then:
        def e = thrown(CarReservationException)
        e.message == 'The reservation can be taken up to 24 hours ahead.'
    }

    def "Reservation duration can take up to 2 hours"() {
        given:
        ReservationRequestDto requestDto = new ReservationRequestDto(startReservation: LocalDateTime.now().plusDays(2),
                endReservation: LocalDateTime.now().plusDays(2).plusHours(3))

        when:
        service.reserveCar(USER_ID, requestDto)

        then:
        def e = thrown(CarReservationException)
        e.message == 'The reservation duration can take up to 2 hours.'
    }

    def "Try reserve car with non existing user"() {
        given:
        ReservationRequestDto requestDto = new ReservationRequestDto(startReservation: LocalDateTime.now().plusDays(2),
                endReservation: LocalDateTime.now().plusDays(2).plusHours(1))
        1 * userRepository.findById(USER_ID) >> Optional.empty()

        when:
        service.reserveCar(USER_ID, requestDto)

        then:
        def e = thrown(UserNotFoundException)
        e.message == 'User with id %s not found'
    }

    def "If there are no available car then exception will be thrown"() {
        given:
        def startReservation = LocalDateTime.now().plusDays(2)
        def endReservation = LocalDateTime.now().plusDays(2).plusHours(1)
        ReservationRequestDto requestDto = new ReservationRequestDto(startReservation: startReservation,
                endReservation: endReservation)
        1 * userRepository.findById(USER_ID) >> Optional.of(USER)
        1 * carRepository.findAvailableCarToRent(startReservation, endReservation, Pageable.ofSize(1)) >> new PageImpl([])

        when:
        service.reserveCar(USER_ID, requestDto)

        then:
        def e = thrown(NoAvailableCarForReservationException)
        e.message == 'No available cars to reserve'
    }

    def "Reserve car"() {
        given:
        def startReservation = LocalDateTime.now().plusDays(2)
        def endReservation = LocalDateTime.now().plusDays(2).plusHours(1)
        ReservationRequestDto requestDto = new ReservationRequestDto(startReservation: startReservation,
                endReservation: endReservation)
        Car car = new Car()
        Reservation reservation = new Reservation()
        Reservation savedReservation = new Reservation()
        UserReservationList expected = new UserReservationList()
        1 * userRepository.findById(USER_ID) >> Optional.of(USER)
        1 * carRepository.findAvailableCarToRent(startReservation, endReservation, Pageable.ofSize(1))
                >> new PageImpl([car])
        1 * mapper.toReservation(car, USER, startReservation, endReservation) >> reservation
        1 * reservationRepository.save(reservation) >> savedReservation
        1 * userReservationListMapper.toDto(savedReservation) >> expected

        when:
        def result = service.reserveCar(USER_ID, requestDto)

        then:
        expected == result
    }
}
