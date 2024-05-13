package com.batalhanaval.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
@AllArgsConstructor
public class PacoteResponseModel {

    private Boolean created;
    private String message;
}

