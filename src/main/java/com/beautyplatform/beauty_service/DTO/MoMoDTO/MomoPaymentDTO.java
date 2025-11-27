package com.beautyplatform.beauty_service.DTO.MoMoDTO;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class MomoPaymentDTO {
    private String partnerCode;
    private String orderId;
    private String requestId;
    private Long amount;
    private String orderInfo;
    private String redirectUrl;
    private String ipnUrl;
    private String requestType;
    private String extraData;
    private String lang;
    private String signature;
}