package com.sdcProject.busReservationSystem.service;

import com.sdcProject.busReservationSystem.modal.OtpEntryModal;
import com.sdcProject.busReservationSystem.modal.Users;
import com.sdcProject.busReservationSystem.repository.UserRepository;
import com.sdcProject.busReservationSystem.utils.OptUtils;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@AllArgsConstructor
public class OtpService {


    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    private final Map<String, OtpEntryModal> otpCache = new ConcurrentHashMap<>();

    public String generateOpt(String email) {
        String otp = OptUtils.generateOtp(6);
        otpCache.put(email, new OtpEntryModal(otp, LocalDateTime.now().plusMinutes(5))); // valid 5 min
        return otp;

    }


    public boolean validateOtp(String email, String otp) {

        OtpEntryModal entry = otpCache.get(email);
        if (entry == null) return false;

        if (entry.getExpiryDate().isBefore(LocalDateTime.now())) {
            otpCache.remove(email);
            return false;
        }

        boolean isValid = entry.getOtp().equals(otp);
        // remove after successful use
        if (isValid) otpCache.remove(email);
        return isValid;}


    public Boolean updatePassword(String email, String newPassword) {
        Users users=userRepository.findByEmail(email).orElseThrow(()->new RuntimeException("user not found"));
        users.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(users);
        return true;
        }
}
