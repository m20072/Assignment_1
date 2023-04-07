package com.example.disgusting.Logic;


import static com.example.disgusting.Logic.DataManager.COLS;

import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.widget.Toast;

import com.example.disgusting.Models.Obstacle;
import com.example.disgusting.Models.Player;

import java.util.ArrayList;
import java.util.Random;

public class GameManager
{
    private final int life;
    private int wrong;
    private int visiblePlayerIndex;
    private static int random;
    private final static Random ran = new Random();
    private final ArrayList<Obstacle> obstacles;
    private final ArrayList<Player> players;


    public GameManager(int life)
    {
        this.life = life;
        this.wrong = 0;
        this.visiblePlayerIndex = 1;
        random = 0;
        obstacles = DataManager.getObstacles();
        players = DataManager.getPlayers();
        getCurrentVisiblePlayer().setVisible(true);
    }


    public boolean spawnObstacle()
    {
        // 50% chance obstacle spawn
        return ran.nextInt(2) == 1;
    }

    public int randomObstacle()
    {
        random = ran.nextInt(COLS);//equal chance to spawn at each of the columns
        obstacles.get(random).setVisible(true);
        return random;//for UI to also set visible
    }

    public boolean isObstacleVisible(int obstacleIndex)
    {
        return obstacles.get(obstacleIndex).isVisible();
    }


    public int newObstacleIndex(int oldObstacleIndex)
    {
        return oldObstacleIndex + COLS;
    }

    public boolean isObstacleLastRow(int obstacleIndex)
    {
        return obstacleIndex >= obstacles.size() - COLS;
    }


    public void setObstacleVisible(int obstacleIndex)
    {
        obstacles.get(obstacleIndex).setVisible(true);
    }

    public void setObstacleInvisible(int obstacleIndex)
    {
        obstacles.get(obstacleIndex).setVisible(false);
    }

    private Player getCurrentVisiblePlayer()
    {
        return players.get(visiblePlayerIndex);
    }

    public int getVisiblePlayerIndex()
    {
        return visiblePlayerIndex;
    }

    public void setVisiblePlayerIndex(int visiblePlayerIndex)//on button click
    {
        if (visiblePlayerIndex >= 0) this.visiblePlayerIndex = visiblePlayerIndex;
        else this.visiblePlayerIndex = players.size() - 1;

    }

    public boolean checkCollision(int obstacleIndex, Context context, Vibrator v)
    {
        if (obstacleIndex - (obstacles.size() - COLS) == getVisiblePlayerIndex())
        {
            //this.wrong++;// once its not longer 3 lives after death, put this back instead
            this.wrong = (this.wrong + 1) % 3; //remove for second assignment
            Toast.makeText(context, "HIT!", Toast.LENGTH_LONG).show();
            // Vibrate for 500 milliseconds
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
            } else
            {
                //deprecated in API 26
                v.vibrate(500);
            }
            return true;
        }
        //can add score if we reach here.
        return false;

    }

    public void moveLeft()
    {
        getCurrentVisiblePlayer().setVisible(false);
        setVisiblePlayerIndex((getVisiblePlayerIndex() - 1));
        getCurrentVisiblePlayer().setVisible(true);
    }

    public void moveRight()
    {
        getCurrentVisiblePlayer().setVisible(false);
        setVisiblePlayerIndex((getVisiblePlayerIndex() + 1) % 3);
        getCurrentVisiblePlayer().setVisible(true);
    }

    public int getWrong()
    {
        return wrong;
    }


    public boolean isLose()
    {
        return life == wrong;
    } //not for first assignment
}