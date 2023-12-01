package com.example.parceltracker.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateParcelStatusRequest {

    @NotNull
    private Long parcelId;

    @NotNull
    private Long statusId;

    private Integer code;
}
