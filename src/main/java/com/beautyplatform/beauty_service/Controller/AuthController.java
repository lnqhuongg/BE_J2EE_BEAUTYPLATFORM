package com.beautyplatform.beauty_service.Controller;

import com.beautyplatform.beauty_service.Config.JwtUtil;
import com.beautyplatform.beauty_service.DTO.AuthDTO.*;
import com.beautyplatform.beauty_service.DTO.KhachHangDTO.KhachHangDTO;
import com.beautyplatform.beauty_service.Helper.ApiResponse;
import com.beautyplatform.beauty_service.Model.TaiKhoan;
import com.beautyplatform.beauty_service.Repository.TaiKhoanRepository;
import com.beautyplatform.beauty_service.Service.Impl.OTPService;
import com.beautyplatform.beauty_service.Service.Interface.IAuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private OTPService otpService;

    @Autowired
    private IAuthService authService;

    @Autowired
    private ApiResponse apiResponse;

    @Autowired
    private JwtUtil jwtUtil;

    // ==================== ĐĂNG KÝ QUA FORM ====================

    @PostMapping("/dangky")
    public ResponseEntity<ApiResponse> dangKy(@Valid @RequestBody DangKyDTO dangKyDTO) {
        try {
            Optional<AuthResponseDTO> result = authService.dangKy(dangKyDTO);

            if (result.isEmpty()) {
                apiResponse.setSuccess(false);
                apiResponse.setMessage("Đăng ký không thành công!");
                apiResponse.setData(null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
            }

            apiResponse.setSuccess(true);
            apiResponse.setMessage("Đăng ký thành công!");
            apiResponse.setData(result.get());
            return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);

        } catch (Exception e) {
            apiResponse.setSuccess(false);
            apiResponse.setMessage("Đã xảy ra lỗi: " + e.getMessage());
            apiResponse.setData(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
        }
    }

    // ==================== ĐĂNG NHẬP QUA FORM ====================

    @PostMapping("/dangnhap")
    public ResponseEntity<ApiResponse> dangNhap(@Valid @RequestBody DangNhapDTO dangNhapDTO) {
        try {
            Optional<AuthResponseDTO> result = authService.dangNhap(dangNhapDTO);

            if (result.isEmpty()) {
                apiResponse.setSuccess(false);
                apiResponse.setMessage("Email hoặc mật khẩu không đúng!");
                apiResponse.setData(null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
            }

            apiResponse.setSuccess(true);
            apiResponse.setMessage("Đăng nhập thành công!");
            apiResponse.setData(result.get());
            return ResponseEntity.ok(apiResponse);

        } catch (Exception e) {
            apiResponse.setSuccess(false);
            apiResponse.setMessage("Đã xảy ra lỗi: " + e.getMessage());
            apiResponse.setData(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
        }
    }

    // ==================== QUÊN MẬT KHẨU ====================

    @PostMapping("/quenmatkhau")
    public ResponseEntity<ApiResponse> quenMatKhau(@Valid @RequestBody QuenMatKhauDTO dto) {
        try {
            Optional<String> result = authService.quenMatKhau(dto);

            if (result.isEmpty()) {
                apiResponse.setSuccess(false);
                apiResponse.setMessage("Email không tồn tại trong hệ thống!");
                apiResponse.setData(null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
            }

            apiResponse.setSuccess(true);
            apiResponse.setMessage(result.get());
            apiResponse.setData(null);
            return ResponseEntity.ok(apiResponse);

        } catch (Exception e) {
            apiResponse.setSuccess(false);
            apiResponse.setMessage("Đã xảy ra lỗi: " + e.getMessage());
            apiResponse.setData(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
        }
    }

    // ==================== ĐỔI MẬT KHẨU ====================

    @PostMapping("/doimatkhau")
    public ResponseEntity<ApiResponse> doiMatKhau(
            @RequestHeader("Authorization") String authHeader,
            @Valid @RequestBody DoiMatKhauDTO dto) {
        try {
            // Lấy token từ header
            String token = authHeader.replace("Bearer ", "");

            // Validate token
            if (!jwtUtil.validateToken(token)) {
                apiResponse.setSuccess(false);
                apiResponse.setMessage("Token không hợp lệ hoặc đã hết hạn!");
                apiResponse.setData(null);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiResponse);
            }

            // Lấy maTK từ token
            int maTK = jwtUtil.getMaTKFromToken(token);

            // Đổi mật khẩu
            Optional<String> result = authService.doiMatKhau(maTK, dto);

            if (result.isEmpty()) {
                apiResponse.setSuccess(false);
                apiResponse.setMessage("Đổi mật khẩu không thành công!");
                apiResponse.setData(null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
            }

            apiResponse.setSuccess(true);
            apiResponse.setMessage(result.get());
            apiResponse.setData(null);
            return ResponseEntity.ok(apiResponse);

        } catch (Exception e) {
            apiResponse.setSuccess(false);
            apiResponse.setMessage("Đã xảy ra lỗi: " + e.getMessage());
            apiResponse.setData(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
        }
    }

    // ==================== VALIDATE TOKEN ====================

    @PostMapping("/validate")
    public ResponseEntity<ApiResponse> validateToken(@RequestBody Map<String, String> request) {
        try {
            String token = request.get("token");

            if (token == null || token.isEmpty()) {
                apiResponse.setSuccess(false);
                apiResponse.setMessage("Token không được để trống!");
                apiResponse.setData(null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
            }

            boolean isValid = authService.validateToken(token);

            if (isValid) {
                // Lấy thông tin từ token
                String email = jwtUtil.getEmailFromToken(token);
                int maTK = jwtUtil.getMaTKFromToken(token);

                apiResponse.setSuccess(true);
                apiResponse.setMessage("Token hợp lệ!");
                apiResponse.setData(Map.of(
                        "email", email,
                        "maTK", maTK,
                        "valid", true
                ));
            } else {
                apiResponse.setSuccess(false);
                apiResponse.setMessage("Token không hợp lệ hoặc đã hết hạn!");
                apiResponse.setData(Map.of("valid", false));
            }

            return ResponseEntity.ok(apiResponse);

        } catch (Exception e) {
            apiResponse.setSuccess(false);
            apiResponse.setMessage("Đã xảy ra lỗi: " + e.getMessage());
            apiResponse.setData(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
        }
    }

    // ==================== ĐĂNG XUẤT ====================

    @PostMapping("/dangxuat")
    public ResponseEntity<ApiResponse> dangXuat() {
        // Vì dùng JWT stateless, client chỉ cần xóa token ở local
        // Server không cần làm gì
        apiResponse.setSuccess(true);
        apiResponse.setMessage("Đăng xuất thành công!");
        apiResponse.setData(null);
        return ResponseEntity.ok(apiResponse);
    }
    // ==================== KIỂM TRA EMAIL TỒN TẠI ====================
    @GetMapping("/check-email")
    public ResponseEntity<ApiResponse> checkEmail(@RequestParam String email) {
        try {
            boolean exists = authService.isEmailExists(email); // phương thức kiểm tra email trong service

            apiResponse.setSuccess(true);
            apiResponse.setMessage("Kiểm tra email thành công");
            apiResponse.setData(Map.of("exists", exists));

            return ResponseEntity.ok(apiResponse);

        } catch (Exception e) {
            apiResponse.setSuccess(false);
            apiResponse.setMessage("Đã xảy ra lỗi: " + e.getMessage());
            apiResponse.setData(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
        }
    }


    // ==================== LẤY THÔNG TIN USER TỪ TOKEN ====================

    @GetMapping("/hoso")
    public ResponseEntity<ApiResponse> getHoSoTaiKhoan(@RequestHeader("Authorization") String authHeader) {
        try {
            String token = authHeader.replace("Bearer ", "");

            if (!jwtUtil.validateToken(token)) {
                apiResponse.setSuccess(false);
                apiResponse.setMessage("Token không hợp lệ!");
                apiResponse.setData(null);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiResponse);
            }

            // Lấy maTK từ token
            Integer maTK = jwtUtil.getMaTKFromToken(token);

            // Lấy thông tin hồ sơ từ service
            var hoSoOpt = authService.getHoSoTaiKhoan(maTK);

            if (hoSoOpt.isEmpty()) {
                apiResponse.setSuccess(false);
                apiResponse.setMessage("Không tìm thấy tài khoản!");
                apiResponse.setData(null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
            }

            apiResponse.setSuccess(true);
            apiResponse.setMessage("Lấy thông tin hồ sơ thành công!");
            apiResponse.setData(hoSoOpt.get());
            return ResponseEntity.ok(apiResponse);

        } catch (Exception e) {
            apiResponse.setSuccess(false);
            apiResponse.setMessage("Đã xảy ra lỗi: " + e.getMessage());
            apiResponse.setData(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
        }
    }

    @PostMapping("/send-otp")
    public ResponseEntity<ApiResponse> sendOtp(@RequestParam String email) {
        try {
            otpService.sendOtpEmail(email);

            apiResponse.setSuccess(true);
            apiResponse.setMessage("OTP đã được gửi thành công!");
            apiResponse.setData(null);
            return ResponseEntity.ok(apiResponse);

        } catch (Exception e) {
            apiResponse.setSuccess(false);
            apiResponse.setMessage("Gửi OTP thất bại: " + e.getMessage());
            apiResponse.setData(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
        }
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<ApiResponse> verifyOtp(
            @RequestParam String email,
            @RequestParam String otp) {
        try {
            boolean valid = otpService.verifyOtp(email, otp);

            if (!valid) {
                apiResponse.setSuccess(false);
                apiResponse.setMessage("OTP không chính xác!");
                apiResponse.setData(null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
            }

            apiResponse.setSuccess(true);
            apiResponse.setMessage("Xác minh OTP thành công!");
            apiResponse.setData(null);
            return ResponseEntity.ok(apiResponse);

        } catch (Exception e) {
            apiResponse.setSuccess(false);
            apiResponse.setMessage("Đã xảy ra lỗi: " + e.getMessage());
            apiResponse.setData(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
        }
    }
}