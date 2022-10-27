package com.rent.carrent.controller

import com.rent.carrent.dto.error.ErrorDto
import com.rent.carrent.dto.reservation.ReservationRequestDto
import com.rent.carrent.dto.reservation.UserReservationList
import com.rent.carrent.dto.user.UserDto
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

import java.time.LocalDateTime

class UserControllerIT extends BaseControllerSpecification {

    @Value('http://localhost:${local.server.port}/api/v1/user')
    String serviceUserUrl

    String USER_ID = 'abbe642c-69cd-44d2-b947-61b6e1adbcd6'

    def "Get list of users"() {
        when:
        ResponseEntity<List<UserDto>> result = restTemplate.exchange(serviceUserUrl, HttpMethod.GET, null, new ParameterizedTypeReference<List<UserDto>>() {})

        then:
        result.statusCode == HttpStatus.OK
        List<UserDto> userList = result.body
        userList.size() == 3
    }

    def "Get upcoming reservation list"() {
        when:
        ResponseEntity<UserReservationList> result = restTemplate.getForEntity(serviceUserUrl + '/{userId}/reservation', UserReservationList.class, USER_ID)

        then:
        result.statusCode == HttpStatus.OK
        def userReservation = result.body
        userReservation.user.id == USER_ID
        userReservation.reservation.size() == 0
    }

    def "Get upcoming reservation list for unknown user will throw exception"() {
        when:
        ResponseEntity<ErrorDto> result = restTemplate.getForEntity(serviceUserUrl + '/{userId}/reservation', ErrorDto.class, '12')

        then:
        result.statusCode == HttpStatus.NOT_FOUND
        result.body.errorMessage == 'User with 12 not found'
    }

    def "Reserve available car"() {
        given:
        LocalDateTime startReservation = LocalDateTime.now().plusDays(3)
        LocalDateTime endReservation = startReservation.plusHours(1)
        ReservationRequestDto requestDto = new ReservationRequestDto(
                'startReservation': startReservation,
                'endReservation': endReservation
        )

        when:
        ResponseEntity<UserReservationList> result = restTemplate.postForEntity(serviceUserUrl + "/{userId}/reservation", requestDto, UserReservationList.class, USER_ID)

        then:
        result.statusCode == HttpStatus.OK
        def userReservationList = result.body
        userReservationList.user.id == USER_ID
        userReservationList.reservation.size() == 1
        userReservationList.reservation[0].reservationStart == startReservation
        userReservationList.reservation[0].reservationEnd == endReservation
    }

    def "Reserve car with start date after end date"() {
        given:
        LocalDateTime startReservation = LocalDateTime.now().plusDays(3)
        LocalDateTime endReservation = startReservation.plusHours(1)
        ReservationRequestDto requestDto = new ReservationRequestDto(
                'startReservation': endReservation,
                'endReservation': startReservation
        )

        when:
        ResponseEntity<ErrorDto> result = restTemplate.postForEntity(serviceUserUrl + "/{userId}/reservation", requestDto, ErrorDto.class, USER_ID)

        then:
        result.statusCode == HttpStatus.BAD_REQUEST
        result.body.errorMessage.trim() == 'End reservation time must be after start reservation time'
    }

    def "Reserve car must start after 24 hours"() {
        given:
        LocalDateTime startReservation = LocalDateTime.now().plusHours(1)
        LocalDateTime endReservation = startReservation.plusHours(1)
        ReservationRequestDto requestDto = new ReservationRequestDto(
                'startReservation': startReservation,
                'endReservation': endReservation
        )

        when:
        ResponseEntity<ErrorDto> result = restTemplate.postForEntity(serviceUserUrl + "/{userId}/reservation", requestDto, ErrorDto.class, USER_ID)

        then:
        result.statusCode == HttpStatus.BAD_REQUEST
        result.body.errorMessage.trim() == 'The reservation can be taken up to 24 hours ahead.'
    }

    def "Reservation can take up to 2 hours only"() {
        given:
        LocalDateTime startReservation = LocalDateTime.now().plusDays(3)
        LocalDateTime endReservation = startReservation.plusHours(3)
        ReservationRequestDto requestDto = new ReservationRequestDto(
                'startReservation': startReservation,
                'endReservation': endReservation
        )

        when:
        ResponseEntity<ErrorDto> result = restTemplate.postForEntity(serviceUserUrl + "/{userId}/reservation", requestDto, ErrorDto.class, USER_ID)

        then:
        result.statusCode == HttpStatus.BAD_REQUEST
        result.body.errorMessage.trim() == 'The reservation duration can take up to 2 hours.'
    }
}
