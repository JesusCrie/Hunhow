package com.jesus_crie.kankanbot.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameManager {

    private static List<Game> games = new ArrayList<>();

    public static void registerGames(Game... gs) {
        games.addAll(Arrays.asList(gs));
    }
}
