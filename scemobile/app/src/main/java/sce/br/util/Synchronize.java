package sce.br.util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import sce.br.dao.Database;
import sce.br.model.City;
import sce.br.scemobile.ActLogin;
import sce.br.scemobile.ActSynchronize;

/**
 * Created by Pedro Saraiva on 05/10/2015.
 */
public class Synchronize {

    private static final Handler handler=new Handler();

    private static ProgressDialog dialog;

    public static void synchronize(final String codibge, final Context act){
        dialog = ProgressDialog.show(act, "endemics", "Aguarde Sincronizando...", true);
        //sincroniza a cidade
        new Thread(new Runnable() {
            @Override
            public void run() {
                SoapObject soap = new SoapObject("http://ws.sce.br/", "listCity");
                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                        SoapEnvelope.VER11);
                soap.addProperty("arg0", codibge);
                envelope.setOutputSoapObject(soap);

                String url = "http://onenteamtecnologia.acessotemporario.net:80/sceweb/webservice/sceWS?wsdl";
                HttpTransportSE httpTransport = new HttpTransportSE(url);
                Object msg = null;
                try {
                    httpTransport.call("http://ws.sce.br/listCity", envelope);
                    msg = envelope.getResponse();

                } catch (Exception e) {
//                    dialog.dismiss();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(act, "Verifique sua conexão!", Toast.LENGTH_LONG).show();
                        }
                    });

                    System.out.println(e.toString());
                }

