package com.beautyplatform.beauty_service.Service.Impl;

import com.beautyplatform.beauty_service.Config.MomoConfig;
import com.beautyplatform.beauty_service.DTO.MoMoDTO.MomoCallbackDTO;
import com.beautyplatform.beauty_service.DTO.MoMoDTO.MomoPaymentDTO;
import com.beautyplatform.beauty_service.DTO.MoMoDTO.MomoResponseDTO;
import com.beautyplatform.beauty_service.Model.ThanhToan;
import com.beautyplatform.beauty_service.Repository.ThanhToanRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class MomoService {

    @Autowired
    private MomoConfig momoConfig;

    @Autowired
    private ThanhToanRepository thanhToanRepository;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Tạo yêu cầu thanh toán MoMo
     */
    public MomoResponseDTO createPayment(int maDL, Long amount, String orderInfo) throws Exception {
        // Tạo orderId và requestId duy nhất
        String orderId = "ORDER_" + maDL + "_" + System.currentTimeMillis();
        String requestId = UUID.randomUUID().toString();

        // Tạo raw signature
        String rawSignature = "accessKey=" + momoConfig.getAccessKey() +
                "&amount=" + amount +
                "&extraData=" +
                "&ipnUrl=" + momoConfig.getIpnUrl() +
                "&orderId=" + orderId +
                "&orderInfo=" + orderInfo +
                "&partnerCode=" + momoConfig.getPartnerCode() +
                "&redirectUrl=" + momoConfig.getRedirectUrl() +
                "&requestId=" + requestId +
                "&requestType=" + momoConfig.getRequestType();

        // Tạo signature
        String signature = generateHMAC(rawSignature, momoConfig.getSecretKey());

        // Tạo request body
        MomoPaymentDTO paymentRequest = MomoPaymentDTO.builder()
                .partnerCode(momoConfig.getPartnerCode())
                .orderId(orderId)
                .requestId(requestId)
                .amount(amount)
                .orderInfo(orderInfo)
                .redirectUrl(momoConfig.getRedirectUrl())
                .ipnUrl(momoConfig.getIpnUrl())
                .requestType(momoConfig.getRequestType())
                .extraData("")
                .lang("vi")
                .signature(signature)
                .build();

        // Gửi request đến MoMo
        String jsonRequest = objectMapper.writeValueAsString(paymentRequest);

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(momoConfig.getEndpoint());
            httpPost.setHeader("Content-Type", "application/json");
            httpPost.setEntity(new StringEntity(jsonRequest, StandardCharsets.UTF_8));

            try (CloseableHttpResponse response = client.execute(httpPost)) {
                String responseBody = EntityUtils.toString(response.getEntity());
                return objectMapper.readValue(responseBody, MomoResponseDTO.class);
            }
        }
    }

    /**
     * Xác thực callback từ MoMo
     */
    public boolean verifyCallback(MomoCallbackDTO callback) {
        try {
            String rawSignature = "accessKey=" + momoConfig.getAccessKey() +
                    "&amount=" + callback.getAmount() +
                    "&extraData=" + callback.getExtraData() +
                    "&message=" + callback.getMessage() +
                    "&orderId=" + callback.getOrderId() +
                    "&orderInfo=" + callback.getOrderInfo() +
                    "&orderType=" + callback.getOrderType() +
                    "&partnerCode=" + callback.getPartnerCode() +
                    "&payType=" + callback.getPayType() +
                    "&requestId=" + callback.getRequestId() +
                    "&responseTime=" + callback.getResponseTime() +
                    "&resultCode=" + callback.getResultCode() +
                    "&transId=" + callback.getTransId();

            String expectedSignature = generateHMAC(rawSignature, momoConfig.getSecretKey());

            return expectedSignature.equals(callback.getSignature());
        } catch (Exception e) {
            System.err.println("Lỗi xác thực callback: " + e.getMessage());
            return false;
        }
    }

    /**
     * Cập nhật trạng thái thanh toán sau khi nhận callback
     */
    public void updatePaymentStatus(MomoCallbackDTO callback) {
        try {
            // Lấy maDL từ orderId (format: ORDER_{maDL}_{timestamp})
            String orderId = callback.getOrderId();
            String[] parts = orderId.split("_");
            if (parts.length >= 2) {
                int maDL = Integer.parseInt(parts[1]);

                // Tìm thanh toán theo maDL
                ThanhToan thanhToan = thanhToanRepository.findAll().stream()
                        .filter(tt -> tt.getDatLich().getMaDL() == maDL)
                        .filter(tt -> tt.getTrangThai() == 0) // Chưa thanh toán
                        .findFirst()
                        .orElse(null);

                if (thanhToan != null) {
                    // Cập nhật trạng thái dựa trên resultCode
                    if (callback.getResultCode() == 0) {
                        // Thanh toán thành công
                        thanhToan.setTrangThai(1);
                    } else {
                        // Thanh toán thất bại
                        thanhToan.setTrangThai(2);
                    }
                    thanhToan.setNgayThanhToan(LocalDateTime.now());
                    thanhToanRepository.save(thanhToan);
                }
            }
        } catch (Exception e) {
            System.err.println("Lỗi cập nhật trạng thái thanh toán: " + e.getMessage());
        }
    }

    /**
     * Tạo HMAC SHA256 signature
     */
    private String generateHMAC(String data, String secretKey) throws Exception {
        Mac hmac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        hmac.init(secretKeySpec);
        byte[] hmacBytes = hmac.doFinal(data.getBytes(StandardCharsets.UTF_8));

        // Convert to hex string
        StringBuilder result = new StringBuilder();
        for (byte b : hmacBytes) {
            result.append(String.format("%02x", b));
        }
        return result.toString();
    }
}