/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.sce.cycle;

import br.sce.area.Area;
import br.sce.city.City;
import br.sce.util.GenericDAO;
import br.sce.util.HibernateUtil;
import br.sce.util.UsuarioAtivo;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import scs.web.ContextBean;

/**
 *
 * @author Pedro Saraiva
 */
public class CycleDAO extends GenericDAO<Cycle>{

    public CycleDAO() {
        super(Cycle.class);
    }
    
    public List<Cycle> list() {
        List<Cycle> lista = null;
        try {
            this.setSessao(HibernateUtil.getSessionFactory().openSession());
            setTransacao(getSessao().beginTransaction());
            lista = this.getSessao().createCriteria(Cycle.class).
                    add(Restrictions.eq("city", UsuarioAtivo.getUser().getCity())).
                    addOrder(Order.desc("endDate")).list();
            
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
    
    public List<Cycle> listCyclesByCity(City c, Date data) {
        List<Cycle> cycles = null;
        try {
            this.setSessao(HibernateUtil.getSessionFactory().openSession());
            setTransacao(getSessao().beginTransaction());
            cycles = this.getSessao().createCriteria(Cycle.class)
                    .add(Restrictions.le("startDate", data))
                    .add(Restrictions.ge("endDate", data))
                    .add(Restrictions.eq("city", c))
                    .list();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return cycles;
    }

    
}
