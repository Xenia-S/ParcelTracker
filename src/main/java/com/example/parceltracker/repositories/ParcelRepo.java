package com.example.parceltracker.repositories;

import com.example.parceltracker.entities.Parcel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParcelRepo extends JpaRepository<Parcel, Long> {

}
