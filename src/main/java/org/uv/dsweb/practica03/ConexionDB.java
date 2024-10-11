/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.dsweb.practica03;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author juan
 */
public class ConexionDB {
    private Connection con=null;
    
    private ConexionDB() throws ClassNotFoundException{
        
        String url="jdbc:postgresql://172.17.0.2:5432/dsweb";
        String pwd="123456";
        String user="postgres";
        
        try{
            Class.forName("org.postgresql.Driver");
            con=DriverManager.getConnection(url,user,pwd);
          Logger.getLogger(ConexionDB.class.getName()).log(Level.INFO, "Se conecto...");
        }catch(SQLException es){
            System.out.println(es);
            Logger.getLogger(ConexionDB.class.getName()).log(Level.INFO, "No se conecto");
        }
    }
    public static ConexionDB cx=null;
    public static ConexionDB getInstance() throws ClassNotFoundException{
        if(cx==null){
            cx=new ConexionDB();
        }
        return cx;
    }
    
    public boolean execute(TransaccionDB tdb){ //recibe una clase abstracta
        return tdb.execute(con);
        
    }
}
