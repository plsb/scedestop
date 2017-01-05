/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.endemics.bean;

import br.endemics.model.Area;
import br.endemics.dao.AreaDAO;
import br.endemics.model.Cycle;
import br.endemics.dao.CycleDAO;
import br.endemics.model.Sample;
import br.endemics.dao.SampleDAO;
import br.endemics.util.UsuarioAtivo;
import br.endemics.model.Visit;
import br.endemics.dao.VisitDAO;
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
@ManagedBean(name = "bsample")
@SessionScoped
public class SampleBean {

    private Sample sample = new Sample();
    private List<Sample> list;
    private SampleDAO dSample = new SampleDAO();
    private FacesContext context = FacesContext.getCurrentInstance();
    private List<SelectItem> cycleSelect;
    private Cycle cycle;
    

    public String edit() {
        return "/limited/sample.jsf";
    }
    
    public String save(){
        dSample.update(sample);
        //context.addMessage(null, new FacesMessage("Sucesso a Atualizar Amostra! ", ""));
        return "/limited/samplelist.jsf";
    }

    public Cycle getCycle() {
        return cycle;
    }

    public void setCycle(Cycle cycle) {
        this.cycle = cycle;
    }

    public Sample getSample() {
        return sample;
    }

    public void setSample(Sample sample) {
        this.sample = sample;
    }

    
    public List<Sample> getList() {
//        return dVisit.list(cycle);
        if(cycle!=null){
            if(cycle.getId()!=null){
                return dSample.list(cycle);
            }
        }
        return new ArrayList<>();
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
