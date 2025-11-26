package com.beautyplatform.beauty_service.Service.Impl;

import com.beautyplatform.beauty_service.Service.Interface.IOTPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class OTPService implements IOTPService {

    private final Map<String, String> otpStorage = new HashMap<>();

    @Autowired
    private EmailService emailService;

    public String generateOtp(String email) {
        String otp = String.valueOf((int)(Math.random() * 900000) + 100000);
        otpStorage.put(email, otp);
        return otp;
    }

    public boolean verifyOtp(String email, String otp) {
        return otp.equals(otpStorage.get(email));
    }

    public void sendOtpEmail(String email) {
        String otp = generateOtp(email);

        Map<String, String> data = new HashMap<>();
        data.put("OTP", otp);

        emailService.sendHtml(
                email,
                "Mã OTP xác minh",
                "otp.html",
                data
        );
    }
}

