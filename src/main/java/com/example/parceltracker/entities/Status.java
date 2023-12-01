package com.example.parceltracker.entities;

import com.example.parceltracker.utils.EntityTimeModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "status")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Status extends EntityTimeModel {

    @Id
    @SequenceGenerator(name = "status_id_seq", sequenceName = "status_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "status_id_seq", strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;

    @Column(name = "deleted", columnDefinition = "boolean default false", nullable = false)
    @JsonIgnore
    private Boolean deleted;

}
