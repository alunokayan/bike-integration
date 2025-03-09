package br.edu.ifsp.spo.bike_integration.dto;

import lombok.Builder;

@Builder
public record JwtSubjectDTO(String accessKey, String secretKey) {

}