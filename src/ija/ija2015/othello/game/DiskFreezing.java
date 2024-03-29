package ija.ija2015.othello.game;

import ija.ija2015.othello.board.Board;
import ija.ija2015.othello.board.Disk;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Zapouzdřuje funkčnost zamrzání kamenů.
 *
 * @author XKADER13, XZEMAN53
 */
public class DiskFreezing
{
    private Game game;
    private Random rand;
    private ActionListener freezeListener;

    private int timerInterval;
    private int freezeInterval;
    private int freezeCount;

    private boolean unfreeze;
    private boolean isFreeze;

    private Timer freezeTimer;
    private Timer unfreezeTimer;

    /**
     * Konstruktor pro třídu DiskFreezing
     * @param game Hra pro kterou se zamrzání nastaví
     * @param timerInterval Interval v kterém dojde k zamrznutí
     * @param freezeInterval Interval na jak dlouho dojde k zamrznutí
     * @param freezeCount Interval kolik disků může zamrznout
     */
    public DiskFreezing(Game game, int timerInterval, int freezeInterval, int freezeCount)
    {
        this.game = game;
        this.rand = new Random();

        this.timerInterval = timerInterval;
        this.freezeInterval = freezeInterval;
        this.freezeCount = freezeCount;

        this.freezeTimer = new Timer();
        this.unfreezeTimer = new Timer();
    }

    //region Public

    /**
     * Nastaví zamrznutí a odmrazí kameny které zůstaly zamrznuté.
     */
    public void SetFreeze()
    {
        if (!unfreeze && isFreeze)
            return;

        if (isFreeze)
            UnfreezeAll();

        setFreezeTimer();
    }

    /**
     * Odmrazí všechny kameny.
     */
    public void UnfreezeAll()
    {
        getDisks().forEach(disk -> disk.unfreeze());
    }

    //endregion

    //region Private

    /**
     * Provede zmražení podle nastavených parametrů.
     */
    private void freezeDisks()
    {
        ArrayList<Disk> disks = getDisks();

        if (disks.size() <= 6)
            return;

        int toFreeze = randInt(0, freezeCount);
        toFreeze = toFreeze > disks.size() ? disks.size() : toFreeze;

        for (int i = 0; i < toFreeze; i++)
        {
            disks.get(randInt(0, disks.size() - 1)).freeze();
        }

        unfreeze = false;
        isFreeze = true;

        if (freezeListener != null)
            freezeListener.actionPerformed(new ActionEvent(this, 0, ""));
    }

    /**
     * Nastaví timer, který nastaví pokyn k odmražení kamenů.
     */
    private void setUnfreezeTimer()
    {
        unfreezeTimer.cancel();
        unfreezeTimer = new Timer();
        unfreezeTimer.schedule(new TimerTask() {

            @Override
            public void run()
            {
                unfreeze = true;
            }

        }, randInt(0, freezeInterval) * 1000);
    }

    /**
     * Nastaví timer, který po dané době zmrazí kameny.
     */
    private void setFreezeTimer()
    {
        freezeTimer.cancel();
        freezeTimer = new Timer();
        freezeTimer.schedule(new TimerTask() {

            @Override
            public void run()
            {
                freezeDisks();
                setUnfreezeTimer();
            }

        }, randInt(0, timerInterval) * 1000);
    }

    /**
     * Načte a vrátí všechny disky na hracím poli.
     * @return Všechny disky
     */
    private ArrayList<Disk> getDisks()
    {
        ArrayList<Disk> disks = new ArrayList<>();
        Board board = game.getBoard();

        for (int row = 1; row <= board.getSize(); row++)
        {
            for (int col = 1; col <= board.getSize(); col++)
            {
                if (!board.getField(row, col).isEmpty())
                    disks.add(board.getField(row, col).getDisk());
            }
        }

        return disks;
    }

    //endregion

    //region Getters/Setters

    public void setFreezeListener(ActionListener freezeListener)
    {
        this.freezeListener = freezeListener;
    }

    public int getTimerInterval()
    {
        return timerInterval;
    }

    public void setTimerInterval(int timerInterval)
    {
        this.timerInterval = timerInterval;
    }

    public int getFreezeInterval()
    {
        return freezeInterval;
    }

    public void setFreezeInterval(int freezeInterval)
    {
        this.freezeInterval = freezeInterval;
    }

    public int getFreezeCount()
    {
        return freezeCount;
    }

    public void setFreezeCount(int freezeCount)
    {
        this.freezeCount = freezeCount;
    }

    //endregion

    private int randInt(int min, int max)
    {
        return rand.nextInt((max - min) + 1) + min;
    }
}
