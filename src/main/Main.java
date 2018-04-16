/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main;
import models.Model;
import view.View;
import controlers.Controler;
        
/**
 * 
 * @jaivo
 */
public class Main {
    public static void main(String [] EJHR){
        Model model=new Model(); //crear objecto
        View view=new View();
        Controler controler=new Controler(model,view);
        controler.init_view();
}

}
