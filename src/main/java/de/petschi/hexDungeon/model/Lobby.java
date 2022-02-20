package de.petschi.hexDungeon.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;
import java.util.UUID;

/**
 * Collection of Players connected to the Game
 */
@NoArgsConstructor
@Getter
@Setter
public class Lobby {
    private String id;
    private Collection<Player> players;
    private Game game;

    public void addPlayer(Player player) { this.players.add(player); }

    public Boolean removePlayer(Player player) { return this.players.remove(player); }

    public Game startGame() { return new Game("New Game"); }
}
