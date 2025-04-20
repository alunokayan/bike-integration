package br.edu.ifsp.spo.bike_integration.dto;

import lombok.Builder;

@Builder
public record JwtConfigDTO(String accessKey, String secretKey, Long expiration) {

}