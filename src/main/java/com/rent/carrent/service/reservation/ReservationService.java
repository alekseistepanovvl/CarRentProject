package com.rent.carrent.service.reservation;

import com.rent.carrent.dto.reservation.ReservationRequestDto;
import com.rent.carrent.dto.reservation.UserReservationList;

public interface ReservationService {

    UserReservationList reserveCar(String userId, ReservationRequestDto requestDto);

    UserReservationList getUpcomingReservationList(String userId);
}
