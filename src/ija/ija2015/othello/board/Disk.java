/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ija.ija2015.othello.board;

/**
 *
 * @author XKADER13, XZEMAN53
 */
public class Disk {
    
    private boolean isWhite;
    
    public Disk(boolean isWhite)
    {
        this.isWhite = isWhite;
    }
    
    //Test, zda je kámen bílý.
    public boolean isWhite()
    {
        return this.isWhite;
    }

    //Otočení (změna barvy) kamene.
    public void	turn()
    {
        this.isWhite = !this.isWhite;
    }
    
    @Override
    public boolean equals(java.lang.Object obj) 
    {
        return obj instanceof Disk && (((Disk)obj).isWhite == this.isWhite);
    }
    
    @Override
    public int hashCode() 
    {
        return this.isWhite ? 1 : 0;
    }
    
    @Override
    public String toString()
    {
        return isWhite ? "W" : "B";
    }
}