                if (msg == null) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            dialog.dismiss();
                            Toast.makeText(act, "Cidade não encontrada!", Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    Database db = new Database(act);
                    db.open();
                    db.execSql("delete from city");
                    db.execSql("delete from cycle");
                    db.execSql("delete from user");
                    db.execSql("delete from street");
                    SoapObject response = (SoapObject) envelope.bodyIn;
                    //preenche uma lista de cidades
                    final City city = new City();
                    for (int i = 0; i < response.getPropertyCount(); i++) {
                        SoapObject soapObject = (SoapObject) response.getProperty(i);

                        city.setActive(Boolean.parseBoolean(soapObject.getProperty("active").toString()));
                        city.setDescription(soapObject.getProperty("description").toString());
                        city.setId(Integer.parseInt(soapObject.getProperty("id").toString()));
                        city.setIdIBGE(Integer.parseInt(soapObject.getProperty("idIBGE").toString()));
                        city.setState(soapObject.getProperty("state").toString());
                        //salva a cidade
                        ContentValues cvCity = new ContentValues();
                        cvCity.put("_ID", city.getId());
                        cvCity.put("description", city.getDescription());
                        cvCity.put("state", city.getState());
                        cvCity.put("idibge", city.getIdIBGE());
                        db.insert("city", cvCity);
                    }
                    //fim do preenche cidade
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(act, "Sincronizando " + city.getDescription() + "-" +
                                    city.getState(), Toast.LENGTH_LONG).show();
                        }
                    });
                    if(city!=null) {
                        synchronizeEmployee(city, act);
                    } else {
                        dialog.dismiss();
                    }

                }
            }

        }).start();
    }

    public static void synchronizeEmployee(final City c, final Context act){
        new Thread(new Runnable() {
            @Override
            public void run() {
                SoapObject soap = new SoapObject("http://ws.sce.br/", "listEmployee");
                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                        SoapEnvelope.VER11);
                soap.addProperty("arg0", c.getId().toString());
                envelope.setOutputSoapObject(soap);

                String url = "http://onenteamtecnologia.acessotemporario.net:80/sceweb/webservice/sceWS?wsdl";
                HttpTransportSE httpTransport = new HttpTransportSE(url);
                Object msg = null;
                try {
                    httpTransport.call("http://ws.sce.br/listEmployee", envelope);
                    msg = envelope.getResponse();

                } catch (Exception e) {
                    System.out.println(e.toString());
                }
                SoapObject response = (SoapObject) envelope.bodyIn;
                if (response.getPropertyCount() == 0) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(act, "Não existe Funcionários!", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Database db = new Database(act);
                    db.open();
                    db.execSql("delete from user");
                    //salva funcionarios
                    for (int i = 0; i < response.getPropertyCount(); i++) {

                        SoapObject soapObject = (SoapObject) response.getProperty(i);

                        ContentValues cvEmployee = new ContentValues();
                        cvEmployee.put("CPF", soapObject.getProperty("cpf").toString());
                        cvEmployee.put("_ID", Integer.parseInt(soapObject.getProperty("id").toString()));
                        cvEmployee.put("imeimobile", soapObject.getProperty("imeiMobile").toString());
                        cvEmployee.put("name", soapObject.getProperty("name").toString());
                        cvEmployee.put("password", soapObject.getProperty("passwordMobile").toString());
                        cvEmployee.put("resgistration", soapObject.getProperty("registration").toString());
                        cvEmployee.put("type", soapObject.getProperty("type").toString());
                        db.insert("user", cvEmployee);

                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(act, "Funcionários Sincronizados!", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
                synchronizeStreet(c, act);

            }
        }).start();
    }

    public static void synchronizeStreet(final City c, final Context act){
        new Thread(new Runnable() {
            @Override
            public void run() {
                SoapObject soap = new SoapObject("http://ws.sce.br/", "listStreet");
                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                        SoapEnvelope.VER11);
                soap.addProperty("arg0", c.getId().toString());
                envelope.setOutputSoapObject(soap);

                String url = "http://onenteamtecnologia.acessotemporario.net:80/sceweb/webservice/sceWS?wsdl";
                HttpTransportSE httpTransport = new HttpTransportSE(url);
                Object msg = null;
                try {
                    httpTransport.call("http://ws.sce.br/listStreet", envelope);
                    msg = envelope.getResponse();

                } catch (Exception e) {
                    System.out.println(e.toString());
                }
                SoapObject response = (SoapObject) envelope.bodyIn;
                if (response.getPropertyCount() == 0) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(act, "Não existe Ruas!", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Database db = new Database(act);
                    db.open();
                    db.execSql("delete from street");
                    //salva funcionarios
                    for (int i = 0; i < response.getPropertyCount(); i++) {

                        SoapObject soapObject = (SoapObject) response.getProperty(i);
                        SoapObject soDistrict = (SoapObject) soapObject.getProperty("district");
                        SoapObject soArea = (SoapObject) soDistrict.getProperty("area");

                        Log.i("ruas", soDistrict.getProperty("description").toString());

                        ContentValues cvStreet = new ContentValues();
                        cvStreet.put("description", soapObject.getProperty("description").toString());
                        cvStreet.put("_ID", Integer.parseInt(soapObject.getProperty("id").toString()));
                        cvStreet.put("idquarter", soapObject.getProperty("idQuarter").toString());
                        cvStreet.put("district", soDistrict.getProperty("description").toString());
                        cvStreet.put("area", soArea.getProperty("description").toString());


                        db.insert("street", cvStreet);

                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(act, "Ruas Sincronizadas!", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
                synchronizeCycle(c, act);

            }
        }).start();

    }

    public static void synchronizeCycle(final City c, final Context act){
        new Thread(new Runnable() {
            @Override
            public void run() {
                SoapObject soap = new SoapObject("http://ws.sce.br/", "listCycle");
                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                        SoapEnvelope.VER11);
                soap.addProperty("arg0", c.getId().toString());
                envelope.setOutputSoapObject(soap);

                String url = "http://onenteamtecnologia.acessotemporario.net:80/sceweb/webservice/sceWS?wsdl";
                HttpTransportSE httpTransport = new HttpTransportSE(url);
                Object msg = null;
                try {
                    httpTransport.call("http://ws.sce.br/listCycle", envelope);
                    msg = envelope.getResponse();

                } catch (Exception e) {
                    System.out.println(e.toString());
                }
                SoapObject response = (SoapObject) envelope.bodyIn;
                if (response.getPropertyCount() == 0) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(act, "Não existe Ciclo!", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Database db = new Database(act);
                    db.open();
                    db.execSql("delete from cycle");
                    //salva cycle
                    for (int i = 0; i < response.getPropertyCount(); i++) {
                        Log.i("ruas", response.getProperty(i).toString());

                        SoapObject soapObject = (SoapObject) response.getProperty(i);
                        ContentValues cvCycle = new ContentValues();
                        cvCycle.put("description", soapObject.getProperty("description").toString());
                        cvCycle.put("_ID", Integer.parseInt(soapObject.getProperty("id").toString()));
                        cvCycle.put("dtstart", soapObject.getProperty("startDate").toString());
                        cvCycle.put("dtfim", soapObject.getProperty("endDate").toString());

                        db.insert("cycle", cvCycle);

                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(act, "Ciclo Sincronizado!", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
                dialog.dismiss();
                Intent i = new Intent(act,ActLogin.class);
                act.startActivity(i);

            }
        }).start();

    }

}
