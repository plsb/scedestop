package sce.br.util;

import sce.br.model.City;
import sce.br.model.Cycle;
import sce.br.model.Employee;

/**
 * Created by Pedro Saraiva on 04/10/2015.
 */
public class Ativo {

    private static Employee user;
    private static City city;
    private static Cycle cycle;

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

    public static Cycle getCycle() {
        return cycle;
    }

    public static void setCycle(Cycle cycle) {
        Ativo.cycle = cycle;
    }
}
