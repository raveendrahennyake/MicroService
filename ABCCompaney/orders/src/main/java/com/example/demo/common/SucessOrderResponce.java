package com.example.demo.common;

import com.example.demo.D.T.O.OrderDTO;
import lombok.Getter;

@Getter
public class SucessOrderResponce implements OrderResponce {

    private final OrderDTO order;
    public SucessOrderResponce (OrderDTO order){
        this.order=order;

    }

}
