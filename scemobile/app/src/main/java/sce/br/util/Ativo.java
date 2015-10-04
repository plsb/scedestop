package sce.br.util;

import sce.br.model.City;
import sce.br.model.Employee;

/**
 * Created by Pedro Saraiva on 04/10/2015.
 */
public class Ativo {

    private static Employee user;
    private static City city;

    public static Employee getUser() {
        return user;
    }

    public static void setUser(Employee user) {
        Ativo.user = user;
    }

    public static City getCity() {
        return city;
    }

    public static void setCity(City city) {
        Ativo.city = city;
    }
}
