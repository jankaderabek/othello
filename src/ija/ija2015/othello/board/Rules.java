/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ija.ija2015.othello.board;

/**
 * Interface pro reprezentaci pravidel hry.
 *
 * @author XKADER13, XZEMAN53
 */
public interface Rules {

    /**
     * Vytvoří odpovídající pole na zadaných indexech.
     * @param row Radek
     * @param col Sloupec
     * @return Pole
     */
    public Field createField(int row, int col);

    /**
     * Vrací velikost desky.
     * @return Size
     */
    public int getSize();

    /**
     * Vrací počet kamenů jednotlivých hráčů
     * @return Počet disků
     */
    public int numberDisks();
}
