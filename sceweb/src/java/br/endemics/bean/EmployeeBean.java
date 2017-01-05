/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.endemics.bean;

import br.endemics.model.Area;
import br.endemics.dao.AreaDAO;
import br.endemics.model.Employee;
import br.endemics.dao.EmployeeDAO;
import br.endemics.util.UsuarioAtivo;
import br.endemics.util.Util;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.FlowEvent;

/**
 *
 * @author Pedro Saraiva
 */
@ManagedBean(name = "bemployee")
@RequestScoped
public class EmployeeBean {

    private Employee employee = new Employee();
    private List<Employee> list;
    private EmployeeDAO dEmployee = new EmployeeDAO();
    private FacesContext context = FacesContext.getCurrentInstance();

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String save() {
        if(employee.getPasswordMobile()==null){
            employee.setPasswordMobile("");
        }
        if (employee.getName()==null || employee.getCpf()==null || employee.getRg()==null
                || employee.getGenre()=='\0' || employee.getAddress()==null ||  employee.getCity()==null
                || employee.getDistrict()==null || employee.getState()==null || employee.getCep()==null
                || employee.getDateOfBirth()==null || employee.getPhone()==null || employee.getPasswordMobile().equals("")
                || employee.getType()=='\0') {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Preencha os campos Obrigatórios (*)", ""));
            return "";
        }

        employee.setCityRegister(UsuarioAtivo.getUser().getCity());
        employee.setName(employee.getName().toUpperCase());
        employee.setAddress(employee.getAddress().toUpperCase());
        employee.setCity(employee.getCity().toUpperCase());
        employee.setDistrict(employee.getDistrict().toUpperCase());
        employee.setState(employee.getState().toUpperCase());
        employee.setCpf(employee.getCpf().replaceAll("[.-]", ""));
        employee.setPhone(employee.getPhone().replaceAll("[.-]", ""));
        employee.setCep(employee.getCep().replaceAll("[.-]", ""));
        employee.setPasswordMobile(Util.md5(employee.getPasswordMobile()));
        if (!Util.CPF(employee.getCpf())) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "CPF Inválido!", ""));
            return null;
        }
        if (employee.getId() == 0) {
            employee.setActive(true);
            dEmployee.add(employee);
            context.addMessage(null, new FacesMessage("Sucesso a Adicionar: "
                    + employee.getName(), ""));

        } else {
            dEmployee.update(employee);
            context.addMessage(null, new FacesMessage("Sucesso a Atualizar: "
                    + employee.getName(), ""));

        }

        return "/limited/employeelist.jsf";
    }

    public String newEmployee() {
        employee = new Employee();
        employee.setActive(true);
        return "/limited/employee.jsf";
    }

    public List<Employee> getList() {
        return dEmployee.list();
    }

    public String edit() {
        return "/limited/employee.jsf";
    }

    public String remove() {
        context.addMessage(null, new FacesMessage("Sucesso ao Excluir: "
                + employee.getName(), ""));

        dEmployee.remove(this.employee);
        this.list = dEmployee.list();
        return null;
    }

    private boolean skip;

    public void setSkip(boolean skip) {
        this.skip = skip;
    }

}
