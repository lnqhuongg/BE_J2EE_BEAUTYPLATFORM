package com.beautyplatform.beauty_service.Service.Impl;

import com.beautyplatform.beauty_service.Config.JwtUtil;
import com.beautyplatform.beauty_service.DTO.AuthDTO.*;
import com.beautyplatform.beauty_service.Model.*;
import com.beautyplatform.beauty_service.Repository.*;
import com.beautyplatform.beauty_service.Service.Interface.IAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class AuthService implements IAuthService {

    @Autowired
    private TaiKhoanRepository taiKhoanRepository;

    @Autowired
    private NhaCungCapRepository nhaCungCapRepository;

    @Autowired
    private KhachHangRepository khachHangRepository;

    @Autowired
    private LoaiHinhKinhDoanhRepository loaiHinhRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    // ==================== ĐĂNG KÝ QUA FORM ====================
    @Override
    @Transactional
    public Optional<AuthResponseDTO> dangKy(DangKyDTO dto) {
        try {
            // 1. Validate
            if (!dto.getMatKhau().equals(dto.getXacNhanMatKhau())) {
                throw new RuntimeException("Mật khẩu xác nhận không khớp");
            }

            if (taiKhoanRepository.findByEmail(dto.getEmail()).isPresent()) {
                throw new RuntimeException("Email đã được sử dụng");
            }

            if (dto.getLoaiTK() != 2 && dto.getLoaiTK() != 3) {
                throw new RuntimeException("Loại tài khoản không hợp lệ");
            }

            // 2. Tạo TaiKhoan
            TaiKhoan taiKhoan = TaiKhoan.builder()
                    .email(dto.getEmail())
                    .matKhau(passwordEncoder.encode(dto.getMatKhau()))
                    .loaiTK(dto.getLoaiTK())
                    .trangThai(1) // Active
                    .ngayTao(LocalDateTime.now())
                    .provider("local")
                    .build();

            TaiKhoan savedTK = taiKhoanRepository.save(taiKhoan);

            // 3. Tạo NCC hoặc KH
            if (dto.getLoaiTK() == 2) {
                // Tạo Nhà Cung Cấp
                LoaiHinhKinhDoanh loaiHinh = loaiHinhRepository.findById(dto.getMaLH())
                        .orElseThrow(() -> new RuntimeException("Loại hình kinh doanh không tồn tại"));

                NhaCungCap ncc = NhaCungCap.builder()
                        .taiKhoan(savedTK)
                        .loaiHinhKinhDoanh(loaiHinh)
                        .tenNCC(dto.getTenNCC())
                        .gioiThieu(dto.getGioiThieu() != null ? dto.getGioiThieu() : "")
                        .diaChi(dto.getDiaChi() != null ? dto.getDiaChi() : "")
                        .build();

                nhaCungCapRepository.save(ncc);

            } else {
                // Tạo Khách Hàng
                KhachHang kh = KhachHang.builder()
                        .taiKhoan(savedTK)
                        .hoTen(dto.getHoTen())
                        .gioiTinh(dto.getGioiTinh())
                        .ngaySinh(dto.getNgaySinh())
                        .sdt(dto.getSdt() != null ? dto.getSdt() : "")
                        .hinhAnh("")
                        .build();

                khachHangRepository.save(kh);
            }

            // 4. Generate JWT Token
            String token = jwtUtil.generateToken(
                    savedTK.getEmail(),
                    savedTK.getMaTK(),
                    savedTK.getLoaiTK()
            );

            // 5. Return response
            return Optional.of(AuthResponseDTO.builder()
                    .token(token)
                    .maTK(savedTK.getMaTK())
                    .email(savedTK.getEmail())
                    .loaiTK(savedTK.getLoaiTK())
                    .provider("local")
                    .build());

        } catch (Exception e) {
            System.err.println("Lỗi khi đăng ký: " + e.getMessage());
            return Optional.empty();
        }
    }

    // ==================== ĐĂNG NHẬP QUA FORM ====================
    @Override
    public Optional<AuthResponseDTO> dangNhap(DangNhapDTO dto) {
        try {
            // 1. Tìm tài khoản
            TaiKhoan taiKhoan = taiKhoanRepository.findByEmail(dto.getEmail())
                    .orElseThrow(() -> new RuntimeException("Email hoặc mật khẩu không đúng"));

            // 2. Kiểm tra provider (chỉ cho phép local login)
            if (!"local".equals(taiKhoan.getProvider())) {
                throw new RuntimeException("Tài khoản này đăng ký qua " + taiKhoan.getProvider() +
                        ". Vui lòng đăng nhập bằng " + taiKhoan.getProvider());
            }

            // 3. Kiểm tra mật khẩu
            if (!passwordEncoder.matches(dto.getMatKhau(), taiKhoan.getMatKhau())) {
                throw new RuntimeException("Email hoặc mật khẩu không đúng");
            }

            // 4. Kiểm tra trạng thái
            if (taiKhoan.getTrangThai() == 0) {
                throw new RuntimeException("Tài khoản đã bị khóa");
            }

            // 5. Generate JWT
            String token = jwtUtil.generateToken(
                    taiKhoan.getEmail(),
                    taiKhoan.getMaTK(),
                    taiKhoan.getLoaiTK()
            );

            // 6. Build response
            AuthResponseDTO response = AuthResponseDTO.builder()
                    .token(token)
                    .maTK(taiKhoan.getMaTK())
                    .email(taiKhoan.getEmail())
                    .loaiTK(taiKhoan.getLoaiTK())
                    .provider("local")
                    .build();

            // 7. Thêm thông tin NCC hoặc KH
            if (taiKhoan.getLoaiTK() == 2) {
                nhaCungCapRepository.findAll().stream()
                        .filter(ncc -> ncc.getTaiKhoan().getMaTK() == taiKhoan.getMaTK())
                        .findFirst()
                        .ifPresent(ncc -> {
                            response.setMaNCC(ncc.getMaNCC());
                            response.setTenNCC(ncc.getTenNCC());
                        });
            } else if (taiKhoan.getLoaiTK() == 3) {
                khachHangRepository.findAll().stream()
                        .filter(kh -> kh.getTaiKhoan().getMaTK() == taiKhoan.getMaTK())
                        .findFirst()
                        .ifPresent(kh -> {
                            response.setMaKH(kh.getMaKH());
                            response.setHoTen(kh.getHoTen());
                        });
            }

            return Optional.of(response);

        } catch (Exception e) {
            System.err.println("Lỗi khi đăng nhập: " + e.getMessage());
            return Optional.empty();
        }
    }

    // ==================== OAUTH2 LOGIN ====================
    @Override
    @Transactional
    public Optional<AuthResponseDTO> oauth2Login(String email, String provider, String providerId) {
        try {
            // 1. Tìm hoặc tạo tài khoản
            Optional<TaiKhoan> existingTK = taiKhoanRepository.findByEmail(email);

            TaiKhoan taiKhoan;
            boolean isNewUser = false;

            if (existingTK.isPresent()) {
                taiKhoan = existingTK.get();

                // Update provider info nếu chưa có
                if (taiKhoan.getProvider() == null || "local".equals(taiKhoan.getProvider())) {
                    taiKhoan.setProvider(provider);
                    taiKhoan.setProviderId(providerId);
                    taiKhoan = taiKhoanRepository.save(taiKhoan);
                }
            } else {
                // Tạo tài khoản mới
                taiKhoan = TaiKhoan.builder()
                        .email(email)
                        .matKhau(null) // OAuth không cần password
                        .loaiTK(3) // Mặc định là Khách Hàng
                        .trangThai(1)
                        .ngayTao(LocalDateTime.now())
                        .provider(provider)
                        .providerId(providerId)
                        .build();

                taiKhoan = taiKhoanRepository.save(taiKhoan);
                isNewUser = true;

                // Tạo profile Khách Hàng
                KhachHang kh = KhachHang.builder()
                        .taiKhoan(taiKhoan)
                        .hoTen(email.split("@")[0]) // Tạm dùng email prefix
                        .gioiTinh(1)
                        .ngaySinh(LocalDate.now())
                        .sdt("")
                        .hinhAnh("")
                        .build();

                khachHangRepository.save(kh);
            }

            // 2. Generate JWT
            String token = jwtUtil.generateToken(
                    taiKhoan.getEmail(),
                    taiKhoan.getMaTK(),
                    taiKhoan.getLoaiTK()
            );

            // 3. Build response
            return Optional.of(AuthResponseDTO.builder()
                    .token(token)
                    .maTK(taiKhoan.getMaTK())
                    .email(taiKhoan.getEmail())
                    .loaiTK(taiKhoan.getLoaiTK())
                    .provider(provider)
                    .build());

        } catch (Exception e) {
            System.err.println("Lỗi OAuth2 login: " + e.getMessage());
            return Optional.empty();
        }
    }

    // ==================== QUÊN MẬT KHẨU ====================
    @Override
    public Optional<String> quenMatKhau(QuenMatKhauDTO dto) {
        try {
            TaiKhoan taiKhoan = taiKhoanRepository.findByEmail(dto.getEmail())
                    .orElseThrow(() -> new RuntimeException("Email không tồn tại"));

            // TODO: Gửi email reset password (implement sau với JavaMailSender)
            // Tạm thời trả về message
            return Optional.of("Email reset mật khẩu đã được gửi đến: " + dto.getEmail());

        } catch (Exception e) {
            System.err.println("Lỗi quên mật khẩu: " + e.getMessage());
            return Optional.empty();
        }
    }

    // ==================== ĐỔI MẬT KHẨU ====================
    @Override
    public Optional<String> doiMatKhau(int maTK, DoiMatKhauDTO dto) {
        try {
            TaiKhoan taiKhoan = taiKhoanRepository.findById(maTK)
                    .orElseThrow(() -> new RuntimeException("Tài khoản không tồn tại"));

            // Kiểm tra mật khẩu cũ
            if (!passwordEncoder.matches(dto.getMatKhauCu(), taiKhoan.getMatKhau())) {
                throw new RuntimeException("Mật khẩu cũ không đúng");
            }

            // Kiểm tra xác nhận mật khẩu mới
            if (!dto.getMatKhauMoi().equals(dto.getXacNhanMatKhauMoi())) {
                throw new RuntimeException("Xác nhận mật khẩu mới không khớp");
            }

            // Cập nhật mật khẩu
            taiKhoan.setMatKhau(passwordEncoder.encode(dto.getMatKhauMoi()));
            taiKhoanRepository.save(taiKhoan);

            return Optional.of("Đổi mật khẩu thành công");

        } catch (Exception e) {
            System.err.println("Lỗi đổi mật khẩu: " + e.getMessage());
            return Optional.empty();
        }
    }

    // ==================== VALIDATE TOKEN ====================
    @Override
    public boolean validateToken(String token) {
        return jwtUtil.validateToken(token);
    }
}