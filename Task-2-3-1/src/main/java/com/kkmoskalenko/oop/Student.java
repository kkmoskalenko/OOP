package com.kkmoskalenko.oop;

import lombok.Data;

import java.net.MalformedURLException;
import java.net.URL;

@Data
class Student {
    private String name;
    private URL repoURL;

    void name(String name) {
        this.name = name;
    }

    void repo(String urlString) {
        try {
            this.repoURL = new URL(urlString);
        } catch (MalformedURLException e) {
            System.err.println(e.getMessage());
        }
    }
}
