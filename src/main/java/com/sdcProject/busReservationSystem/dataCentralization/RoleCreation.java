package com.sdcProject.busReservationSystem.dataCentralization;

import com.sdcProject.busReservationSystem.modal.Roles;
import com.sdcProject.busReservationSystem.repository.RoleRepository;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Slf4j
@AllArgsConstructor
public class RoleCreation {
    private RoleRepository roleRepository;

    @PostConstruct
    public void initData(){
        if (roleRepository.count() == 0) {
            Roles adminRole = new Roles();
            adminRole.setRole("ROLE_ADMIN");

            Roles ownerRole = new Roles();
            ownerRole.setRole("ROLE_OWNER");

            Roles userRole = new Roles();
            userRole.setRole("ROLE_USER");

            Roles busRole = new Roles();
            busRole.setRole("ROLE_BUS");

            roleRepository.saveAll(Arrays.asList(adminRole, ownerRole, userRole, busRole));
            log.info("Default roles inserted successfully!");
        } else {
            log.info("Roles already exist in database");
        }
    }

}
