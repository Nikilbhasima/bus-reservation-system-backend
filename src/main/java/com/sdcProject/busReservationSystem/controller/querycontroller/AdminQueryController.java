package com.sdcProject.busReservationSystem.controller.querycontroller;

import com.sdcProject.busReservationSystem.dto.AdminQueryDto;
import com.sdcProject.busReservationSystem.dto.UserQueryDto;
import com.sdcProject.busReservationSystem.modal.Query;
import com.sdcProject.busReservationSystem.repository.QueryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
    
@RestController
@RequestMapping("/api/auth/admin/query")
public class AdminQueryController {
    @Autowired
    QueryRepository queryRepository;
    @GetMapping("/all")
    public List<AdminQueryDto> getAllQueriesForAdmin() {
        return queryRepository.findAll().stream().map(query -> {
            AdminQueryDto dto = new AdminQueryDto();
            dto.setId(query.getId());
            dto.setName(query.getName());
            dto.setEmail(query.getEmail());
            dto.setNumber(query.getNumber());
            dto.setCategory(query.getCategory());
            dto.setMessage(query.getMessage());
            return dto;
        }).toList();
    }
    @PutMapping("/update/{id}")
    public String updateQuery(@PathVariable int id){
         Optional<Query> update = queryRepository.findById(id);
        if (update.isPresent()) {
      Query query =  update.get();
        query.setStatus("Solved");
        queryRepository.save(query);
        return "Query marked as solved" ;
        }
        else {
            return "Query not found.";
        }

    }

}
