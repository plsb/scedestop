package br.endemics.bean.converte;

import br.endemics.model.User;
import br.endemics.dao.UserDAO;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@FacesConverter(forClass = User.class)
public class UsuarioConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext contex, UIComponent component, String value) {
        if (value != null && value.trim().length() > 0) {
            Integer codigo = Integer.valueOf(value);
            try {
                UserDAO uDAO = new UserDAO();
                return uDAO.checkExists("id", codigo);
            } catch (Exception e) {
                throw new ConverterException("Não foi possível encontrar o usuário de c�digo " + value + "." + e.getMessage());
            }
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext arg0, UIComponent arg1, Object value) {
        if (value != null) {
            User user = (User) value;
            return user.getId().toString();
        }
        return "";
    }

}
