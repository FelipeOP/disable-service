package com.sgd.disable.models;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentDto {

    private Long did;

    @JsonProperty("xdNumeroCuenta")
    private String accountNumber;

    @JsonProperty("dOriginalName")
    private String originalName;

    @JsonProperty("dStatus")
    private String status;

    @JsonProperty("dReleaseState")
    private String releaseState;

    @JsonProperty("dCreateDate")
    private Date createDate;

    @JsonProperty("xdMotivoDeshabilitacion")
    private String disableReason;
}
