package com.sdcProject.busReservationSystem.controller.optController;

import com.sdcProject.busReservationSystem.modal.OtpResponseModal;
import com.sdcProject.busReservationSystem.modal.Users;
import com.sdcProject.busReservationSystem.service.MailService;
import com.sdcProject.busReservationSystem.service.OtpService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/otp")
@AllArgsConstructor
public class OtpController {


    private MailService mailService;
    private OtpService otpService;

    @GetMapping("/generateOtp")
    public ResponseEntity<OtpResponseModal> generateOtp(@RequestParam("email") String email) {

        String otp=otpService.generateOpt(email);

       boolean send= mailService.sendOtp(email, otp);

       if(!send){
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new OtpResponseModal(false,"failed to send otp"));
       }


        return  ResponseEntity.status(HttpStatus.OK).body(new OtpResponseModal(true,"opt successfully sent to:"+email));

    }

    @PostMapping("/validateOtp")
    public ResponseEntity<OtpResponseModal> validateOtp(@RequestParam("otp") String otp,@RequestParam("email") String email) {
        boolean isValidated=otpService.validateOtp(email,otp);

        if(!isValidated){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new OtpResponseModal(false,"otp validation failed"));
        }

        return ResponseEntity.status(HttpStatus.OK).body(new OtpResponseModal(true,"otp successfully validated:"+otp));
    }

    @PostMapping("/updatePassword")
    public ResponseEntity<Boolean> updatePassword(@RequestBody Users users){
        String email=users.getEmail();
        String password=users.getPassword();
        if(otpService.updatePassword(email,password)){
            return ResponseEntity.status(HttpStatus.OK).body(true);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
    }
}
