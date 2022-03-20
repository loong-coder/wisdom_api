package com.edu.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class BasePage implements Serializable {

    private static final long serialVersionUID = -8548586283590180468L;

    protected Integer pageNum;

    protected Integer size;
}
