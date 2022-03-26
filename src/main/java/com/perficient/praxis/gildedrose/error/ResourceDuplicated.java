package com.perficient.praxis.gildedrose.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
public class ResourceDuplicated extends RuntimeException{
    public ResourceDuplicated(String message){
        super(message);
    }
}
