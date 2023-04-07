package com.example.disgusting.Models;

public class Obstacle
{
    private boolean Visible;

    public Obstacle()
    {

    }

    public boolean isVisible()
    {
        return Visible;
    }

    public Obstacle setVisible(boolean visible)
    {
        Visible = visible;
        return this;
    }
}
