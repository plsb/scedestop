/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.sce.bean;

import br.sce.area.Area;
import br.sce.area.AreaDAO;
import br.sce.cycle.Cycle;
import br.sce.cycle.CycleDAO;
import br.sce.district.District;
import br.sce.district.DistrictDAO;
import br.sce.employee.Employee;
import br.sce.employee.EmployeeDAO;
import br.sce.team.Team;
import br.sce.team.TeamDAO;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

/**
 *
 * @author Pedro Saraiva
 */
@ManagedBean(name = "bteam")
@RequestScoped
public class TeamBean {

    private Team team = new Team();
    private TeamDAO dTeam = new TeamDAO();
    private FacesContext context = FacesContext.getCurrentInstance();
    private List<Team> list;
    private List<SelectItem> employeeMasterSelect;
    private List<SelectItem> employeeSupervisedSelect;
    
    public List<Team> getList() {
        return dTeam.list();
    }

    public String save() {
        if(dTeam.verifyMasterSupervised(team.getMaster(),team.getSupervised())){
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Equipe Já Cadastrada!", ""));
            return null;
        }
                
        if (team.getId() == 0) {
             dTeam.add(team);
            context.addMessage(null, new FacesMessage("Sucesso a Adicionar: "
                    + team.getMaster().getName() +" | "+team.getSupervised().getName(), ""));

           
        } else {
            dTeam.update(team);
            context.addMessage(null, new FacesMessage("Sucesso a Atualizar: "
                    + team.getMaster().getName() +" | "+team.getSupervised().getName(), ""));
            
        }

        return "/limited/teamlist.jsf";
    }

    public String newTeam() {
        team = new Team();
        return "/limited/team.jsf";
    }

    public String edit() {
        return "/limited/team.jsf";
    }

    public String remove() {
        context.addMessage(null, new FacesMessage("Sucesso ao Excluir: "
                + team.getMaster().getName() +" | "+team.getSupervised().getName(), ""));

        dTeam.remove(this.team);
        this.list = dTeam.list();
        return null;
    }

    /**
     * @return the team
     */
    public Team getTeam() {
        return team;
    }

    /**
     * @param team the team to set
     */
    public void setTeam(Team team) {
        this.team = team;
    }

    public List<SelectItem> getEmployeeMasterSelect() {
   
       if (this.employeeMasterSelect == null) {
            this.employeeMasterSelect = new ArrayList<SelectItem>();
            //ContextoBean contextoBean = scs.util.ContextoUtil.getContextoBean();

            EmployeeDAO dao = new EmployeeDAO();
            List<Employee> categorias = dao.listMaster();
            this.showDataSelectEmpl(this.employeeMasterSelect, categorias, "");
        }

        return employeeMasterSelect;
    }

    private void showDataSelectEmpl(List<SelectItem> select, List<Employee> employees, String string) {
        SelectItem item = null;
        if (employees != null) {
            for (Employee employee : employees) {
                item = new SelectItem(employee, employee.getName());
                item.setEscape(false);

                select.add(item);

                //this.montaDadosSelect(select, usuario.getNome(), prefixo + "&nbsp;&nbsp;");
            }
        }
    }

    public List<SelectItem> getEmployeeSupervisedSelect() {
        if (this.employeeSupervisedSelect == null) {
            this.employeeSupervisedSelect = new ArrayList<SelectItem>();
            //ContextoBean contextoBean = scs.util.ContextoUtil.getContextoBean();

            EmployeeDAO dEmpl = new EmployeeDAO();
            List<Employee> categorias = dEmpl.listSupervised();
            this.showDataSelectEmpl(this.employeeSupervisedSelect, categorias, "");
        }

        return employeeSupervisedSelect;

    }

   
    
}
