package sce.br.scemobile;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.concurrent.ExecutionException;

import sce.br.dao.Database;
import sce.br.model.City;
import sce.br.model.Employee;
import sce.br.model.Mensagem;
import sce.br.util.Ativo;
import sce.br.util.Util;

/**
 * Created by Pedro Saraiva on 23/08/2015.
 */
public class ActLogin extends Activity implements View.OnClickListener{

    private EditText etLogin, etPassword;
    private Button btSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_login);
        etLogin = (EditText) findViewById(R.id.LoginEtUserName);
        etPassword = (EditText) findViewById(R.id.LoginEtPass);
        btSignIn = (Button) findViewById(R.id.LoginBtnSingIn);
        btSignIn.setOnClickListener(this);
        verificarDadosSincronizados();
    }

    public void verificarDadosSincronizados(){
        Database db = new Database(ActLogin.this);
        db.open();
        try{
            Cursor cursor = db.consult("user",
                    new String[] { "*" },
                    null, null, null, null,
                    null, null);
            if(cursor.getCount()==0){
                Intent i = new Intent(ActLogin.this,ActSynchronize.class);
                finish();
                startActivity(i);
            }
        } catch (Exception ex){

        }

    }

    @Override
    public void onClick(View v) {
        if(v==btSignIn){
            if(autentica(etLogin.getText().toString(),
                    etPassword.getText().toString())){
                Intent intent = new Intent(ActLogin.this,
                        ActMainActivity.class);//AdministradorOpcoes.class);
                finish();
                startActivity(intent);
            } else {
                Toast.makeText(this,"Incorreto. Tente novamente!",Toast.LENGTH_SHORT).show();
            }

        }
    }

    public boolean autentica(String login, String password){
        password = Util.md5(password);
        Database db = new Database(ActLogin.this);
        db.open();
        Cursor cursor = db.consult("user",new String[] { "*" },
                " resgistration = ? AND password = ?",
                new String[] { login, password }, null, null,
                null, null);
        if (cursor.getCount() != 0) {
            //preenche user
            cursor.moveToFirst();
            Employee employee = new Employee();
            employee.setCpf(cursor.getString(cursor.getColumnIndex("CPF")).toString());
            employee.setName(cursor.getString(cursor.getColumnIndex("name")).toString());
            employee.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex("_ID")).toString()));
            Ativo.setUser(employee);
            //preenche city
            Cursor cursorCity = db.consult("city",new String[] { "*" },
                    null, null, null, null,
                    null, null);
            if(cursorCity.getCount()!=0){
                cursorCity.moveToFirst();
                City city = new City();
                city.setDescription(cursorCity.getString(cursorCity.getColumnIndex("description")).toString());
                city.setState(cursorCity.getString(cursorCity.getColumnIndex("state")).toString());
                city.setIdIBGE(Integer.parseInt(cursorCity.getString(cursorCity.getColumnIndex("idibge")).toString()));
                city.setId(Integer.parseInt(cursorCity.getString(cursorCity.getColumnIndex("_ID")).toString()));
                Ativo.setCity(city);
            }

            return true;
        } else {
            Toast.makeText(ActLogin.this,"Dados Incorretos!",Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}
