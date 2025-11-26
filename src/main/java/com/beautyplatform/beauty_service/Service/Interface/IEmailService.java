package com.beautyplatform.beauty_service.Service.Interface;
import java.util.Map;

public interface IEmailService {

    /**
     * Gửi email HTML theo template với các biến thay thế
     *
     * @param to           Email người nhận
     * @param subject      Tiêu đề email
     * @param templateName Tên file template HTML
     * @param vars         Map các biến sẽ thay thế trong template (ví dụ: OTP, tên người dùng)
     */
    void sendHtml(String to, String subject, String templateName, Map<String, String> vars);
}

