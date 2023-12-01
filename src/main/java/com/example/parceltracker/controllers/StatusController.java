package com.example.parceltracker.controllers;

import com.example.parceltracker.entities.Status;
import com.example.parceltracker.services.StatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/status")
@RequiredArgsConstructor
public class StatusController {

    private final StatusService statusService;

    @GetMapping(path = "/get/list")
    public List<Status> getStatuses() {
        return statusService.getStatuses();
    }

    @GetMapping(path = "/get/deleted")
    public List<Status> getDeletedStatuses() {
        return statusService.getDeletedStatuses();
    }

    @PostMapping(path = "/create")
    public Status createStatus(@RequestParam("name") String name) {
        return statusService.createStatus(name);
    }

    @PutMapping(path = "/update/{statusId}")
    public Status updateStatus(@PathVariable Long statusId, @RequestParam("name") String name) {
        return statusService.updateStatus(statusId, name);
    }

    @DeleteMapping(path = "/delete/{statusId}")
    public void deleteStatus(@PathVariable Long statusId) {
        statusService.deleteStatus(statusId);
    }

    @PutMapping(path = "/restore/{statusId}")
    public Status restoreStatus(@PathVariable Long statusId) {
        return statusService.restoreStatus(statusId);
    }
}
