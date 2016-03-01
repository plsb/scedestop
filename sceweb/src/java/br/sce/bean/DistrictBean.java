/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.sce.bean;

import br.sce.area.Area;
import br.sce.area.AreaDAO;
import br.sce.district.District;
import br.sce.district.DistrictDAO;
import br.sce.util.UsuarioAtivo;
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
@ManagedBean(name = "bdistrict")
@RequestScoped
public class DistrictBean {

    private District district = new District();
    private DistrictDAO dDistrict = new DistrictDAO();
    private FacesContext context = FacesContext.getCurrentInstance();
    private List<District> list;
    private List<SelectItem> areaSelect;

    public List<District> getList() {
        return dDistrict.list();
    }

    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public String save() {
        if(district.getArea()==null || district.getDescription().equals("") || district.getZone()=='\0'){
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Preencha os campos Obrigatórios (*)", ""));
            return "";
        }
        district.setDescription(district.getDescription().toUpperCase());
        district.setCity(UsuarioAtivo.getUser().getCity());
        if(district.getDistrictId()==0){
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Código do Bairro Não Pode Ser 0", ""));
            return "";
        }
        if (district.getId() == 0) {
            dDistrict.add(district);
            context.addMessage(null, new FacesMessage("Sucesso a Adicionar: "
                    + district.getDescription(), ""));

            
        } else {
            dDistrict.update(district);
            context.addMessage(null, new FacesMessage("Sucesso a Atualizar: "
                    + district.getDescription(), ""));
            
        }

        return "/limited/districtlist.jsf";
    }

    public String newDistrict() {
        district = new District();
        return "/limited/district.jsf";
    }

    public String edit() {
        return "/limited/district.jsf";
    }

    public String remove() {
        context.addMessage(null, new FacesMessage("Sucesso ao Excluir: "
                + district.getDescription(), ""));

        dDistrict.remove(this.district);
        this.list = dDistrict.list();
        return null;
    }

    public List<SelectItem> getAreaSelect() {
        if (this.areaSelect == null) {
            this.areaSelect = new ArrayList<SelectItem>();
            //ContextoBean contextoBean = scs.util.ContextoUtil.getContextoBean();

            AreaDAO dArea = new AreaDAO();
            List<Area> categorias = dArea.list();
            this.showDataSelectArea(this.areaSelect, categorias, "");
        }

        return areaSelect;
    }

    private void showDataSelectArea(List<SelectItem> select, List<Area> areas, String prefixo) {

        SelectItem item = null;
        if (areas != null) {
            for (Area area : areas) {
                item = new SelectItem(area, area.getDescription());
                item.setEscape(false);

                select.add(item);

                //this.montaDadosSelect(select, usuario.getNome(), prefixo + "&nbsp;&nbsp;");
            }
        }
    }

}
