package br.edu.ifsp.spo.bike_integration.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GeoJsonDto {
    private String type;
    private List<FeatureDto> features;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class FeatureDto {
        private String type;
        private Long id;
        private PropertiesDto properties;
        private GeometryDto geometry;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class PropertiesDto {
        private Long id;
        private String type;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class GeometryDto {
        private String type;
        private List<List<List<String>>> coordinates;
    }
}
