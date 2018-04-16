
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controlers;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import models.Model;
import view.View;



import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.Mongo;
import com.mongodb.DBCollection;
import com.mongodb.DB;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.JOptionPane;


/**
 * 
 * @jaivo
 */
public class Controler implements ActionListener{
  private  Model model;
  private View view;
  
  public Controler(Model model, View view){
        //acceso a los botones 
      this.model=model;
      this.view=view;
      
      this.view.jB_ver.addActionListener(this); 
      this.view.jB_actualizar.addActionListener(this);
       this.view.jB_guardar.addActionListener(this); 
      this.view.jB_borrar.addActionListener(this);
      
  }

  public void init_view(){
// para mostrar la interfaz
    this.view.setTitle("incremento y decremento");
    this.view.setLocationRelativeTo(null);
    this.view.setVisible(true);
     // MongoClient mongo = conexionMongo();
}
  
  
private static MongoClient conexionMongo(){
        
    //Instanciamos objeto de tipo MongoClient
    MongoClient mc = null;
    //Bloque try-catch que establece la conexión si no hay errores.
    try{
        //Ruta en donde se aloja en servidor la base de datos
        mc = new MongoClient("localhost", 27017);
    }
    catch(Exception e){
        System.out.println("Error: "+e);
    }
    return mc;
    }
    
  
  
     public void  consulta(MongoClient mongo){
            
            //Seleccionamos la base de datos 'prueba'
            DB db = mongo.getDB("SYSARP");
            //Seleccionamos la colección 'contactos'
            
            String coleccion = (String) view.jComboBox1.getSelectedItem();
            DBCollection col = db.getCollection(coleccion);
            
            try( //Agregamos todos los elementos de la colección a un
      //objeto de tipo cursor.
                    DBCursor elementos = col.find()) {
                while(elementos.hasNext()){
                    //Seleccionamos cada elemento de la colección
                    //Y se imprime
                    DBObject obj = elementos.next() ;
                    System.out.println(obj);
                     view.jT_vista.setLineWrap(true);
                    this.view.jT_vista.append(obj.toString()+ "\n");
                }
                
            }
            catch(Exception e){
                System.out.println("Error: "+e);
            }
        }
        
     
     


        
        public void guardar(MongoClient mongo){
            
            //Seleccionamos la base de datos 'prueba'
            DB db = mongo.getDB("SYSARP");
            //Seleccionamos la colección 'contactos' dentro de esa base de datos
            
            String coleccion = (String) view.jComboBox1.getSelectedItem();
            DBCollection col = db.getCollection("CLIENTES");
            //Tomamos los datos de las cajas
            String nombre = view.jT_nombre.getText();
            int id = Integer.parseInt(view.jT_id.getText());
            String ap = view.jT_ap.getText();
            String edad = view.jT_edad.getText();
            String ciudad = view.jT_ciudad.getText();
            String calle = view.jT_calle.getText();
            //Creamos objeto con los elementos que se han de añadir
            DBObject obj = new BasicDBObject().append("_id",id).
                    append("nombre", nombre).append("apellido", ap).append("edad", edad).append("calle", calle).append("ciudad", ciudad);
            try{
                col.insert(obj);
                 consulta(mongo);
                 JOptionPane.showMessageDialog(view, "Guardado exitosamente");
            }
            catch(Exception e){
                 JOptionPane.showMessageDialog(view,"Error: "+e);
                
            }
        }
        
        public  void eliminar(MongoClient mongo){
            DB db = mongo.getDB("SYSARP");
            DBCollection col = db.getCollection("CLIENTES");
            try{
                //Instrucción que va a eliminar los elementos especificados
                int id = Integer.parseInt(view.jT_id.getText());
                col.remove(new BasicDBObject("_id",id));
                 JOptionPane.showMessageDialog(view, "Eliminado exitosamente");
                consulta(mongo);
            }
            catch(Exception e){
                 JOptionPane.showMessageDialog(view, "Error: "+e);
            }
        }
        
  void verifica_id(){
    String a = view.jT_id.getText().toString();
    if(    a.equalsIgnoreCase("")
        ){
          
           JOptionPane.showMessageDialog(view, "PARA ELIMINAR INTRODUCA EL ID");
          }else {
       
            MongoClient mongo = conexionMongo();
            eliminar(mongo);
          }
  }
        
        
        public void modificar(MongoClient mongo){
            DB db = mongo.getDB("SYSARP");
            DBCollection col = db.getCollection("CLIENTES");
            try{
                //Instrucción que modifica los elementos
                int id = Integer.parseInt(view.jT_id.getText());
                String nombre = view.jT_nombre.getText();
                String ap = view.jT_ap.getText();
                String edad = view.jT_edad.getText();
                String calle = view.jT_calle.getText();
                String ciudad = view.jT_ciudad.getText();
                
                
                
                col.update( new BasicDBObject("_id",id), //Id que queremos modificar
                            new BasicDBObject("$set",//operador para añadir un elemento
                            new BasicDBObject("nombre",nombre)
                            ));
                col.update( new BasicDBObject("_id",id), //Id que queremos modificar
                            new BasicDBObject("$set",//operador para añadir un elemento
                            new BasicDBObject("apellido",ap)
                            ));
                col.update( new BasicDBObject("_id",id), //Id que queremos modificar
                            new BasicDBObject("$set",//operador para añadir un elemento
                            new BasicDBObject("edad",edad)
                            ));
                col.update( new BasicDBObject("_id",id), //Id que queremos modificar
                            new BasicDBObject("$set",//operador para añadir un elemento
                            new BasicDBObject("calle",calle)
                            ));
                col.update( new BasicDBObject("_id",id), //Id que queremos modificar
                            new BasicDBObject("$set",//operador para añadir un elemento
                            new BasicDBObject("ciudad",ciudad)
                            ));
                            
               JOptionPane.showMessageDialog(view,"Actualizado exitosamente.");
            }
            catch(Exception e){
                
                JOptionPane.showMessageDialog(view,"Error: "+e);
            }
        }
       
        
  

    @Override
    public void actionPerformed(ActionEvent e) {
   
        
        if (e.getSource() ==this.view.jB_ver){
       
           view.jT_vista.setText("");
            MongoClient mongo = conexionMongo();
            consulta(mongo);
           
        
        }
      
        else if (e.getSource() ==this.view.jB_guardar){
             view.jT_vista.setText("");
            MongoClient mongo = conexionMongo();
            guardar(mongo);
           
        
           
        }
        else if (e.getSource() ==this.view.jB_borrar){
            view.jT_vista.setText("");
           verifica_id();
            
            
    }
        
           else if (e.getSource() ==this.view.jB_actualizar){
            view.jT_vista.setText("");
            MongoClient mongo = conexionMongo();
            modificar(mongo);
            consulta(mongo);
            
            
    }
    }
  
}
