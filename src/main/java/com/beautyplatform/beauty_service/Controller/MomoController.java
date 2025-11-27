package com.beautyplatform.beauty_service.Controller;

import com.beautyplatform.beauty_service.DTO.MoMoDTO.MomoCallbackDTO;
import com.beautyplatform.beauty_service.DTO.MoMoDTO.MomoResponseDTO;
import com.beautyplatform.beauty_service.Helper.ApiResponse;
import com.beautyplatform.beauty_service.Service.Impl.MomoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/momo")
public class MomoController {

    @Autowired
    private MomoService momoService;

    @Autowired
    private ApiResponse apiResponse;

    /**
     * Tạo yêu cầu thanh toán MoMo
     * POST /momo/create-payment
     * Body: { "maDL": 1, "amount": 100000, "orderInfo": "Thanh toán dịch vụ làm đẹp" }
     */
    @PostMapping("/create-payment")
    public ResponseEntity<ApiResponse> createPayment(@RequestBody Map<String, Object> request) {
        try {
            int maDL = (Integer) request.get("maDL");
            Long amount = Long.valueOf(request.get("amount").toString());
            String orderInfo = (String) request.get("orderInfo");

            // Tạo request thanh toán
            MomoResponseDTO momoResponse = momoService.createPayment(maDL, amount, orderInfo);

            if (momoResponse.getResultCode() == 0) {
                apiResponse.setSuccess(true);
                apiResponse.setMessage("Tạo yêu cầu thanh toán MoMo thành công!");
                apiResponse.setData(momoResponse);
                return ResponseEntity.ok(apiResponse);
            } else {
                apiResponse.setSuccess(false);
                apiResponse.setMessage("Lỗi từ MoMo: " + momoResponse.getMessage());
                apiResponse.setData(momoResponse);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
            }

        } catch (Exception e) {
            apiResponse.setSuccess(false);
            apiResponse.setMessage("Đã xảy ra lỗi: " + e.getMessage());
            apiResponse.setData(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
        }
    }

    /**
     * Xử lý callback từ MoMo (IPN - Instant Payment Notification)
     * POST /momo/ipn
     */
    @PostMapping("/ipn")
    public ResponseEntity<Map<String, Object>> handleIPN(@RequestBody MomoCallbackDTO callback) {
        try {
            // Xác thực signature
            if (!momoService.verifyCallback(callback)) {
                return ResponseEntity.ok(Map.of(
                        "status", "error",
                        "message", "Invalid signature"
                ));
            }

            // Cập nhật trạng thái thanh toán
            momoService.updatePaymentStatus(callback);

            // Trả về response cho MoMo
            return ResponseEntity.ok(Map.of(
                    "status", "success",
                    "message", "Payment status updated"
            ));

        } catch (Exception e) {
            System.err.println("Lỗi xử lý IPN: " + e.getMessage());
            return ResponseEntity.ok(Map.of(
                    "status", "error",
                    "message", e.getMessage()
            ));
        }
    }

    /**
     * Xử lý redirect từ MoMo sau khi khách hàng thanh toán
     * GET /momo/callback
     */
    @GetMapping("/callback")
    public ResponseEntity<ApiResponse> handleCallback(
            @RequestParam String orderId,
            @RequestParam Integer resultCode,
            @RequestParam(required = false) String message) {

        try {
            if (resultCode == 0) {
                apiResponse.setSuccess(true);
                apiResponse.setMessage("Thanh toán thành công!");
                apiResponse.setData(Map.of(
                        "orderId", orderId,
                        "resultCode", resultCode
                ));
            } else {
                apiResponse.setSuccess(false);
                apiResponse.setMessage("Thanh toán thất bại: " + message);
                apiResponse.setData(Map.of(
                        "orderId", orderId,
                        "resultCode", resultCode
                ));
            }

            return ResponseEntity.ok(apiResponse);

        } catch (Exception e) {
            apiResponse.setSuccess(false);
            apiResponse.setMessage("Đã xảy ra lỗi: " + e.getMessage());
            apiResponse.setData(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
        }
    }
}