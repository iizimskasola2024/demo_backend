package si.um.feri.measurements.dto.post;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
public record PostMeasurementResponseDTO(String result) {
}
