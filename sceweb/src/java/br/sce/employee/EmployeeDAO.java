/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.sce.employee;

import br.sce.area.Area;
import br.sce.city.City;
import br.sce.district.District;
import br.sce.util.GenericDAO;
import br.sce.util.HibernateUtil;
import br.sce.util.UsuarioAtivo;
import java.util.List;
import javax.swing.JOptionPane;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import scs.web.ContextBean;

/**
 *
 * @author Pedro Saraiva
 */
public class EmployeeDAO extends GenericDAO<Employee> {

    public EmployeeDAO() {
        super(Employee.class);
    }

    public List<Employee> listMaster() {
        List<Employee> lista = null;
        try {
            this.setSessao(HibernateUtil.getSessionFactory().openSession());
            setTransacao(getSessao().beginTransaction());
            lista = this.getSessao().createCriteria(Employee.class).
                    add(Restrictions.eq("cityRegister", UsuarioAtivo.getUser().getCity())).
                    add(Restrictions.eq("type", 'S')).
                    addOrder(Order.asc("name")).list();

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

    public List<Employee> listSupervised() {
        List<Employee> lista = null;
        try {
            this.setSessao(HibernateUtil.getSessionFactory().openSession());
            setTransacao(getSessao().beginTransaction());
            lista = this.getSessao().createCriteria(Employee.class).
                    add(Restrictions.eq("cityRegister", UsuarioAtivo.getUser().getCity())).
                    add(Restrictions.eq("type", 'A')).
                    addOrder(Order.asc("name")).list();

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

    public List<Employee> list() {
        List<Employee> lista = null;
        try {
            this.setSessao(HibernateUtil.getSessionFactory().openSession());
            setTransacao(getSessao().beginTransaction());
            lista = this.getSessao().createCriteria(Employee.class).
                    add(Restrictions.eq("cityRegister", UsuarioAtivo.getUser().getCity())).
                    addOrder(Order.asc("name")).list();

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

    public List<Employee> listActivesByCity(City c) {
        List<Employee> employees = null;
        try {
            this.setSessao(HibernateUtil.getSessionFactory().openSession());
            setTransacao(getSessao().beginTransaction());
            employees = this.getSessao().createCriteria(Employee.class)
                    .add(Restrictions.eq("active", true))
                    .add(Restrictions.eq("cityRegister", c))
                    .list();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return employees;
    }

}
