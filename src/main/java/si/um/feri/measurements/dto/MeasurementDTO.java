package si.um.feri.measurements.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.format.DateTimeFormatter;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
public record MeasurementDTO(
        Long id,
        String date,
        Long productId,
        double avgTemperature,
        boolean isOk) {
    public static final DateTimeFormatter JSON_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
}
