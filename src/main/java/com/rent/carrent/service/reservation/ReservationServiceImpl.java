package com.rent.carrent.service.reservation;

import com.rent.carrent.dto.reservation.ReservationRequestDto;
import com.rent.carrent.dto.reservation.UserReservationList;
import com.rent.carrent.exception.CarReservationException;
import com.rent.carrent.exception.NoAvailableCarForReservationException;
import com.rent.carrent.exception.UserNotFoundException;
import com.rent.carrent.mapper.ReservationMapper;
import com.rent.carrent.mapper.UserReservationListMapper;
import com.rent.carrent.model.Car;
import com.rent.carrent.model.Reservation;
import com.rent.carrent.model.User;
import com.rent.carrent.repository.CarRepository;
import com.rent.carrent.repository.ReservationRepository;
import com.rent.carrent.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {
    private final UserRepository userRepository;
    private final CarRepository carRepository;
    private final ReservationRepository reservationRepository;
    private final ReservationMapper mapper;
    private final UserReservationListMapper userReservationListMapper;

    @Override
    @Transactional
    public UserReservationList reserveCar(String userId, ReservationRequestDto requestDto) {
        LocalDateTime startReservation = requestDto.getStartReservation();
        LocalDateTime endReservation = requestDto.getEndReservation();
        validateReservationDates(startReservation, endReservation);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with id %s not found", userId));
        Page<Car> availableCarToRent = carRepository.findAvailableCarToRent(startReservation, endReservation, Pageable.ofSize(1));
        Car car = availableCarToRent.get()
                .findFirst()
                .orElseThrow(() -> new NoAvailableCarForReservationException("No available cars to reserve"));
        Reservation reservation = mapper.toReservation(car, user, startReservation, endReservation);
        Reservation savedReservation = reservationRepository.save(reservation);
        return userReservationListMapper.toDto(savedReservation);
    }

    @Override
    public UserReservationList getUpcomingReservationList(String userId) {
        User upcomingReservationList = userRepository.getUpcomingReservationList(userId);
        return userReservationListMapper.toDtoList(upcomingReservationList.getReservationList(), upcomingReservationList);
    }

    private void validateReservationDates(LocalDateTime startReservation, LocalDateTime endReservation) {
        LocalDateTime now = LocalDateTime.now();
        if (endReservation.isBefore(startReservation) || endReservation.isEqual(startReservation)) {
            throw new CarReservationException("End reservation time must be after start reservation time");
        }
        if (now.plusHours(24).isAfter(startReservation)) {
            throw new CarReservationException("The reservation can be taken up to 24 hours ahead.");
        }
        if (startReservation.plusHours(2).isBefore(endReservation)) {
            throw new CarReservationException("The reservation duration can take up to 2 hours.");
        }
    }
}
