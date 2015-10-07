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

import java.security.spec.RSAOtherPrimeInfo;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Objects;

import sce.br.dao.Database;
import sce.br.model.City;
import sce.br.model.Employee;
import sce.br.model.Mensagem;
import sce.br.util.Synchronize;

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

    @Override
    public void onClick(View v) {
        if(v==btSynchronize){
            if(idIBGE.getText().toString().equals("")){
                Mensagem.exibeMessagem(ActSynchronize.this, "endemics", "Informe o Código IBGE!");
            } else {
                Synchronize.synchronize(idIBGE.getText().toString(), ActSynchronize.this);
            }
        }
    }

}
