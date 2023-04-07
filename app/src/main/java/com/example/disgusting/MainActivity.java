package com.example.disgusting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;

import com.example.disgusting.Logic.GameManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity
{
    private final int DELAY = 1000;
    private ShapeableImageView[] main_IMG_hearts;
    private FloatingActionButton timer_FAB_left;
    private FloatingActionButton timer_FAB_right;
    private ShapeableImageView[] main_IMG_Obstacles;
    private ShapeableImageView[] main_IMG_Players;
    private Timer timer;

    private GameManager gameManager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();
        gameManager = new GameManager(main_IMG_hearts.length);
        initialUI();
        movementClickListener();
        startTimer();
    }


    private void startTimer()
    {
        if (timer == null)
        {
            timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask()
            {
                @Override
                public void run()
                {
                    runOnUiThread(() ->
                    {
                        refreshUI();
                    });
                }
            }, 0, DELAY);
        }
    }

    private void refreshUI()
    {
        for (int i = main_IMG_Obstacles.length - 1; i >= 0; i--) //backwards, otherwise overlaps with ones that were adjusted to higher index
        {
            if (gameManager.isObstacleVisible(i))
            {
                gameManager.setObstacleInvisible(i);
                main_IMG_Obstacles[i].setVisibility(View.INVISIBLE);
                if (!gameManager.isObstacleLastRow(i))
                {
                    gameManager.setObstacleVisible(gameManager.newObstacleIndex(i));
                    main_IMG_Obstacles[gameManager.newObstacleIndex(i)].setVisibility(View.VISIBLE);
                } else
                {
                    Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    if (gameManager.checkCollision(i, getApplicationContext(), v))
                    {
                        if (gameManager.getWrong() != 0 && gameManager.getWrong() <= 3)
                            main_IMG_hearts[main_IMG_hearts.length - gameManager.getWrong()].setVisibility(View.INVISIBLE);
                        if (gameManager.getWrong() == 0) //only for this assignment, if 0 wrongs (due to %3 on 3 wrongs OR start of game), give 3 hearts
                        {
                            for (ShapeableImageView main_img_heart : main_IMG_hearts) //for each heart make visible
                                main_img_heart.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }
        }
        if (gameManager.spawnObstacle())
            main_IMG_Obstacles[gameManager.randomObstacle()].setVisibility(View.VISIBLE);

    }

    private void initialUI()
    {
        for (ShapeableImageView main_img_obstacle : main_IMG_Obstacles) //for each obstacle make invisible
        {
            main_img_obstacle.setVisibility(View.INVISIBLE);
        }

        for (ShapeableImageView main_img_player : main_IMG_Players) //for each player make invisible
        {
            main_img_player.setVisibility(View.INVISIBLE);
        }
        main_IMG_Players[gameManager.getVisiblePlayerIndex()].setVisibility(View.VISIBLE);

    }


    private void findViews()
    {

        main_IMG_hearts = new ShapeableImageView[]
                {
                        findViewById(R.id.main_IMG_heart1),
                        findViewById(R.id.main_IMG_heart2),
                        findViewById(R.id.main_IMG_heart3)
                };

        main_IMG_Obstacles = new ShapeableImageView[]
                {
                        findViewById(R.id.main_IMG_obstacle1),
                        findViewById(R.id.main_IMG_obstacle2),
                        findViewById(R.id.main_IMG_obstacle3),
                        findViewById(R.id.main_IMG_obstacle4),
                        findViewById(R.id.main_IMG_obstacle5),
                        findViewById(R.id.main_IMG_obstacle6),
                        findViewById(R.id.main_IMG_obstacle7),
                        findViewById(R.id.main_IMG_obstacle8),
                        findViewById(R.id.main_IMG_obstacle9),
                        findViewById(R.id.main_IMG_obstacle10),
                        findViewById(R.id.main_IMG_obstacle11),
                        findViewById(R.id.main_IMG_obstacle12),
                };

        main_IMG_Players = new ShapeableImageView[]
                {
                        findViewById(R.id.main_IMG_player1),
                        findViewById(R.id.main_IMG_player2),
                        findViewById(R.id.main_IMG_player3)
                };

        timer_FAB_left = findViewById(R.id.timer_FAB_left);
        timer_FAB_right = findViewById(R.id.timer_FAB_right);
    }

    private void movementClickListener()
    {
        timer_FAB_left.setOnClickListener(v -> moveLeft());
        timer_FAB_right.setOnClickListener(v -> moveRight());
    }

    private void moveLeft()
    {
        main_IMG_Players[gameManager.getVisiblePlayerIndex()].setVisibility(View.INVISIBLE);
        gameManager.moveLeft();
        main_IMG_Players[gameManager.getVisiblePlayerIndex()].setVisibility(View.VISIBLE);
    }

    private void moveRight()
    {
        main_IMG_Players[gameManager.getVisiblePlayerIndex()].setVisibility(View.INVISIBLE);
        gameManager.moveRight();
        main_IMG_Players[gameManager.getVisiblePlayerIndex()].setVisibility(View.VISIBLE);
    }
}