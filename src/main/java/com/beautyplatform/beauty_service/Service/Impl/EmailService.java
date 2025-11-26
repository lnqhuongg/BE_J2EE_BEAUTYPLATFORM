package com.beautyplatform.beauty_service.Service.Impl;

import com.beautyplatform.beauty_service.Helper.EmailTemplateLoader;
import com.beautyplatform.beauty_service.Service.Interface.IEmailService;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class EmailService implements IEmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private EmailTemplateLoader templateLoader;

    public void sendHtml(String to, String subject, String templateName, Map<String, String> vars) {
        try {
            String html = templateLoader.loadTemplate(templateName);

            for (Map.Entry<String, String> entry : vars.entrySet()) {
                String placeholder = "{{" + entry.getKey() + "}}";
                html = html.replace(placeholder, entry.getValue());
            }

            MimeMessage msg = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(msg, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(html, true);
            helper.setFrom("Freshj <dangchithanh12a12022@gmail.com>");

            mailSender.send(msg);

        } catch (Exception e) {
            throw new RuntimeException("Gửi email thất bại", e);
        }
    }
}
