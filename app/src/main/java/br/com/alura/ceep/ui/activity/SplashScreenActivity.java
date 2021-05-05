package br.com.alura.ceep.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import br.com.alura.ceep.R;
import br.com.alura.ceep.Utils.Preferencias;

public class SplashScreenActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        int duracao = 500;
        if(Preferencias.primeiroAcesso(this)){
            duracao = 2000;
        }


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Preferencias.salvaAcesso(getApplicationContext());
                enviaParaListaNotasActivity();
            }
        }, duracao);
    }

    private void enviaParaListaNotasActivity(){
        Intent intent = new Intent(SplashScreenActivity.this, ListaNotasActivity.class);
        startActivity(intent);
    }



    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}