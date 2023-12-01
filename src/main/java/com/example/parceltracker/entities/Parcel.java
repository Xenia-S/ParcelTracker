package com.example.parceltracker.entities;

import com.example.parceltracker.utils.EntityTimeModel;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "parcel")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Parcel extends EntityTimeModel {

    @Id
    @SequenceGenerator(name = "parcel_id_seq", sequenceName = "parcel_id_seq")
    @GeneratedValue(generator = "parcel_id_seq", strategy = GenerationType.SEQUENCE)
    private Long id;

    private String identifier;

    private Double weight;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private Sender sender;

    @ManyToOne
    @JoinColumn(name = "recipient_id")
    private Recipient recipient;

    private LocalDate dateSent;

    private LocalDate datePlannedDelivery;

    private LocalDate dateReceived;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status status;

    private Integer code;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Parcel parcel)) return false;
        return getIdentifier().equals(parcel.getIdentifier()) &&
                getWeight().equals(parcel.getWeight()) &&
                getSender().equals(parcel.getSender()) &&
                getRecipient().equals(parcel.getRecipient()) &&
                getDateSent().equals(parcel.getDateSent()) &&
                getDatePlannedDelivery().equals(parcel.getDatePlannedDelivery());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdentifier(), getWeight(),
                getSender(), getRecipient(), getDateSent(),
                getDatePlannedDelivery());
    }
}
