package com.example.demo.common;
import com.example.demo.D.T.O.OrderDTO;
import lombok.Getter;
public class ErrorResponce implements OrderResponce {
    @Getter
    private final String errormessage;

    public ErrorResponce (String errormessage){
        this.errormessage=errormessage;

    }




}
