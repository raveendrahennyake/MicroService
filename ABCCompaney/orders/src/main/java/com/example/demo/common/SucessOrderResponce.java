package com.example.demo.common;

import com.example.demo.D.T.O.OrderDTO;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import lombok.Getter;

@Getter

public class SucessOrderResponce implements OrderResponce {
    @JsonUnwrapped
    private final OrderDTO order;
    public SucessOrderResponce (OrderDTO order){
        this.order=order;

    }

}
