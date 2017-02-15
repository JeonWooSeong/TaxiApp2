package com.example.wooseonge.taxiapp.bean;

import java.io.Serializable;

import lombok.Data;
import lombok.ToString;

/**
 * Created by KSE_NOTEBOOK on 2017-02-15.
 */
@ToString
public @Data class ResultDefault implements Serializable {
    Header header;
    Body body;
}
