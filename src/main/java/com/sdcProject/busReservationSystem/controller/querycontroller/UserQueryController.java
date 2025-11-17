package com.sdcProject.busReservationSystem.controller.querycontroller;

import com.sdcProject.busReservationSystem.dto.UserQueryDto;
import com.sdcProject.busReservationSystem.modal.Query;
import com.sdcProject.busReservationSystem.repository.QueryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth/user/query")
public class UserQueryController {
    @Autowired
    QueryRepository queryRepository;
    // Add a new query
    @PostMapping("/add")
    public Query addQuery(@RequestBody Query query) {
        return queryRepository.save(query);
    }
    @GetMapping("/all/{email}")
    public List<UserQueryDto> getUserQueries(@PathVariable String email) {
        return queryRepository.findByEmail(email).stream().map(query -> {
            UserQueryDto dto = new UserQueryDto();
            dto.setId(query.getId());
            dto.setName(query.getName());
            dto.setEmail(query.getEmail());
            dto.setNumber(query.getNumber());
            dto.setCategory(query.getCategory());
            dto.setMessage(query.getMessage());
            dto.setStatus(query.getStatus()); // show status to user
            return dto;
        }).toList();
    }

}
