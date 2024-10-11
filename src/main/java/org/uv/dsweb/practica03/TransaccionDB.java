/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.dsweb.practica03;

import java.sql.Connection;

/**
 *
 * @author Juan
 */
public abstract class TransaccionDB<T> {
    protected T p;
    protected TransaccionDB(T p){
        this.p=p;
    }
    public abstract boolean execute(Connection con);
}