package com.rent.carrent.repository;

import com.rent.carrent.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    @Query("select u from User u " +
            " left join fetch u.reservationList r" +
            " left join u.reservationList r1 on  r.id = r1.id and current_timestamp < r1.reservationStart" +
            " left join fetch r.car" +
            " where u.id = :id")
    User getUpcomingReservationList(String id);
}
