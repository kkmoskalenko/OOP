package com.kkmoskalenko.oop;

import lombok.Value;

import java.util.Date;

@Value
public class Note {
    String title;
    String message;
    Date timestamp;
}
