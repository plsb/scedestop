package sce.br.scemobile;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;

import sce.br.dao.Database;
import sce.br.util.Ativo;
import sce.br.util.Synchronize;

public class ActMainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnClose;
    private Button btnImoFechados;
    private Button btnStreet;
    private Button btnSynchronize;
    private TextView tvInfo;
    private static TextView tvAImportar;
    private TextView TvInfoCycle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);
        btnClose = (Button) findViewById(R.id.MenuBtn_close);
        btnClose.setOnClickListener(this);
        btnImoFechados = (Button) findViewById(R.id.MenuBtn_home);
        btnImoFechados.setOnClickListener(this);
        btnStreet = (Button) findViewById(R.id.MenuBtn_ruas);
        btnStreet.setOnClickListener(this);
        btnSynchronize = (Button) findViewById(R.id.MenuBtn_sincronizar);
        btnSynchronize.setOnClickListener(this);
        TvInfoCycle = (TextView) findViewById(R.id.tvInfoCycle);
        tvAImportar = (TextView) findViewById(R.id.tvInfoAImportar);
        //faz contagem de quantos faltam a importar
        mostraContagemAImportar(ActMainActivity.this);

        tvInfo = (TextView) findViewById(R.id.tvInfo);
        tvInfo.setText(Ativo.getUser().getName() +" | "+
                Ativo.getCity().getDescription()+"-"+Ativo.getCity().getState());
        if(Ativo.getCycle()!=null) {
            TvInfoCycle.setText(Ativo.getCycle().getDescription());
        }

    }

    public static void mostraContagemAImportar(Context cts){
        Database db = new Database(cts);
        Cursor c = db.consult("visit",new String[] { "*" },
                " FOIIMPORTADO = ? ",
                new String[] { String.valueOf(0) }, null, null,
                null, null);
        int i = c.getCount();
        if(i==0){
            tvAImportar.setText("");
        } else {
            tvAImportar.setText("Não Sincronizados: " + i);
        }
    }

    @Override
    public void onClick(View v) {
        if(v==btnClose){
            Intent i = new Intent(ActMainActivity.this, ActLogin.class);
            finish();
            startActivity(i);

        } else if(v==btnImoFechados){
            Intent i = new Intent(ActMainActivity.this, ActImoFechados.class);
            startActivity(i);
        } else if(v==btnStreet){
            if(Ativo.getCycle()!=null){
                Intent i = new Intent(ActMainActivity.this, ActStreet.class);
                startActivity(i);

            } else {
                Toast.makeText(ActMainActivity.this,"Não Existe Ciclo!",Toast.LENGTH_LONG).show();
            }

        } else if(v==btnSynchronize){
            Integer ibge = Ativo.getCity().getIdIBGE();
            Synchronize.synchronize(String.valueOf(ibge),ActMainActivity.this);
        }
    }
}
