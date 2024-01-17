package si.um.feri.measurements.rest;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import si.um.feri.measurements.dao.MeasurementRepository;
import si.um.feri.measurements.dto.MeasurementDTO;
import si.um.feri.measurements.vao.Measurement;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;

@Path("/measurements")
public class MeasurementHistoryController {

    @Inject
    MeasurementRepository measurementRepository;

    @ConfigProperty(name = "history.dayslimit", defaultValue = "30")
    int envHistoryDayslimit;

    @GET
    @Path("/history")
    @Produces(MediaType.APPLICATION_JSON)
    public List<MeasurementDTO> getHistory() {
        long history = System.currentTimeMillis() - (long) envHistoryDayslimit * 3_600_000 * 24;
        LocalDateTime historyDate = LocalDateTime.ofInstant(Instant.ofEpochMilli(history), TimeZone.getDefault().toZoneId());

        return measurementRepository.findByCreatedGreaterThan(historyDate)
                .stream()
                .map(Measurement::toDto)
                .collect(Collectors.toList());
    }
}