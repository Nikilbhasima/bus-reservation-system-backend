package com.sdcProject.busReservationSystem.service;

import com.sdcProject.busReservationSystem.dto.SendNotification;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MailService {


    private JavaMailSender mailSender;

    public boolean sendOtp(String email, String otp) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(email);
            helper.setSubject("Bus Yatra - OTP Verification");

            // HTML content
            String htmlContent = "<!DOCTYPE html>" +
                    "<html>" +
                    "<body style='font-family: Arial, sans-serif;'>" +
                    "<div style='max-width: 500px; margin: auto; padding: 20px; border: 1px solid #ddd; border-radius: 10px;'>" +
                    "<h2 style='color: #2E86C1;'>Bus Yatra OTP Verification</h2>" +
                    "<p>Your OTP to reset your password is:</p>" +
                    "<h1 style='background-color: #f2f2f2; text-align: center; padding: 10px; border-radius: 5px; letter-spacing: 5px;'>"
                    + otp + "</h1>" +
                    "<p style='color: #555;'>Please do not share it with anyone.</p>" +
                    "<p style='color: #555;'>It is only valid for <strong>5 minutes</strong>.</p>" +
                    "<hr>" +
                    "<p style='font-size: 12px; color: #999;'>If you did not request this OTP, please ignore this email.</p>" +
                    "</div>" +
                    "</body>" +
                    "</html>";

            helper.setText(htmlContent, true); // true = HTML

            mailSender.send(message);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }


    }


    public boolean sendNotification(List<String> email, SendNotification sendNotification) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            for (String emailTo : email) {
                helper.setTo(emailTo);
                helper.setSubject("Notification!!!");
                helper.setText(sendNotification.getMessage(), true);
                mailSender.send(message);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean boardingNotification(List<String> email) {
        try {
            for (String emailTo : email) {

                MimeMessage message = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

                helper.setTo(emailTo);
                helper.setSubject("🚌 Bus Boarding Notification");
                helper.setText(
                        "<h3>Dear Passenger,</h3>" +
                                "<p>Your bus is now <strong>ready for boarding</strong>.</p>" +
                                "<p>Please proceed to the designated platform on time.</p>" +
                                "<br>" +
                                "<p>Safe travels!<br/>Bus Reservation System</p>",
                        true
                );

                mailSender.send(message);
            }

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }
}
