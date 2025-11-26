package com.beautyplatform.beauty_service.Service.Interface;

public interface IOTPService {

    /**
     * Tạo OTP mới cho email
     * @param email địa chỉ email người nhận
     * @return mã OTP vừa tạo
     */
    String generateOtp(String email);

    /**
     * Kiểm tra OTP có hợp lệ với email hay không
     * @param email địa chỉ email
     * @param otp mã OTP cần kiểm tra
     * @return true nếu hợp lệ, false nếu không
     */
    boolean verifyOtp(String email, String otp);

    /**
     * Gửi email OTP tới người nhận
     * @param email địa chỉ email
     */
    void sendOtpEmail(String email);
}

