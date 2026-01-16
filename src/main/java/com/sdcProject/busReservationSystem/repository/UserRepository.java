package com.sdcProject.busReservationSystem.repository;

import com.sdcProject.busReservationSystem.modal.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {
    Optional<Users> findByEmail(String username);

    Optional<Users> findByPhoneNumber(String username);

    @Query(value = "SELECT u.*\n" +
            "FROM users u\n" +
            "         JOIN user_roles ur ON u.user_id = ur.user_id\n" +
            "         JOIN roles r ON r.role_id = ur.role_id\n" +
            "WHERE r.role =:roleName",nativeQuery = true)
    List<Users> findByRole(@Param("roleName")String roleName);
}
