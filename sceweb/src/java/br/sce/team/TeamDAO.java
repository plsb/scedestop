/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.sce.team;

import br.sce.employee.Employee;
import br.sce.util.GenericDAO;
import br.sce.util.HibernateUtil;
import java.util.List;
import javax.swing.JOptionPane;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Pedro Saraiva
 */
public class TeamDAO extends GenericDAO<Team>{

    public TeamDAO() {
        super(Team.class);
    }

    public boolean verifyMasterSupervised(Employee master, Employee supervised) {
        //método verifica se o supervisionado já está cadastrado para o supervisor
        List<Team> lista = null;
        try {
            this.setSessao(HibernateUtil.getSessionFactory().openSession());
            setTransacao(getSessao().beginTransaction());
            lista = this.getSessao().createCriteria(Team.class).
                    add(Restrictions.eq("master", master))
                    .add(Restrictions.eq("supervised",supervised)).list();
            
        } catch (Throwable e) {
            if (getTransacao().isActive()) {
                getTransacao().rollback();
            }
            JOptionPane.showMessageDialog(null, "Não foi possível listar: " + e.getMessage());
        } finally {
            getSessao().close();
        }
        if(lista.size()>0){
            return true;
        } else {
            return false;
        }
    }
    
    
    
}
