package de.petschi.hexDungeon.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * game actor
 */
@AllArgsConstructor
@Getter
@Setter
public class Player {
    private String id;
    private String name;
    private String mail;
    private String pwd;
}
