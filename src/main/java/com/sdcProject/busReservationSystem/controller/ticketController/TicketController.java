package com.sdcProject.busReservationSystem.controller.ticketController;

import com.sdcProject.busReservationSystem.dto.BookingDTO;
import com.sdcProject.busReservationSystem.service.PdfTicketService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;

@RestController
@RequestMapping("api/ticketBooking")
@AllArgsConstructor
public class TicketController {


    private PdfTicketService pdfTicketService;



    @PostMapping("/getMyTicket")
    public ResponseEntity<InputStreamResource> downloadTicket(@RequestBody BookingDTO bookingDTO) {


        ByteArrayInputStream pdfStream = pdfTicketService.generateTicketPdf(bookingDTO);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=ticket_" + ".pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(pdfStream));
    }
}
