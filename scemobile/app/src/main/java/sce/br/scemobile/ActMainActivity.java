package sce.br.scemobile;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;

import sce.br.util.Ativo;
import sce.br.util.Synchronize;

public class ActMainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnClose;
    private Button btnVisit;
    private Button btnStreet;
    private Button btnSynchronize;
    private TextView tvInfo;
    private TextView TvInfoCycle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);
        btnClose = (Button) findViewById(R.id.MenuBtn_close);
        btnClose.setOnClickListener(this);
        btnVisit = (Button) findViewById(R.id.MenuBtn_home);
        btnVisit.setOnClickListener(this);
        btnStreet = (Button) findViewById(R.id.MenuBtn_ruas);
        btnStreet.setOnClickListener(this);
        btnSynchronize = (Button) findViewById(R.id.MenuBtn_sincronizar);
        btnSynchronize.setOnClickListener(this);
        TvInfoCycle = (TextView) findViewById(R.id.tvInfoCycle);

        tvInfo = (TextView) findViewById(R.id.tvInfo);
        tvInfo.setText(Ativo.getUser().getName() +" | "+
                Ativo.getCity().getDescription()+"-"+Ativo.getCity().getState());
        TvInfoCycle.setText(Ativo.getCycle().getDescription());

    }


    @Override
    public void onClick(View v) {
        if(v==btnClose){
            Intent i = new Intent(ActMainActivity.this, ActLogin.class);
            startActivity(i);
            finish();
        } else if(v==btnVisit){

        } else if(v==btnStreet){
            if(Ativo.getCycle()!=null){
                Intent i = new Intent(ActMainActivity.this, ActStreet.class);
                startActivity(i);
                finish();
            } else {
                Toast.makeText(ActMainActivity.this,"Não Existe Ciclo!",Toast.LENGTH_LONG).show();
            }

        } else if(v==btnSynchronize){
            Integer ibge = Ativo.getCity().getIdIBGE();
            Synchronize.synchronize(String.valueOf(ibge),ActMainActivity.this);
        }
    }
}
