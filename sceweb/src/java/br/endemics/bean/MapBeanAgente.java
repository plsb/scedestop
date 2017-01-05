/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.endemics.bean;

import br.endemics.model.Cycle;
import br.endemics.dao.CycleDAO;
import br.endemics.model.Employee;
import br.endemics.dao.EmployeeDAO;
import br.endemics.model.Sample;
import br.endemics.dao.SampleDAO;
import br.endemics.util.UsuarioAtivo;
import br.endemics.model.Visit;
import br.endemics.dao.VisitDAO;
import com.google.code.geocoder.Geocoder;
import com.google.code.geocoder.GeocoderRequestBuilder;
import com.google.code.geocoder.model.GeocodeResponse;
import com.google.code.geocoder.model.GeocoderRequest;
import com.google.code.geocoder.model.GeocoderResult;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.model.SelectItem;
import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;
import org.primefaces.model.map.Polyline;

/**
 *
 * @author Pedro Saraiva
 */
@ManagedBean(name = "bmapagente")
@RequestScoped
public class MapBeanAgente implements Serializable {

    private String latlong;

    private MapModel emptyModel;

    private List<SelectItem> cycleSelect;
    private List<SelectItem> agenteSelect;
    private Cycle cycle;
    private Employee agente;

    @PostConstruct
    public void init() {
        emptyModel = new DefaultMapModel();
        String lat = String.valueOf(UsuarioAtivo.getUser().getCity().getLatitude()).replace(",", ".");
        String longi = String.valueOf(UsuarioAtivo.getUser().getCity().getLongitude()).replace(",", ".");
        latlong = lat + ", " + longi;

    }

    public MapModel getEmptyModel() {
        return emptyModel;
    }

    private Marker marker;

    public void onMarkerSelect(OverlaySelectEvent event) {
        marker = (Marker) event.getOverlay();
    }

    public Marker getMarker() {
        return marker;
    }

    public String getLatlong() {
        return latlong;
    }

    public void setLatlong(String latlong) {
        this.latlong = latlong;
    }

    public void addMark() {
        if (cycle != null) {
            final Geocoder geocoder = new Geocoder();
            VisitDAO vDAO = new VisitDAO();
            Polyline polyline = new Polyline();
            for (Visit v : vDAO.list(agente)) {
                if (v.getLatitude() != 0) {

                    String endereco = v.getStreet().getDescription() + ", "
                            + v.getNumero() + ","
                            + ", " + v.getStreet().getDistrict().getDescription() + ", "
                            + v.getStreet().getCity().getDescription()
                            + "-" + v.getStreet().getCity().getState();
                    GeocoderRequest geocoderRequest = new GeocoderRequestBuilder().setAddress(endereco).getGeocoderRequest();
                    GeocodeResponse geocoderResponse = geocoder.geocode(geocoderRequest);
                    List<GeocoderResult> results = geocoderResponse.getResults();

                    if (results != null && !results.isEmpty()) {

                        for (int i = 0; i < results.size(); i++) {

                            double latitude = results.get(i).getGeometry().getLocation().getLat().floatValue();
                            double longitude = results.get(i).getGeometry().getLocation().getLng().floatValue();

                            polyline.getPaths().add(new LatLng(latitude, longitude));

                        }

                    }

                }

            }
            polyline.setStrokeWeight(10);
            polyline.setStrokeColor("#FF9900");
            polyline.setStrokeOpacity(0.7);
            emptyModel.addOverlay(polyline);
        }
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
                        + cycle.getEndDate() + ")");
                item.setEscape(false);

                select.add(item);

                //this.montaDadosSelect(select, usuario.getNome(), prefixo + "&nbsp;&nbsp;");
            }
        }
    }

    public Cycle getCycle() {
        return cycle;
    }

    public void setCycle(Cycle cycle) {
        this.cycle = cycle;
    }

    public Employee getAgente() {
        return agente;
    }

    public void setAgente(Employee agente) {
        this.agente = agente;
    }
    
    public List<SelectItem> getAgenteSelect() {
        if (this.agenteSelect == null) {
            this.agenteSelect = new ArrayList<SelectItem>();
            //ContextoBean contextoBean = scs.util.ContextoUtil.getContextoBean();

            EmployeeDAO dAgente = new EmployeeDAO();
            List<Employee> categorias = dAgente.listSupervised();
            this.showDataSelectAgente(this.agenteSelect, categorias, "");
        }

        return agenteSelect;
    }

    private void showDataSelectAgente(List<SelectItem> select, List<Employee> employes, String prefixo) {

        SelectItem item = null;
        if (employes != null) {
            for (Employee employee : employes) {
                item = new SelectItem(employee, employee.getName());
                item.setEscape(false);

                select.add(item);

                //this.montaDadosSelect(select, usuario.getNome(), prefixo + "&nbsp;&nbsp;");
            }
        }
    }
    
    

}

