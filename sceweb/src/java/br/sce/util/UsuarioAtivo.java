/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.sce.util;

import br.sce.user.User;

/**
 *
 * @author Pedro Saraiva
 */
public class UsuarioAtivo {
    
    private static User user;

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        UsuarioAtivo.user = user;
    }

   
    
    
    
    
}
