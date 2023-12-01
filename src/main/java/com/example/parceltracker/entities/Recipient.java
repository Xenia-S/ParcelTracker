package com.example.parceltracker.entities;

import com.example.parceltracker.utils.EntityTimeModel;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@Table(name = "recipient")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Recipient extends EntityTimeModel {

    @Id
    @SequenceGenerator(name = "recipient_id_seq", sequenceName = "recipient_id_seq")
    @GeneratedValue(generator = "recipient_id_seq", strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;

    private String address;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Recipient recipient)) return false;
        return getName().equals(recipient.getName()) && getAddress().equals(recipient.getAddress());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getAddress());
    }
}
