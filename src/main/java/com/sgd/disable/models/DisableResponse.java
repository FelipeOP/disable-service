package com.sgd.disable.models;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DisableResponse {

    @JsonProperty("Fecha")
    private Date date = new Date();

    @JsonProperty("TotalDeshabilitados")
    private long disableCount;

}
