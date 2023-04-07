package com.example.disgusting.Logic;

import com.example.disgusting.Models.Obstacle;
import com.example.disgusting.Models.Player;

import java.util.ArrayList;

public class DataManager
{
    public static final int COLS = 3;
    public static final int ROWS = 4;
    public static final int NUM_OF_OBSTACLES = COLS * ROWS;
    public static final int NUM_OF_PLAYERS = COLS;


    public static ArrayList<Player> getPlayers()
    {
        ArrayList<Player> Players = new ArrayList<>();
        for (int i = 0; i < NUM_OF_PLAYERS; i++)
        {
            Player q = new Player().setVisible(false);
            Players.add(q);
        }

        return Players;
    }

    public static ArrayList<Obstacle> getObstacles()
    {
        ArrayList<Obstacle> Obstacles = new ArrayList<>();
        for (int i = 0; i < NUM_OF_OBSTACLES; i++)
        {
            Obstacle q = new Obstacle().setVisible(false);
            Obstacles.add(q);
        }

        return Obstacles;
    }
}
