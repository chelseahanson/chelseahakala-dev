package io.hakala.chelseahakaladev.Entity;

import lombok.Data;

@Data
public class Project {
    private String name;

    public Project(String name) {
        this.name = name;
    }
}