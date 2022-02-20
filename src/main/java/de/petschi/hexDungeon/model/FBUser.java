package de.petschi.hexDungeon.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * FireBase Authentication Object
 */
@AllArgsConstructor
@Getter
@Setter
public class FBUser {
    private String id;
    private String name;
}
