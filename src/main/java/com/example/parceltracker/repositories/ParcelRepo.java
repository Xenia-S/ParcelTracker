package com.example.parceltracker.repositories;

import com.example.parceltracker.entities.Parcel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ParcelRepo extends JpaRepository<Parcel, Long> {

    @Query(value = "select * from parcel " +
            "order by status_id, date_planned_delivery", nativeQuery = true)
    List<Parcel> findAllSorted();

    @Query(value = "select p.identifier from parcel p", nativeQuery = true)
    List<String> findAllIdentifiers();

}
