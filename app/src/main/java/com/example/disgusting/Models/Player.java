package com.example.disgusting.Models;

public class Player
{
    private boolean Visible;

    public Player()
    {

    }

    public boolean isVisible()
    {
        return Visible;
    }

    public Player setVisible(boolean visible)
    {
        Visible = visible;
        return this;
    }
}
