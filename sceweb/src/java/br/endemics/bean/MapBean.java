/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.endemics.bean;

import br.endemics.model.Cycle;
import br.endemics.dao.CycleDAO;
import br.endemics.model.Sample;
import br.endemics.dao.SampleDAO;
import br.endemics.util.UsuarioAtivo;
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
import org.primefaces.model.map.Circle;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

/**
 *
 * @author Pedro Saraiva
 */
@ManagedBean(name = "bmap")
@RequestScoped
public class MapBean implements Serializable {

    private String latlong;

    private MapModel emptyModel;

    private List<SelectItem> cycleSelect;
    private Cycle cycle;

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
            SampleDAO sDAO = new SampleDAO();
            for (Sample s : sDAO.list(cycle)) {
                if (s.getVisit().getLatitude() != 0) {
                    String icone, txt;
                    if (s.getConfirmada() == 0) {
                        icone = "http://maps.google.com/mapfiles/ms/micons/green-dot.png";
                        txt = "Não Confirmada";
                    } else if (s.getConfirmada() == 1) {
                        icone = "http://maps.google.com/mapfiles/ms/micons/red-dot.png";
                        txt = "Confirmada";
                    } else {
                        icone = "http://maps.google.com/mapfiles/ms/micons/yellow-dot.png";
                        txt = "A analisar";
                    }
                    String endereco = s.getVisit().getStreet().getDescription() + ", "
                            + s.getVisit().getNumero() + ","
                            + ", " + s.getVisit().getStreet().getDistrict().getDescription() + ", "
                            + s.getVisit().getStreet().getCity().getDescription()
                            + "-" + s.getVisit().getStreet().getCity().getState();
                    GeocoderRequest geocoderRequest = new GeocoderRequestBuilder().setAddress(endereco).getGeocoderRequest();
                    GeocodeResponse geocoderResponse = geocoder.geocode(geocoderRequest);
                    List<GeocoderResult> results = geocoderResponse.getResults();

                    if (results != null && !results.isEmpty()) {

                        for (int i = 0; i < results.size(); i++) {

                            double latitude = results.get(i).getGeometry().getLocation().getLat().floatValue();
                            double longitude = results.get(i).getGeometry().getLocation().getLng().floatValue();
                            
                            if (s.getConfirmada() == 1) {
                                Circle circle1 = new Circle(new LatLng(latitude,
                                    longitude), 30);
                                circle1.setStrokeColor("#d93c3c");
                                circle1.setFillColor("#d93c3c");
                                circle1.setFillOpacity(0.5);
                                emptyModel.addOverlay(circle1);
                            }
                            Marker marker = new Marker(new LatLng(latitude,
                                    longitude), "Data: " + s.getVisit().getData()
                                    + "\nAgente: " + s.getVisit().getAgente().getName()
                                    + "\nLocalizacão: " + s.getVisit().getStreet().getDescription() + ", "
                                    + s.getVisit().getNumero() + "-" + s.getVisit().getComplemento()
                                    + "\n" + txt,
                                    txt, icone);
                            emptyModel.addOverlay(marker);
                        }
                    }

                }

            }
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

}
