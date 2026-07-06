package com.pcveldi.analytics.controller;

import com.pcveldi.analytics.model.MetricEvent;
import com.pcveldi.analytics.service.MetricsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/metrics")
@RequiredArgsConstructor
public class MetricsController {

    private final MetricsService metricsService;

    @GetMapping("/summary")
    public ResponseEntity<List<MetricEvent>> getSummary(
            @RequestParam(required = false) String from,
            @RequestParam(required = false) String to) {
        return ResponseEntity.ok(metricsService.getSummary(from, to));
    }

    @GetMapping("/live")
    public ResponseEntity<MetricEvent> getLiveMetrics() {
        return ResponseEntity.ok(metricsService.getCurrentSnapshot());
    }

    @PostMapping("/ingest")
    public ResponseEntity<Void> ingestEvent(@RequestBody MetricEvent event) {
        metricsService.processEvent(event);
        return ResponseEntity.accepted().build();
    }
}
