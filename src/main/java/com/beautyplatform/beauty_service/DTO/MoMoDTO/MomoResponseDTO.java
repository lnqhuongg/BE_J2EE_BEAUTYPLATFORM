package com.beautyplatform.beauty_service.DTO.MoMoDTO;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class MomoResponseDTO {
    private String partnerCode;
    private String orderId;
    private String requestId;
    private Long amount;
    private Long responseTime;
    private String message;
    private Integer resultCode;
    private String payUrl;
    private String deeplink;
    private String qrCodeUrl;
}