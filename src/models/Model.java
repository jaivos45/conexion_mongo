/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package models;

/**
 * 
 * @jaivo
 */
public class Model {
  private  int x =20;

    /**
     * @return the x
     */
    public int getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(int x) {
        this.x = x;
    }
    
    public int incre(){
    this.x++;
    return x;
    }
    public int decre(){
    this.x--;
    return x;
    }

}
