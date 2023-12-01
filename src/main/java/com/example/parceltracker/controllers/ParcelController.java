package com.example.parceltracker.controllers;

import com.example.parceltracker.dto.UpdateParcelStatusRequest;
import com.example.parceltracker.entities.Parcel;
import com.example.parceltracker.services.ParcelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/parcel")
@RequiredArgsConstructor
public class ParcelController {

    private final ParcelService parcelService;

    @GetMapping(path = "/get/list")
    public List<Parcel> getParcels() {
        return parcelService.getParcels();
    }

    @PutMapping(path = "/update/status")
    public void updateParcelStatus(@RequestBody @Valid UpdateParcelStatusRequest request) {
        parcelService.updateParcelStatus(request);
    }
}
