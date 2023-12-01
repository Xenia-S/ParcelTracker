package com.example.parceltracker.services;

import com.example.parceltracker.entities.Status;
import com.example.parceltracker.repositories.StatusRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.parceltracker.utils.Constants.*;

@Service
@RequiredArgsConstructor
public class StatusService {

    private final StatusRepo statusRepo;

    public List<Status> getStatuses() {
        return statusRepo.findAllByDeletedIsFalse();
    }

    public List<Status> getDeletedStatuses() {
        return statusRepo.findAllByDeletedIsTrue();
    }

    public Status createStatus(String name) {
        Status statusInDb = statusRepo.findByName(name);

        if (statusInDb != null) {
            if (statusInDb.getDeleted()) {
                statusInDb.setDeleted(false);
                return statusInDb;
            } else {
                throw new IllegalArgumentException(STATUS_EXISTS + name);
            }
        }

        Status newStatus = new Status();
        newStatus.setName(name);
        statusRepo.save(newStatus);
        return newStatus;
    }

    public Status updateStatus(Long statusId, String name) {
        Status status = statusRepo.findById(statusId)
                .orElseThrow(() -> new NullPointerException(STATUS_NOT_FOUND));

        Status statusInDb = statusRepo.findByName(name);
        if (statusInDb != null)
            throw new IllegalArgumentException(STATUS_EXISTS + name);

        status.setName(name);
        statusRepo.save(status);
        return status;
    }

    public void deleteStatus(Long statusId) {
        Status status = statusRepo.findById(statusId)
                .orElseThrow(() -> new NullPointerException(STATUS_NOT_FOUND));

        if (status.getDeleted())
            throw new IllegalArgumentException(STATUS_DELETED);

        status.setDeleted(true);
        statusRepo.save(status);
    }

    public Status restoreStatus(Long statusId) {
        Status status = statusRepo.findById(statusId)
                .orElseThrow(() -> new NullPointerException(STATUS_NOT_FOUND));

        if (!status.getDeleted())
            throw new IllegalArgumentException(STATUS_NOT_DELETED);

        status.setDeleted(false);
        statusRepo.save(status);
        return status;
    }
}
