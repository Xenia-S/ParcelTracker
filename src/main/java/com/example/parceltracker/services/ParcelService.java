package com.example.parceltracker.services;


import com.example.parceltracker.dto.UpdateParcelStatusRequest;
import com.example.parceltracker.entities.Parcel;
import com.example.parceltracker.entities.Status;
import com.example.parceltracker.repositories.ParcelRepo;
import com.example.parceltracker.repositories.StatusRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static com.example.parceltracker.utils.Constants.*;
import static com.example.parceltracker.utils.StatusNames.RECEIVED;

@Service
@RequiredArgsConstructor
public class ParcelService {

    private final ParcelRepo parcelRepo;
    private final StatusRepo statusRepo;

    public List<Parcel> getParcels() {
        return parcelRepo.findAllSorted();
    }

    @Transactional
    public void updateParcelStatus(UpdateParcelStatusRequest request) {
        Status status = statusRepo.findById(request.getStatusId())
                .orElseThrow(() -> new NullPointerException(STATUS_NOT_FOUND));
        Parcel parcel = parcelRepo.findById(request.getParcelId())
                .orElseThrow(() -> new NullPointerException(PARCEL_NOT_FOUND));

        parcel.setStatus(status);
        if (status.getName().equals(RECEIVED.getName())) {
            if (request.getCode() == null)
                throw new IllegalStateException(NO_RECEIVE_CODE);
            checkCode(parcel, request.getCode());
            parcel.setDateReceived(LocalDate.now(ZONE_ID));
        }

        parcelRepo.save(parcel);
    }

    private void checkCode(Parcel parcel, Integer code) {
        if (!parcel.getCode().equals(code)) {
            throw new IllegalArgumentException(RECEIVE_CODE_NOT_CORRECT);
        }
    }
}
