/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.sce.bean.converte;

import br.sce.area.Area;
import br.sce.area.AreaDAO;
import br.sce.cycle.Cycle;
import br.sce.cycle.CycleDAO;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Pedro Saraiva
 */
@FacesConverter(forClass = Cycle.class)
public class CycleConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext contex, UIComponent componente, String value) {
        if (value != null && value.trim().length() > 0) {
            Integer codigo = Integer.valueOf(value);
            try {
                CycleDAO cDAO = new CycleDAO();
                return cDAO.checkExists("id", codigo).get(0);
            } catch (Exception e) {
                throw new ConverterException("Não foi possível encontrar o ciclo de código " + value + "." + e.getMessage());
            }
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext arg0, UIComponent arg1, Object value) {
        if (value != null) {
            Cycle c = (Cycle) value;
            return c.getId().toString();
        }
        return "";
    }

}
