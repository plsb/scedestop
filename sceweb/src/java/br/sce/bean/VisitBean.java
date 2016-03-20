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
import br.sce.sample.Sample;
import br.sce.sample.SampleDAO;
import br.sce.util.UsuarioAtivo;
import br.sce.visit.Visit;
import br.sce.visit.VisitDAO;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.swing.JOptionPane;
import org.primefaces.context.RequestContext;
import org.primefaces.model.map.DefaultMapModel;

/**
 *
 * @author Pedro Saraiva
 */
@ManagedBean(name = "bvisit")
@SessionScoped
public class VisitBean {

    private Visit visit = new Visit();
    private List<Visit> list;
    private VisitDAO dVisit = new VisitDAO();
    private FacesContext context = FacesContext.getCurrentInstance();
    private List<SelectItem> cycleSelect;
    private Cycle cycle;
    List<Sample> amostras = new ArrayList<Sample>();
    
    public List<Sample> getAmostras() {
        if (visit.getId() != null) {
            if (!visit.getId().equals("")) {
                SampleDAO sDAo = new SampleDAO();
                amostras = sDAo.list(visit);
            }
        }
        return amostras;
    }

    public String visualizar() {
        return "/limited/visit.jsf";
    }

    public Cycle getCycle() {
        return cycle;
    }

    public void setCycle(Cycle cycle) {
        this.cycle = cycle;
    }

    public Visit getVisit() {
        return visit;
    }

    public void setVisit(Visit visit) {
        this.visit = visit;
    }

    public List<Visit> getList() {
//        return dVisit.list(cycle);
        if(cycle!=null){
            if(cycle.getId()!=null){
                return dVisit.list(cycle);
            }
        }
        return new ArrayList<>();
    }

    public String tipoImovel(String tipo) {
        switch (tipo) {
            case "R":
                return "RESIDENCIAL";
            case "C":
                return "COMÉRCIO";
            case "TB":
                return "TERRENO BALDIO";
            case "PE":
                return "PONTO ESTRATÈGICO";
            case "O":
                return "OUTROS";
        }
        return "";
    }

    private String tipoImovel;

    public String getTipoImovel() {
        if (visit != null) {

            switch (visit.getTipoImovel()) {
                case "R":
                    return "RESIDENCIAL";
                case "C":
                    return "COMÈRCIO";
                case "TB":
                    return "TERRENO BALDIO";
                case "PE":
                    return "PONTO ESTRATÈGICO";
                case "O":
                    return "OUTROS";
            }
        }
        return "";
    }

    private String tipoAtividade;

    public String getTipoAtividade() {
        if (visit != null) {

            switch (visit.getTipoAtividade()) {
                case "1":
                    return "LI - LEVANTAMENTO DE INDICE";
                case "2":
                    return "LI+T - LEVANTAMENTO DE INDICE + TRATAM.";
                case "3":
                    return "PE - PONTO ESTRATÉGICO";
                case "4":
                    return "T - TRATAMENTO";
                case "5":
                    return "DF - DELIMITAÇÃO DE FOCO";
                case "6":
                    return "PVE - PESQUISA VETORIAL ESPACIAL";
            }
        }
        return "";
    }

    public List<SelectItem> getCycleSelect() {
        if (this.cycleSelect == null) {
            this.cycleSelect = new ArrayList<SelectItem>();
            //ContextoBean contextoBean = scs.util.ContextoUtil.getContextoBean();

            CycleDAO dCycle = new CycleDAO();
            List<Cycle> categorias = dCycle.list();
            this.showDataSelectCycle(this.cycleSelect, categorias, "");
        }

        return cycleSelect;
    }

    private void showDataSelectCycle(List<SelectItem> select, List<Cycle> cycles, String prefixo) {

        SelectItem item = null;
        if (cycles != null) {
            for (Cycle cycle : cycles) {
                item = new SelectItem(cycle, cycle.getDescription() + " (Inicio: " + cycle.getStartDate() + ", Fim: "
                        + cycle.getEndDate()+")");
                item.setEscape(false);

                select.add(item);

                //this.montaDadosSelect(select, usuario.getNome(), prefixo + "&nbsp;&nbsp;");
            }
        }
    }
    
    public String pesquisa(){
        return "";
    }

}
