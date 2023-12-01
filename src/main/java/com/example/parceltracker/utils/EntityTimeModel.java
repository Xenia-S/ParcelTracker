package com.example.parceltracker.utils;



import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

import static com.example.parceltracker.utils.Constants.ZONE_ID;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EntityTimeModel {

    @Column(name = "date_add", nullable = false, updatable = false)
    @CreatedDate
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.LOCAL_DATE_TIME_SERIALIZATION_FORMAT)
    @JsonIgnore
    private LocalDateTime createdAt;

    @Column(name = "date_changed", nullable = false)
    @LastModifiedDate
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.LOCAL_DATE_TIME_SERIALIZATION_FORMAT)
    @JsonIgnore
    private LocalDateTime updatedAt;

    @PreUpdate
    protected void preUpdate() {
        updatedAt = LocalDateTime.now(ZONE_ID);
    }

    @PrePersist
    protected void prePersist() {
        createdAt = LocalDateTime.now(ZONE_ID);
        updatedAt = LocalDateTime.now(ZONE_ID);
    }
}
