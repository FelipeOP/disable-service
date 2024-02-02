package com.sgd.disable.models;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DisableRequest {

    @NotEmpty
    private String primaryFile;

    @NotEmpty
    @JsonProperty("xdNumeroCuenta")
    private String accountNumber;
}
