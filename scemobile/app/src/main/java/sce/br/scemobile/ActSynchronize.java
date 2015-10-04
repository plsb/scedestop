package sce.br.scemobile;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

import sce.br.dao.Database;
import sce.br.model.City;
import sce.br.model.Employee;
import sce.br.model.Mensagem;

public class ActSynchronize extends Activity implements View.OnClickListener{
    private EditText idIBGE;
    private Button btSynchronize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_synchronize);

        idIBGE = (EditText) findViewById(R.id.idCity);
        btSynchronize = (Button) findViewById(R.id.btSynchronize);

        btSynchronize.setOnClickListener(this);
    }

    private ProgressDialog dialog;
    @Override
    public void onClick(View v) {
        if(v==btSynchronize){
            if(idIBGE.getText().toString().equals("")){
                Mensagem.exibeMessagem(ActSynchronize.this, "endemics", "Informe o Código IBGE!");
            } else {
                dialog = ProgressDialog.show(ActSynchronize.this, "endemics", "Aguarde Sincronizando...", true);

                sincronizar();
            }
        }
    }

    final Handler handler=new Handler();

    public void sincronizar(){

        //sincroniza a cidade
        new Thread(new Runnable() {
            @Override
            public void run() {
                SoapObject soap = new SoapObject("http://ws.sce.br/", "listCity");
                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                        SoapEnvelope.VER11);
                soap.addProperty("arg0", idIBGE.getText().toString());
                envelope.setOutputSoapObject(soap);

                String url = "http://onenteamtecnologia.acessotemporario.net:80/sceweb/webservice/sceWS?wsdl";
                HttpTransportSE httpTransport = new HttpTransportSE(url);
                Object msg = null;
                try {
                    httpTransport.call("http://ws.sce.br/listCity", envelope);
                    msg = envelope.getResponse();

                } catch (Exception e) {
                    dialog.dismiss();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ActSynchronize.this, "Verifique sua conexão!", Toast.LENGTH_LONG).show();
                        }
                    });

                    System.out.println(e.toString());
                }

                if (msg == null) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            dialog.dismiss();
                            Toast.makeText(ActSynchronize.this, "Cidade não encontrada!", Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    Database db = new Database(ActSynchronize.this);
                    db.open();
                    db.execSql("delete from city");
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
                            Toast.makeText(ActSynchronize.this, "Sincronizando " + city.getDescription() + "-" +
                                    city.getState(), Toast.LENGTH_LONG).show();
                        }
                    });

                    sincronizarFuncionario(city);

                }
            }

        }).start();
    }

    public void sincronizarFuncionario(final City c){
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
                            Toast.makeText(ActSynchronize.this, "Não existe Funcionários!", Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    Database db = new Database(ActSynchronize.this);
                    db.open();
                    db.execSql("delete from user");
                    //salva funcionarios
                    for (int i = 0; i < response.getPropertyCount(); i++) {

                        SoapObject soapObject = (SoapObject) response.getProperty(i);
                        Log.i("pedrosaraiva", response.getProperty(i).toString());

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
                            dialog.dismiss();
                            Toast.makeText(ActSynchronize.this, "Funcionários Sincronizados!", Toast.LENGTH_LONG).show();
                        }
                    });

                }

                dialog.dismiss();
                Intent i = new Intent(ActSynchronize.this,ActLogin.class);
                finish();
                startActivity(i);

            }
        }).start();
    }


}
