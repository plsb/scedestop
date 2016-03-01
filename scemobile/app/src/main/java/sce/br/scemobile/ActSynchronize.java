package sce.br.scemobile;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
