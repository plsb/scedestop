/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.endemics.dao;

import br.endemics.model.User;
import br.endemics.util.GenericDAO;

/**
 *
 * @author Pedro Saraiva
 */
public class UserDAO extends GenericDAO<User>{

    public UserDAO() {
        super(User.class);
    }
    
    
    
}
