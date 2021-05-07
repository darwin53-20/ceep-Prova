package br.com.alura.ceep.ui.activity;

import android.annotation.SuppressLint;
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
import br.com.alura.ceep.asynctask.SalvaNotaTask;
import br.com.alura.ceep.dao.NotaDAO;
import br.com.alura.ceep.dataBase.NotasDatabase;
import br.com.alura.ceep.model.Nota;
import br.com.alura.ceep.ui.recyclerview.adapter.ListaCoresAdapter;

import static br.com.alura.ceep.ui.activity.NotaActivityConstantes.CHAVE_NOTA;

public class FormularioNotaActivity extends AppCompatActivity {


    public static final String TITULO_APPBAR_INSERE = "Insere nota";
    public static final String TITULO_APPBAR_ALTERA = "Altera nota";

    public static final String COR_DE_FUNDO = "bgColor";
    public static final String ERRO_CONSULTA_COR = "Não foi posssível recuperar a cor definida";
    private TextView titulo;
    private TextView descricao;
    private ConstraintLayout layoutNotaFormulario;
    private String corDeFundoSelecionada;
    private NotaDAO notaDao;

    private Nota notaRecebida;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_nota);

        setTitle(TITULO_APPBAR_INSERE);
        inicializaCampos();


        Intent dadosRecebidos = getIntent();
        if(dadosRecebidos.hasExtra(CHAVE_NOTA)){
            setTitle(TITULO_APPBAR_ALTERA);
            notaRecebida = (Nota) dadosRecebidos.getSerializableExtra(CHAVE_NOTA);
            preencheCampos(notaRecebida);
        }else{
            notaRecebida = null;
        }
    }

    @SuppressLint("ResourceType")
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        if(savedInstanceState != null){
            corDeFundoSelecionada = savedInstanceState.getString(COR_DE_FUNDO);
            try{
                layoutNotaFormulario.setBackgroundColor(Color.parseColor(corDeFundoSelecionada));
            }catch (Exception e ){
                e.printStackTrace();
                layoutNotaFormulario.setBackgroundColor(Color.parseColor(getResources().getString(R.color.brancoLista)));
                Toast.makeText(getApplicationContext(), ERRO_CONSULTA_COR, Toast.LENGTH_SHORT).show();
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
        corDeFundoSelecionada = getResources().getString(R.color.brancoLista);
        NotasDatabase database = NotasDatabase.getInstance(this);
        notaDao = database.getNotaDAO();
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

    private void configuraRvCores(List<String> listaCores) {
        RecyclerView rvCores = findViewById(R.id.lista_cores);
        configuraAdapter(rvCores, listaCores);
    }

    private void configuraAdapter(RecyclerView rvCores, List<String> listaCores) {
        ListaCoresAdapter listaCoresAdapter = new ListaCoresAdapter(listaCores, this);
        rvCores.setAdapter(listaCoresAdapter);
        listaCoresAdapter.setOnColorClickListener(corEscolhida -> {
            try {
                layoutNotaFormulario.setBackgroundColor(Color.parseColor(corEscolhida));
                corDeFundoSelecionada = corEscolhida;
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Houve um erro ao selecionar a cor", Toast.LENGTH_SHORT).show();
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
            if(notaRecebida != null){
                notaRecebida.setTitulo(titulo.getText().toString());
                notaRecebida.setDescricao( descricao.getText().toString());
                notaRecebida.setCor(corDeFundoSelecionada);
                salvaNota(notaRecebida);
            }else {
                Nota notaCriada = criaNota();
                salvaNota(notaCriada);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void salvaNota(Nota nota) {
        new SalvaNotaTask(notaDao, nota, this::finish).execute();
    }

    @NonNull
    private Nota criaNota() {
        return new Nota(titulo.getText().toString(),
                descricao.getText().toString(),
                corDeFundoSelecionada
        );
    }

    private boolean ehMenuSalvaNota(MenuItem item) {
        return item.getItemId() == R.id.menu_formulario_nota_ic_salva;
    }


}
