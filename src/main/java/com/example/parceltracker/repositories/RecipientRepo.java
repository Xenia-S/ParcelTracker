package com.example.parceltracker.repositories;

import com.example.parceltracker.entities.Recipient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipientRepo extends JpaRepository<Recipient, Long> {
}

