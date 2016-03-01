package sce.br.util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import sce.br.dao.Database;
import sce.br.model.City;
import sce.br.model.Cycle;
import sce.br.scemobile.ActLogin;
import sce.br.scemobile.ActMainActivity;
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
                soap.addProperty("codVerificacao", "@endemics04_p_09M08k");
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
                soap.addProperty("codVerificacao", "@endemics04_p_09M08k");
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
                soap.addProperty("codVerificacao", "@endemics04_p_09M08k");
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
                soap.addProperty("codVerificacao", "@endemics04_p_09M08k");
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
                synchronizeVisit(c, act);
            }
        }).start();

    }

    public static void synchronizeVisit(final City c, final Context act){
        new Thread(new Runnable() {
            @Override
            public void run() {
                if(Ativo.getCycle()!=null) {
                    Database db = new Database(act);
                    db.open();
                    String idciclo = String.valueOf(Ativo.getCycle().getId());
                    Cursor cursorVisit = db.consult("visit", new String[]{"*"},
                            "CICLO = ?", new String[]{idciclo}, null, null,
                            null, null);
                    if (cursorVisit.getCount() != 0) {
                        cursorVisit.moveToFirst();

                        SoapObject soap = new SoapObject("http://ws.sce.br/", "insertVisit");
                        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                                SoapEnvelope.VER11);
                        while (cursorVisit.isAfterLast() == false) {
                            String tipoVisita = cursorVisit.getString(cursorVisit.getColumnIndex("VIS_TIPO_VISITA")).toString();
                            final String idVisita = cursorVisit.getString(cursorVisit.getColumnIndex("_ID")).toString();
                            int foiimportada = cursorVisit.getInt(cursorVisit.getColumnIndex("FOIIMPORTADO"));
                            String dataRecuperada = cursorVisit.getString(cursorVisit.getColumnIndex("VIS_DATA_RECUPERADA")).toString();
                            if(foiimportada!=1 || (foiimportada==1 && !dataRecuperada.equals(""))) {

                                soap.addProperty("id", cursorVisit.getString(cursorVisit.getColumnIndex("_ID")).toString());
                                soap.addProperty("idrua", cursorVisit.getString(cursorVisit.getColumnIndex("VIS_IDRUA")).toString());
                                soap.addProperty("numeroResidencia", cursorVisit.getString(cursorVisit.getColumnIndex("VIS_NUMERO")).toString());
                                soap.addProperty("idciclo", cursorVisit.getString(cursorVisit.getColumnIndex("CICLO")).toString());
                                soap.addProperty("complemento", cursorVisit.getString(cursorVisit.getColumnIndex("VIS_COMPLEMENTO")).toString());
                                soap.addProperty("tipoImovel", cursorVisit.getString(cursorVisit.getColumnIndex("VIS_TIPO_IMOVEL")).toString());
                                soap.addProperty("tipoVisita", tipoVisita);
                                soap.addProperty("codDoenca", cursorVisit.getString(cursorVisit.getColumnIndex("VIS_COD_DOENCA")).toString());
                                soap.addProperty("hora", cursorVisit.getString(cursorVisit.getColumnIndex("VIS_HORA")).toString());
                                soap.addProperty("pneu", cursorVisit.getString(cursorVisit.getColumnIndex("VIS_PNEU")).toString());
                                soap.addProperty("tanque", cursorVisit.getString(cursorVisit.getColumnIndex("VIS_TANQUE")).toString());
                                soap.addProperty("tambor", cursorVisit.getString(cursorVisit.getColumnIndex("VIS_TAMBOR")).toString());
                                soap.addProperty("barril", cursorVisit.getString(cursorVisit.getColumnIndex("VIS_BARRIL")).toString());
                                soap.addProperty("tina", cursorVisit.getString(cursorVisit.getColumnIndex("VIS_TINA")).toString());
                                soap.addProperty("pote", cursorVisit.getString(cursorVisit.getColumnIndex("VIS_POTE")).toString());
                                soap.addProperty("filtro", cursorVisit.getString(cursorVisit.getColumnIndex("VIS_FILTRO")).toString());
                                soap.addProperty("quartinha", cursorVisit.getString(cursorVisit.getColumnIndex("VIS_QUARTINHA")).toString());
                                soap.addProperty("vaso", cursorVisit.getString(cursorVisit.getColumnIndex("VIS_VASO")).toString());
                                soap.addProperty("matConstrucao", cursorVisit.getString(cursorVisit.getColumnIndex("VIS_MAT_CONSTRUCAO")).toString());
                                soap.addProperty("pecaCarro", cursorVisit.getString(cursorVisit.getColumnIndex("VIS_PECA_CARRO")).toString());
                                soap.addProperty("garrafa", cursorVisit.getString(cursorVisit.getColumnIndex("VIS_GARRAFA")).toString());
                                soap.addProperty("lata", cursorVisit.getString(cursorVisit.getColumnIndex("VIS_LATA")).toString());
                                soap.addProperty("depPlastico", cursorVisit.getString(cursorVisit.getColumnIndex("VIS_DEP_PLASTICO")).toString());
                                soap.addProperty("poco", cursorVisit.getString(cursorVisit.getColumnIndex("VIS_POCO")).toString());
                                soap.addProperty("cisterna", cursorVisit.getString(cursorVisit.getColumnIndex("VIS_CISTERNA")).toString());
                                soap.addProperty("cacimba", cursorVisit.getString(cursorVisit.getColumnIndex("VIS_CACIMBA")).toString());
                                soap.addProperty("cxDagua", cursorVisit.getString(cursorVisit.getColumnIndex("VIS_CX_DAGUA")).toString());
                                soap.addProperty("recNatural", cursorVisit.getString(cursorVisit.getColumnIndex("VIS_REC_NATURAL")).toString());
                                soap.addProperty("outros", cursorVisit.getString(cursorVisit.getColumnIndex("VIS_OUTROS")).toString());
                                soap.addProperty("armadilha", cursorVisit.getString(cursorVisit.getColumnIndex("VIS_ARMADILHA")).toString());
                                soap.addProperty("pool", cursorVisit.getString(cursorVisit.getColumnIndex("VIS_POOL")).toString());
                                soap.addProperty("ralo", cursorVisit.getString(cursorVisit.getColumnIndex("VIS_RALO")).toString());
                                soap.addProperty("piscina", cursorVisit.getString(cursorVisit.getColumnIndex("VIS_PISCINA")).toString());
                                soap.addProperty("depTratadoFocal", cursorVisit.getString(cursorVisit.getColumnIndex("VIS_DEP_TRATADOS_FOCAL")).toString());
                                soap.addProperty("detpTratadoPerifocal", cursorVisit.getString(cursorVisit.getColumnIndex("VIS_DEP_TRATADOS_PERIFOCAL")).toString());
                                soap.addProperty("depEliminados", cursorVisit.getString(cursorVisit.getColumnIndex("VIS_DEP_ELIMINADOS")).toString());
                                soap.addProperty("tipoAtividade", cursorVisit.getString(cursorVisit.getColumnIndex("VIS_TIPO_ATIVIDADE")).toString());
                                if(!(foiimportada==1 && !dataRecuperada.equals(""))) {
                                    soap.addProperty("data", cursorVisit.getString(cursorVisit.getColumnIndex("VIS_DATA")).toString());
                                }
                                soap.addProperty("dataRecuperada", dataRecuperada);
                                soap.addProperty("responsavel", cursorVisit.getString(cursorVisit.getColumnIndex("VIS_RESPONSAVEL")).toString());
                                soap.addProperty("tipoLarvicida", cursorVisit.getString(cursorVisit.getColumnIndex("VIS_TIPO_LARVICIDA")).toString());
                                soap.addProperty("obs", cursorVisit.getString(cursorVisit.getColumnIndex("VIS_OBS")).toString());
                                soap.addProperty("larvicidaGt", cursorVisit.getString(cursorVisit.getColumnIndex("VIS_LARVICIDAGT")).toString());
                                soap.addProperty("larvicidaMl", cursorVisit.getString(cursorVisit.getColumnIndex("VIS_LARVICIDAML")).toString());
                                soap.addProperty("idcidade", String.valueOf(Ativo.getCity().getId()));
                                soap.addProperty("idagente", cursorVisit.getString(cursorVisit.getColumnIndex("VIS_AGENTE")).toString());
                                soap.addProperty("latitude", cursorVisit.getString(cursorVisit.getColumnIndex("VIS_LATITUDE")).toString());
                                soap.addProperty("longitude", cursorVisit.getString(cursorVisit.getColumnIndex("VIS_LONGITUDE")).toString());
                                soap.addProperty("codVerificacao", "@endemics04_p_09M08k");
                                envelope.setOutputSoapObject(soap);

                                String url = "http://onenteamtecnologia.acessotemporario.net:80/sceweb/webservice/sceWS?wsdl";
                                HttpTransportSE httpTransport = new HttpTransportSE(url);
                                Object msg = null;
                                try {
                                    httpTransport.call("http://ws.sce.br/insertVisit", envelope);
                                    msg = envelope.getResponse();

                                } catch (Exception e) {
                                    Log.e("erropedro",e.getMessage());
                                    System.out.println(e.toString());
                                }

                                SoapObject response = (SoapObject) envelope.bodyIn;
                                if (response.getPropertyCount() == 0) {
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(act, "Sem conexão!", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                } else {
                                    //salva cycle

                                    if (response.getProperty(0).toString().equals("true")) {
                                        if (!tipoVisita.equals("FECHADA")) {
                                            db.execSql("delete from visit where _ID='" + idVisita + "'");
                                        } else if (tipoVisita.equals("FECHADA") && !dataRecuperada.equals("")) {
                                            db.execSql("delete from visit where _ID='" + idVisita + "'");
                                        } else if(tipoVisita.equals("FECHADA") && foiimportada==0){
                                            ContentValues cv = new ContentValues();
                                            cv.put("FOIIMPORTADO",1);
                                            db.update("visit",cv,"_ID="+idVisita);
                                        }
                                        handler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(act, "Visita " + idVisita + " Importada!", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    } else {
                                        handler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(act, "Erro ao importar visita " + idVisita + "!", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }


                                }
                            }
                            cursorVisit.moveToNext();

                        }

                    }
                }
                synchronizeSample(c, act);
            }
        }).start();

    }

    public static void synchronizeSample(final City c, final Context act){
        new Thread(new Runnable() {
            @Override
            public void run() {
                if(Ativo.getCycle()!=null) {
                    Database db = new Database(act);
                    db.open();
                    String idciclo = String.valueOf(Ativo.getCycle().getId());
                    Cursor cursorSample = db.consult("amostra", new String[]{"*"},
                            "ID_CICLO = ?", new String[]{idciclo}, null, null,
                            null, null);
                    if (cursorSample.getCount() != 0) {
                        cursorSample.moveToFirst();

                        SoapObject soap = new SoapObject("http://ws.sce.br/", "insertSample");
                        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                                SoapEnvelope.VER11);
                        while (cursorSample.isAfterLast() == false) {
                            final String id = cursorSample.getString(cursorSample.getColumnIndex("_ID")).toString();
                            soap.addProperty("idvisita", cursorSample.getString(cursorSample.getColumnIndex("ID_VISIT")).toString());
                            soap.addProperty("deposito", cursorSample.getString(cursorSample.getColumnIndex("DEPOSITO")).toString());
                            soap.addProperty("numAmostras", cursorSample.getString(cursorSample.getColumnIndex("NUM_AMOSTRA")).toString());
                            soap.addProperty("city", Ativo.getCity().getId());
                            soap.addProperty("cycle", cursorSample.getString(cursorSample.getColumnIndex("ID_CICLO")).toString());
                            soap.addProperty("numLavas", cursorSample.getString(cursorSample.getColumnIndex("NUM_LAVAS")).toString());
                            soap.addProperty("id", cursorSample.getString(cursorSample.getColumnIndex("_ID")).toString());
                            soap.addProperty("codVerificacao", "@endemics04_p_09M08k");
                            envelope.setOutputSoapObject(soap);

                                String url = "http://onenteamtecnologia.acessotemporario.net:80/sceweb/webservice/sceWS?wsdl";
                                HttpTransportSE httpTransport = new HttpTransportSE(url);
                                Object msg = null;
                                try {
                                    httpTransport.call("http://ws.sce.br/insertSample", envelope);
                                    msg = envelope.getResponse();

                                } catch (Exception e) {
                                    Log.e("erropedro",e.getMessage());
                                    System.out.println(e.toString());
                                }

                                SoapObject response = (SoapObject) envelope.bodyIn;
                                if (response.getPropertyCount() == 0) {
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(act, "Sem conexão!", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                } else {
                                    //salva cycle

                                    if (response.getProperty(0).toString().equals("true")) {
                                            db.execSql("delete from amostra where _ID='" + id + "'");
                                        handler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(act, "Amostra " + id + " Importada!", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    } else {
                                        handler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(act, "Erro ao importar amostra " + id + "!", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }


                                }
                            cursorSample.moveToNext();

                        }

                    }
                }
                synchronizeImoFechados(act);
            }
        }).start();

    }

    public static void synchronizeImoFechados(final Context act){
        new Thread(new Runnable() {
            @Override
            public void run() {
                if(Ativo.getCycle()!=null){

                    SoapObject soap = new SoapObject("http://ws.sce.br/", "listImoFechados");
                    SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                            SoapEnvelope.VER11);
                    soap.addProperty("arg0", Ativo.getCycle().getId());
                    soap.addProperty("codVerificacao", "@endemics04_p_09M08k");
                    envelope.setOutputSoapObject(soap);

                    Database db = new Database(act);
                    db.open();
                    db.execSql("delete from visit");

                    String url = "http://onenteamtecnologia.acessotemporario.net:80/sceweb/webservice/sceWS?wsdl";
                    HttpTransportSE httpTransport = new HttpTransportSE(url);
                    Object msg = null;
                    try {
                        httpTransport.call("http://ws.sce.br/listImoFechados", envelope);
                        msg = envelope.getResponse();

                    } catch (Exception e) {
                        System.out.println(e.toString());
                    }
                    SoapObject response = (SoapObject) envelope.bodyIn;

                    if (response.getPropertyCount() == 0) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(act, "Não existe Imóveis Fechados!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {

                        //salva funcionarios
                        for (int i = 0; i < response.getPropertyCount(); i++) {

                            SoapObject soapObject = (SoapObject) response.getProperty(i);
                            SoapObject soStreet = (SoapObject) soapObject.getProperty("street");
                            SoapObject soCycle = (SoapObject) soapObject.getProperty("ciclo");
                            SoapObject soAgente = (SoapObject) soapObject.getProperty("agente");

                            ContentValues cvImoFec = new ContentValues();
                            cvImoFec.put("_ID", soapObject.getProperty("id").toString());
                            cvImoFec.put("VIS_IDRUA", soStreet.getProperty("id").toString());
                            cvImoFec.put("VIS_NOMERUA", soStreet.getProperty("description").toString());
                            cvImoFec.put("VIS_NUMERO", Integer.parseInt(soapObject.getProperty("numero").toString()));
                            cvImoFec.put("CICLO", soCycle.getProperty("id").toString());
                            if (soapObject.getProperty("complemento").toString().equals("anyType{}")){
                                cvImoFec.put("VIS_COMPLEMENTO","");
                            } else {
                                cvImoFec.put("VIS_COMPLEMENTO", soapObject.getProperty("complemento").toString());
                            }
                            if(soapObject.getProperty("responsavel").toString().toString().equals("anyType{}")){
                                cvImoFec.put("VIS_RESPONSAVEL", "");
                            } else {
                                cvImoFec.put("VIS_RESPONSAVEL", soapObject.getProperty("responsavel").toString());
                            }
                            cvImoFec.put("VIS_TIPO_IMOVEL", soapObject.getProperty("tipoImovel").toString());
                            cvImoFec.put("VIS_TIPO_VISITA", soapObject.getProperty("tipoVisita").toString());
                            cvImoFec.put("VIS_COD_DOENCA", soapObject.getProperty("codDoenca").toString());
                            cvImoFec.put("VIS_HORA", soapObject.getProperty("hora").toString());
                            cvImoFec.put("VIS_TIPO_ATIVIDADE", soapObject.getProperty("tipoAtividade").toString());
                            String dataCom = soapObject.getProperty("data").toString().substring(0,10);
                            String data = dataCom.substring(8,10)+"/"+dataCom.substring(5,7)+"/"+dataCom.substring(0,4);

                            cvImoFec.put("VIS_DATA", data);
                            cvImoFec.put("VIS_DATA_RECUPERADA", "");
                            cvImoFec.put("VIS_AGENTE", soAgente.getProperty("id").toString());
                            cvImoFec.put("FOIIMPORTADO", 1);


                            db.insert("visit", cvImoFec);

                        }
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(act, "Imóveis Fechados Sincronizados!", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                }
                //ActMainActivity.mostraContagemAImportar(act);
                dialog.dismiss();
                Intent i = new Intent(act, ActLogin.class);
                act.startActivity(i);

            }
        }).start();
    }

}
