/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.endemics.util;

/**
 *
 * @author Pedro Saraiva
 */
public class Main {
    
    public static void main(String[] args) {
        new HibernateUtil().getSessionFactory().openSession();
    }
    
}
