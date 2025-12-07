package com.sdcProject.busReservationSystem.controller.querycontroller;

import com.sdcProject.busReservationSystem.dto.AdminQueryDto;
import com.sdcProject.busReservationSystem.modal.Query;
import com.sdcProject.busReservationSystem.repository.QueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
    
@RestController
@RequestMapping("/api/auth/admin/query")
@RequiredArgsConstructor
public class AdminQueryController {
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
            dto.setStatus(query.getStatus());
            return dto;
        }).toList();
    }

    @PutMapping("/update/{id}")
    public String updateQuery(@PathVariable int id){
         Optional<Query> update = queryRepository.findById(id);
        if (update.isPresent()) {
      Query query =  update.get();
        query.setStatus("SOLVED");
        queryRepository.save(query);
        return "Query marked as solved" ;
        }
        else {
            return "Query not found.";
        }

    }

}
