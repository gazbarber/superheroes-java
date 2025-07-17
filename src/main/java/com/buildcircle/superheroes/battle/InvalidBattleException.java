package com.buildcircle.superheroes.battle;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidBattleException extends RuntimeException {
    InvalidBattleException(String message) {
        super(message);
    }
}