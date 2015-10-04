package sce.br.scemobile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.TextView;

import sce.br.util.Ativo;

public class ActMainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnClose;
    private Button btnVisit;
    private TextView tvInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);
        btnClose = (Button) findViewById(R.id.MenuBtn_close);
        btnClose.setOnClickListener(this);
        btnVisit = (Button) findViewById(R.id.MenuBtn_home);
        btnVisit.setOnClickListener(this);
        tvInfo = (TextView) findViewById(R.id.tvInfo);
        tvInfo.setText(Ativo.getUser().getName() +" | "+
                Ativo.getCity().getDescription()+"-"+Ativo.getCity().getState());

    }


    @Override
    public void onClick(View v) {
        if(v==btnClose){
            Intent i = new Intent(ActMainActivity.this, ActLogin.class);
            startActivity(i);
            finish();
        } else if(v==btnVisit){
            Intent i = new Intent(ActMainActivity.this, ActVisit.class);
            startActivity(i);
        }
    }
}
