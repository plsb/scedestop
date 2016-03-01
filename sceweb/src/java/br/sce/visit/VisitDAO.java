/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.sce.visit;

import br.sce.city.City;
import br.sce.cycle.Cycle;
import br.sce.cycle.CycleDAO;
import br.sce.employee.Employee;
import br.sce.util.GenericDAO;
import br.sce.util.HibernateUtil;
import br.sce.util.UsuarioAtivo;
import java.util.List;
import javax.swing.JOptionPane;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Pedro Saraiva
 */
public class VisitDAO extends GenericDAO<Visit>{

    public VisitDAO() {
        super(Visit.class);
    }
     
    public List<Visit> list() {
        List<Visit> lista = null;
        try {
           
            this.setSessao(HibernateUtil.getSessionFactory().openSession());
            setTransacao(getSessao().beginTransaction());
            lista = this.getSessao().createCriteria(Visit.class).
                    add(Restrictions.eq("city", UsuarioAtivo.getUser().getCity())).
                    addOrder(Order.desc("data")).list();

        } catch (Throwable e) {
            if (getTransacao().isActive()) {
                getTransacao().rollback();
            }
            JOptionPane.showMessageDialog(null, "Não foi possível listar: " + e.getMessage());
        } finally {
            getSessao().close();
        }
        return lista;
    }
    
    public List<Visit> list(Cycle c) {
        List<Visit> lista = null;
        try {
            this.setSessao(HibernateUtil.getSessionFactory().openSession());
            setTransacao(getSessao().beginTransaction());
            lista = this.getSessao().createCriteria(Visit.class).
                    add(Restrictions.eq("city", UsuarioAtivo.getUser().getCity())).
                    add(Restrictions.eq("ciclo", c)).
                    addOrder(Order.desc("data")).list();

        } catch (Throwable e) {
            if (getTransacao().isActive()) {
                getTransacao().rollback();
            }
            JOptionPane.showMessageDialog(null, "Não foi possível listar: " + e.getMessage());
        } finally {
            getSessao().close();
        }
        return lista;
    }
    
    public List<Visit> listImoFechados(Cycle cycle) {
        List<Visit> lista = null;
        try {
            this.setSessao(HibernateUtil.getSessionFactory().openSession());
            setTransacao(getSessao().beginTransaction());
            lista = this.getSessao().createCriteria(Visit.class).
                    add(Restrictions.eq("tipoVisita", "FECHADA")).
                    add(Restrictions.eq("ciclo", cycle)).
                    addOrder(Order.asc("data")).list();

        } catch (Throwable e) {
            if (getTransacao().isActive()) {
                getTransacao().rollback();
            }
            JOptionPane.showMessageDialog(null, "Não foi possível listar: " + e.getMessage());
        } finally {
            getSessao().close();
        }
        return lista;
    }

    public List<Visit> list(Employee agente) {
                List<Visit> lista = null;
        try {
           
            this.setSessao(HibernateUtil.getSessionFactory().openSession());
            setTransacao(getSessao().beginTransaction());
            lista = this.getSessao().createCriteria(Visit.class).
                    add(Restrictions.eq("agente", agente)).
                    add(Restrictions.eq("city", UsuarioAtivo.getUser().getCity())).
                    addOrder(Order.asc("data")).list();

        } catch (Throwable e) {
            if (getTransacao().isActive()) {
                getTransacao().rollback();
            }
            JOptionPane.showMessageDialog(null, "Não foi possível listar: " + e.getMessage());
        } finally {
            getSessao().close();
        }
        return lista;


    }
    
    
    
    
    
}
