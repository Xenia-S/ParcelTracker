package com.example.parceltracker.repositories;

import com.example.parceltracker.entities.Sender;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SenderRepo extends JpaRepository<Sender, Long> {
}
