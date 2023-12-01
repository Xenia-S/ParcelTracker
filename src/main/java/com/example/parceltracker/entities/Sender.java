package com.example.parceltracker.entities;

import com.example.parceltracker.utils.EntityTimeModel;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@Table(name = "sender")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Sender extends EntityTimeModel {

    @Id
    @SequenceGenerator(name = "sender_id_seq", sequenceName = "sender_id_seq")
    @GeneratedValue(generator = "sender_id_seq", strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Sender sender)) return false;
        return getName().equals(sender.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}
