package com.rent.carrent.mapper;

import com.rent.carrent.dto.reservation.UserReservationList;
import com.rent.carrent.model.Reservation;
import com.rent.carrent.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;
import java.util.Optional;

@Mapper(uses = {ReservationMapper.class, UserMapper.class})
public interface UserReservationListMapper {

    @Mappings({
            @Mapping(target = "reservation", source = "reservation")
    })
    UserReservationList toDto(Reservation reservation);

    @Mappings({
            @Mapping(target = "user", source = "user"),
            @Mapping(target = "reservation", source = "reservationList")
    })
    UserReservationList toDtoList(List<Reservation> reservationList, User user);

}
