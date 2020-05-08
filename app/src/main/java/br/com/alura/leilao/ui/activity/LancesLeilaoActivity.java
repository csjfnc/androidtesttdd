package br.com.alura.leilao.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.Serializable;

import br.com.alura.leilao.R;
import br.com.alura.leilao.model.Leilao;

public class LancesLeilaoActivity extends AppCompatActivity {

    private TextView lances_leilao_maior_lance;
    private TextView lances_leilao_menor_lance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lances_leilao);
        Intent dadosRecebidos = getIntent();
        mapeiaCOmponentesView();

        if(dadosRecebidos.hasExtra("leilao")){
            Leilao leilao = (Leilao) dadosRecebidos.getSerializableExtra("leilao");
            TextView descricao = findViewById(R.id.lances_leilao_descricao);
            descricao.setText(leilao.getDescricao());
            lances_leilao_maior_lance.setText(String.valueOf(leilao.getMaiorLance()));
            lances_leilao_menor_lance.setText(String.valueOf(leilao.getMenorLance()));
        }
    }

    public void mapeiaCOmponentesView(){
        lances_leilao_maior_lance = findViewById(R.id.lances_leilao_maior_lance);
        lances_leilao_menor_lance = findViewById(R.id.lances_leilao_menor_lance);


    }
}
