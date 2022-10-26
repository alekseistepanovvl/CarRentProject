package com.rent.carrent.mapper;

import com.rent.carrent.dto.reservation.ReservationDto;
import com.rent.carrent.mapper.CarMapper;
import com.rent.carrent.mapper.UserMapper;
import com.rent.carrent.model.Car;
import com.rent.carrent.model.Reservation;
import com.rent.carrent.model.User;
import org.mapstruct.Mapper;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Mapper(uses = {UserMapper.class, CarMapper.class})
public interface ReservationMapper {

    default Reservation toReservation(Car car, User user,
                                      LocalDateTime startReservation, LocalDateTime endReservation) {
        Reservation reservation = new Reservation();
        reservation.setCar(car);
        reservation.setUser(user);
        reservation.setReservationStart(startReservation);
        reservation.setReservationEnd(endReservation);
        return reservation;
    }

    ReservationDto toDto(Reservation model);

    List<ReservationDto> toDto(List<Reservation> model);

    default List<ReservationDto> toDtoList(Reservation model) {
        ReservationDto reservationDto = this.toDto(model);
        return Collections.singletonList(reservationDto);
    }
}
