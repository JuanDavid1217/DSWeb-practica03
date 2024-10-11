/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.dsweb.practica03;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
/**
 *
 * @author juan
 */
public class UserDao implements IDAOGeneral<User, Long>{
    private PreparedStatement query=null; 
    @Override
    public User create(final User user) {
        try {
            ConexionDB cx=ConexionDB.getInstance();
            TransaccionDB tdb=new TransaccionDB<User>(user){
                @Override
                public boolean execute(Connection con){
                    try{
                        query=con.prepareStatement("insert into usuarios(nombre, direccion, telefono)"+
                                " values (?, ?, ?);");
                        query.setString(1, user.getNombre());
                        query.setString(2, user.getDireccion());
                        query.setString(3, user.getTelefono());
                        query.execute();
                        return true;
                    }catch(SQLException ex){
                        Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
                        return false;
                    }
                }
            };
            boolean res=cx.execute(tdb);
            if (res)
                Logger.getLogger(UserDao.class.getName()).log(Level.INFO, "Se guardo");
            else
                Logger.getLogger(UserDao.class.getName()).log(Level.INFO, "No se guardo");
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return user;
    }

    @Override
    public boolean delete(final Long id) {
        ConexionDB cx = null;
        try {
            cx = ConexionDB.getInstance();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        TransaccionDB tdb=new TransaccionDB <Long>(id){
        
            @Override
            public boolean execute(Connection con){
                try{
                    String sql="Delete from usuarios where id=?";
                    query=con.prepareStatement(sql);
                    query.setLong(1, id);
                    query.execute();
                    return true;
                }catch(SQLException ex){
                    Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, "No se realizo");
                    return false;
                }
        
            }
        };
        boolean res=cx.execute(tdb);
        if (res)
            Logger.getLogger(UserDao.class.getName()).log(Level.INFO, "Se ha borrado");
        else
            Logger.getLogger(UserDao.class.getName()).log(Level.INFO, "No se realizo la operación solicitada");
        

        return res;
    }

    @Override
    public User update(User user, final Long id) {
        ConexionDB cx = null;
        try {
            cx = ConexionDB.getInstance();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        TransaccionDB tdb= new TransaccionDB<User>(user){
            @Override
            public boolean execute(Connection con){
                try{
                    String sql="Update usuarios set nombre=?, direccion=?, telefono=? where id=?";
                    query=con.prepareStatement(sql);
                    query.setString(1, p.getNombre());
                    query.setString(2, p.getDireccion());
                    query.setString(3, p.getTelefono());
                    query.setLong(4, id);
                    query.execute();
                    return true;
                }catch(SQLException ex){
                    Logger.getLogger(UserDao.class.getName()).log(Level.INFO, null, ex);
                    return false;
                }
            }
        };
        boolean res=cx.execute(tdb);
        if (res)
            Logger.getLogger(UserDao.class.getName()).log(Level.INFO, "Se ha actualizado");
        else
            Logger.getLogger(UserDao.class.getName()).log(Level.INFO, "No se realizo la operación solicitada");
        

        return user;  
    }

    @Override
    public List<User> findAll() {
        final List<User> listaUsuarios=new ArrayList<User>();
        ConexionDB cx = null;
        try {
            cx = ConexionDB.getInstance();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        TransaccionDB tdb=new TransaccionDB<List<User>>(listaUsuarios){
        @Override
            public boolean execute(Connection con){
                try{
                String sql="Select * from usuarios order by id asc";
                query=con.prepareStatement(sql);
                ResultSet st=query.executeQuery();
                while(st.next()){
                    listaUsuarios.add(new User(st.getLong("id"), st.getString("nombre"), st.getString("direccion"), st.getString("telefono")));
                }
                return true;
                }catch(SQLException ex){
                    Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, "No se realizo");
                    return false;
                }
        
            }
        };
        boolean res=cx.execute(tdb);
        if (res)
            Logger.getLogger(UserDao.class.getName()).log(Level.INFO, "Se ha obtenido la info de la BD");
        else
            Logger.getLogger(UserDao.class.getName()).log(Level.INFO, "No se realizo la operación solicitada");
        
        return listaUsuarios;
    }

    @Override
    public User findbyID(final Long id) {
        ConexionDB cx = null;
        try {
            cx = ConexionDB.getInstance();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        final User user = new User();
        TransaccionDB tdb=new TransaccionDB<User>(user){
        @Override
            public boolean execute(Connection con){
                try{
                String sql="Select * from usuarios where id=?";
                query=con.prepareStatement(sql);
                query.setLong(1, id);
                ResultSet st=query.executeQuery();
                if (st.next()){
                user.setId(st.getLong(1));
                user.setNombre(st.getString(2));
                user.setDireccion(st.getString(3));
                user.setTelefono(st.getString(4));}
                return true;
                }catch(SQLException ex){
                    Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, "No se realizo", ex);
                    return false;
                }
        
            }
        };
        boolean res=cx.execute(tdb);
        if (res)
            Logger.getLogger(UserDao.class.getName()).log(Level.INFO, "Se ha obtenido la info de la BD");
        else
            Logger.getLogger(UserDao.class.getName()).log(Level.INFO, "No se realizo la operación solicitada");
        
        return user;
    }
    
}
