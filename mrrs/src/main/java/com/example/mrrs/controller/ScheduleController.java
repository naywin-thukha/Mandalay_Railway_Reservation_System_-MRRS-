package com.example.mrrs.controller;

import com.example.mrrs.dto.schedule.ScheduleRequest;
import com.example.mrrs.dto.schedule.ScheduleResponse;
import com.example.mrrs.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    // =========================
    // 1. CREATE SCHEDULE (ADMIN)
    // =========================
    @PostMapping
    public ScheduleResponse create(@RequestBody ScheduleRequest request) {
        return scheduleService.createSchedule(request);
    }

    // =========================
    // 2. GET ALL SCHEDULES (ADMIN / DEBUG)
    // =========================
    @GetMapping
    public List<ScheduleResponse> getAll() {
        return scheduleService.getAllSchedules();
    }

    // =========================
    // 3. GET BY ID (DETAIL PAGE)
    // =========================
    @GetMapping("/{id}")
    public ScheduleResponse get(@PathVariable Long id) {
        return scheduleService.getSchedule(id);
    }

    // =========================
    // 4. SEARCH BY ROUTE + DATE (MAIN USER SEARCH)
    // =========================
    @GetMapping("/search")
    public List<ScheduleResponse> search(
            @RequestParam Long routeId,
            @RequestParam String date) {

        return scheduleService.getSchedulesByRouteAndDate(
                routeId,
                LocalDate.parse(date)
        );
    }

    // =========================
    // 5. SEARCH BY TRAIN
    // =========================
    @GetMapping("/train/{trainId}")
    public List<ScheduleResponse> getByTrain(@PathVariable Long trainId) {
        return scheduleService.getSchedulesByTrain(trainId);
    }

    // =========================
    // 6. FILTER BY DATE RANGE (USER DASHBOARD)
    // =========================
    @GetMapping("/filter")
    public List<ScheduleResponse> filterByDateRange(
            @RequestParam String from,
            @RequestParam String to) {

        return scheduleService.getSchedulesByDateRange(
                LocalDate.parse(from),
                LocalDate.parse(to)
        );
    }

    // =========================
    // 7. UPDATE SCHEDULE (ADMIN)
    // =========================
    @PutMapping("/{id}")
    public ScheduleResponse update(
            @PathVariable Long id,
            @RequestBody ScheduleRequest request) {

        return scheduleService.updateSchedule(id, request);
    }

    // =========================
    // 8. DELETE SCHEDULE (ADMIN)
    // =========================
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        scheduleService.deleteSchedule(id);
        return "Schedule deleted successfully";
    }
}
