package br.edu.ifsp.spo.bike_integration.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class BrasilApiCepResponse {

    private String cep;
    private String state;
    private String city;
    private String neighborhood;
    private String street;
    private String service;

    @JsonProperty("location")
    private Location location;

    // Getters e Setters

    @Getter
    @Setter
    public static class Location {
        private String type;
        private Coordinates coordinates;
        
        @Getter
        @Setter
        public static class Coordinates {
            private String longitude;
            private String latitude;
        }
     
    }
}
