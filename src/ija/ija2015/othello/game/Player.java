/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ija.ija2015.othello.game;

import ija.ija2015.othello.board.*;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Stack;
import java.util.function.Function;

/**
 * Reprezentuje hráče v rámci hry.
 *
 * @author XKADER13, XZEMAN53
 */
public class Player
{
    protected boolean isWhite;
    protected boolean isHuman;
    protected String name;
    protected Queue<Disk> disks;
    protected Board board;

    /**
     * Konstruktor s nastavením jestli je to člověk a jménem.
     *
     * @param isWhite Bílý hráč?
     * @param isHuman Člověk?
     * @param name Jméno
     */
    public Player(boolean isWhite, boolean isHuman, String name)
    {
        this.isWhite = isWhite;
        this.isHuman = isHuman;
        this.name = name;
        this.disks = new ArrayDeque<>();
    }

    /**
     * Jednoduchý konstruktor
     *
     * @param isWhite Bílý hráč?
     */
    public Player(boolean isWhite)
    {
        this(isWhite, true, "Unknown");
    }


    public boolean isWhite()
    {
        return this.isWhite;
    }

    public boolean isHuman()
    {
        return this.isHuman;
    }

    public String getName()
    {
        return name;
    }

    public boolean emptyPool()
    {
        return this.disks.isEmpty();
    }

    /**
     * Spočítá skóre daného hráče.
     *
     * @return Skóre
     */
    public int getScore()
    {
        int score = 0;

        for (int row = 1; row <= board.getSize(); row++)
        {
            for (int col = 1; col <= board.getSize(); col++)
            {
                if (!board.getField(row, col).isEmpty() && board.getField(row, col).getDisk().isWhite() == isWhite())
                {
                    score++;
                }
            }
        }
        return score;
    }


    /**
     * Inicializace hráče v rámci hrací desky.
     *
     * @param board Deska
     */
    public void init(Board board)
    {
        for (int i = 0; i < board.getSize() * board.getSize(); i++)
        {
            this.disks.add(new Disk(this.isWhite));
        }

        if (this.isWhite)
        {
            board.getField(board.getSize() / 2, board.getSize() / 2).putDisk(this.disks.poll());
            board.getField(board.getSize() / 2 + 1, board.getSize() / 2 + 1).putDisk(this.disks.poll());
        }
        else
        {
            board.getField(board.getSize() / 2, board.getSize() / 2 + 1).putDisk(this.disks.poll());
            board.getField(board.getSize() / 2 + 1, board.getSize() / 2).putDisk(this.disks.poll());
        }

        this.board = board;
    }

    /**
     * Zjistí možné tahy hráče.
     *
     * @return ArrayList [row][col]
     */
    public ArrayList<int[]> PossibleTurns()
    {
        ArrayList<int[]> turns = new ArrayList<int[]>();

        for (int row = 1; row <= board.getSize(); row++)
        {
            for (int col = 1; col <= board.getSize(); col++)
            {
                if (canPutDisk(board.getField(row, col)))
                {
                    turns.add(new int[] { row, col});
                }
            }
        }

        return turns;
    }

    /**
     * Test, zda je možné vložit nový kámen hráče na dané pole.
     *
     * @param field Pole
     * @return Úspěch
     */
    public boolean canPutDisk(Field field)
    {
        return field.canPutDisk(this.disks.peek());
    }

    /**
     * Vloží nový kámen hráče na dané pole, pokud to pravidla umožňují.
     *
     * @param field Pole
     * @return Úspěch
     */
    public boolean putDisk(Field field)
    {
        if (this.emptyPool())
            return false;

        if (!field.canPutDisk(this.disks.peek()))
            return false;

        if (!field.putDisk(this.disks.peek()))
            return false;

        this.disks.poll();

        return true;
    }

    @Override
    public String toString()
    {
        return this.isWhite ? "W" : "B";
    }

}

