package br.com.alura.ceep.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.alura.ceep.R;
import br.com.alura.ceep.model.Nota;
import br.com.alura.ceep.ui.recyclerview.adapter.ListaCoresAdapter;
import br.com.alura.ceep.ui.recyclerview.adapter.listener.OnColorClickListener;

import static br.com.alura.ceep.ui.activity.NotaActivityConstantes.CHAVE_NOTA;
import static br.com.alura.ceep.ui.activity.NotaActivityConstantes.CHAVE_POSICAO;
import static br.com.alura.ceep.ui.activity.NotaActivityConstantes.POSICAO_INVALIDA;

public class FormularioNotaActivity extends AppCompatActivity {


    public static final String TITULO_APPBAR_INSERE = "Insere nota";
    public static final String TITULO_APPBAR_ALTERA = "Altera nota";
    private int posicaoRecibida = POSICAO_INVALIDA;
    private TextView titulo;
    private TextView descricao;
    private ConstraintLayout layoutNotaFormulario;
    private RecyclerView rvCores;
    private ListaCoresAdapter listaCoresAdapter;
    private String corDeFundoSelecionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_nota);

        setTitle(TITULO_APPBAR_INSERE);
        inicializaCampos();

        Intent dadosRecebidos = getIntent();
        if(dadosRecebidos.hasExtra(CHAVE_NOTA)){
            setTitle(TITULO_APPBAR_ALTERA);
            Nota notaRecebida = (Nota) dadosRecebidos
                    .getSerializableExtra(CHAVE_NOTA);
            posicaoRecibida = dadosRecebidos.getIntExtra(CHAVE_POSICAO, POSICAO_INVALIDA);
            preencheCampos(notaRecebida);
        }
    }

    @SuppressLint("ResourceType")
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        if(savedInstanceState != null){
            corDeFundoSelecionada = savedInstanceState.getString("bgColor");
            try{
                layoutNotaFormulario.setBackgroundColor(Color.parseColor(corDeFundoSelecionada));
            }catch (Exception e ){
                e.printStackTrace();
                layoutNotaFormulario.setBackgroundColor(Color.parseColor(getResources().getString(R.color.brancoLista)));
                Toast.makeText(getApplicationContext(), "Não foi posssível recuperar a cor definida", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("bgColor", corDeFundoSelecionada);
        super.onSaveInstanceState(outState);
    }

    private void preencheCampos(Nota notaRecebida) {
        titulo.setText(notaRecebida.getTitulo());
        descricao.setText(notaRecebida.getDescricao());
        corDeFundoSelecionada = notaRecebida.getCor();
        layoutNotaFormulario.setBackgroundColor(Color.parseColor(corDeFundoSelecionada));
    }

    @SuppressLint("ResourceType")
    private void inicializaCampos() {
        titulo = findViewById(R.id.formulario_nota_titulo);
        descricao = findViewById(R.id.formulario_nota_descricao);
        layoutNotaFormulario = findViewById(R.id.layout_nota_formulario);
        corDeFundoSelecionada =getResources().getString(R.color.brancoLista);
        List<String> listaCores = pegaTodasCores();
        configuraRvCores(listaCores);
    }

    @SuppressLint("ResourceType")
    private List<String> pegaTodasCores() {
        List<String> cores = new ArrayList<>();

        cores.add(getResources().getString(R.color.brancoLista));
        cores.add(getResources().getString(R.color.azulLista));
        cores.add(getResources().getString(R.color.vermelhoLista));
        cores.add(getResources().getString(R.color.verdeLista));
        cores.add(getResources().getString(R.color.amareloLista));
        cores.add(getResources().getString(R.color.lilasLista));
        cores.add(getResources().getString(R.color.cinzaLista));
        cores.add(getResources().getString(R.color.marromLista));
        cores.add(getResources().getString(R.color.roxoLista));
        return cores;
    }

    private void configuraRvCores(List<String> listaCores){
        rvCores = findViewById(R.id.lista_cores);
        configuraAdapter(rvCores, listaCores);
    }

    private void configuraAdapter(RecyclerView rvCores, List<String> listaCores) {
        listaCoresAdapter = new ListaCoresAdapter(listaCores, this);
        rvCores.setAdapter(listaCoresAdapter);
        listaCoresAdapter.setOnColorClickListener(new OnColorClickListener() {
            @Override
            public void onItemClick(String corEscolhida) {
                try {
                    layoutNotaFormulario.setBackgroundColor(Color.parseColor(corEscolhida));
                    corDeFundoSelecionada = corEscolhida;
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Houve um erro ao selecionar a cor", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_formulario_nota_salva, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(ehMenuSalvaNota(item)){
            Nota notaCriada = criaNota();
            retornaNota(notaCriada);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void retornaNota(Nota nota) {
        Intent resultadoInsercao = new Intent();
        resultadoInsercao.putExtra(CHAVE_NOTA, nota);
        resultadoInsercao.putExtra(CHAVE_POSICAO, posicaoRecibida);
        setResult(Activity.RESULT_OK,resultadoInsercao);
    }

    @NonNull
    private Nota criaNota() {
        return new Nota(titulo.getText().toString(),
                descricao.getText().toString(), corDeFundoSelecionada);
    }

    private boolean ehMenuSalvaNota(MenuItem item) {
        return item.getItemId() == R.id.menu_formulario_nota_ic_salva;
    }
}
