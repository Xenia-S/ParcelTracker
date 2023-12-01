package com.example.parceltracker.repositories;

import com.example.parceltracker.entities.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StatusRepo extends JpaRepository<Status, Long> {

    Status findByNameIgnoreCase(String name);
    Status findByName(String name);
    List<Status> findAllByDeletedIsFalse();
    List<Status> findAllByDeletedIsTrue();
}
