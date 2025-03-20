package br.edu.ifsp.spo.bike_integration.dto;

import lombok.Builder;

@Builder
public record JwtUserDTO(String nickname, String email, String password, String role) {

}