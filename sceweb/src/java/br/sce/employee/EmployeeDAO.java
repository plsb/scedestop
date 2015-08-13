/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.sce.employee;

import br.sce.util.GenericDAO;

/**
 *
 * @author Pedro Saraiva
 */
public class EmployeeDAO extends GenericDAO<Employee>{

    public EmployeeDAO() {
        super(Employee.class);
    }
    
    
    
}
