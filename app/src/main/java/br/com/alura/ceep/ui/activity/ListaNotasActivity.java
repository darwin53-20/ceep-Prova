package br.com.alura.ceep.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;




import br.com.alura.ceep.R;
import br.com.alura.ceep.utils.Preferencias;



import br.com.alura.ceep.model.Nota;
import br.com.alura.ceep.ui.ListaNotaView;

import br.com.alura.ceep.ui.recyclerview.adapter.listener.OnItemClickListener;


import static br.com.alura.ceep.ui.activity.NotaActivityConstantes.CHAVE_NOTA;

import static br.com.alura.ceep.ui.activity.NotaActivityConstantes.CODIGO_REQUISICAO_ALTERA_NOTA;
import static br.com.alura.ceep.ui.activity.NotaActivityConstantes.CODIGO_REQUISICAO_INSERE_NOTA;
import static br.com.alura.ceep.ui.activity.NotaActivityConstantes.POSICAO_INVALIDA;

public class ListaNotasActivity extends AppCompatActivity implements OnItemClickListener{



    public static final String TITULO_APPBAR = "Notas";
    public static final int MODO_STAGGERED = 2;
    public static final int MODO_LINEAR = 1;
    private RecyclerView listaNotas;
    private ListaNotaView listaNotaView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_notas);

        setTitle(TITULO_APPBAR);

        listaNotaView = new ListaNotaView(this);
        configuraRecyclerView();
        configuraBotaoInsereNota();
    }

    private void configuraBotaoInsereNota() {
        TextView botaoInsereNota = findViewById(R.id.lista_notas_insere_nota);
        botaoInsereNota.setOnClickListener(view -> vaiParaFormularioNotaActivityInsere());
    }

    private void vaiParaFormularioNotaActivityInsere() {
        Intent iniciaFormularioNota =
                new Intent(ListaNotasActivity.this,
                        FormularioNotaActivity.class);
        startActivityForResult(iniciaFormularioNota,
                CODIGO_REQUISICAO_INSERE_NOTA);
    }

    private void consultaTodasNotas() {
        configuraAdapter(listaNotas);
    }

    @Override
    protected void onResume() {
        super.onResume();
        listaNotaView.atualizaNotas();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lista_notas, menu);
        int modoDeListagem = Preferencias.pegarPreferencias(this);
        switch (modoDeListagem){
            case 1:
                configurarInterfaceParaListaLinear(menu.getItem(0));
                break;
            case 2:
                configurarInterfaceParaListaEscalonada(menu.getItem(0));
                break;
            default:
                Log.e("PARAMETROS", "Modo de listagem inválido");
                break;
        }
        return true;
    }

    private void configurarInterfaceParaListaEscalonada(MenuItem item) {
        item.setIcon(R.drawable.ic_list_white_24dp);
        listaNotas.setLayoutManager(new StaggeredGridLayoutManager(2,1));
    }

    private void configurarInterfaceParaListaLinear(MenuItem item) {
        item.setIcon(R.drawable.ic_grid_on_white_24dp);
        listaNotas.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_lista_exibicao_icone:
                selecionaModoListagem(item);
                return true;
            case R.id.menu_layout_feedback:
                chamaTelaDeFeedback();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void chamaTelaDeFeedback() {
        Intent abreFeedback = new Intent(ListaNotasActivity.this,
                FeedbackActivity.class);
        startActivity(abreFeedback);
    }

    private void selecionaModoListagem(MenuItem item) {
        if(Preferencias.pegarPreferencias(this) == MODO_LINEAR){
            configurarInterfaceParaListaEscalonada(item);
            salvarModoDeListagem(MODO_STAGGERED);
        }else{
            configurarInterfaceParaListaLinear(item);
            salvarModoDeListagem(MODO_LINEAR);
        }
    }

    private void salvarModoDeListagem(int modoDeListagem) {
        Preferencias.salvarModoDeListagem(modoDeListagem, this);
    }



    private boolean ehPosicaoValida(int posicaoRecebida) {
        return posicaoRecebida > POSICAO_INVALIDA;
    }

    private boolean ehResultadoAlteraNota(int requestCode, Intent data) {
        return ehCodigoRequisicaoAlteraNota(requestCode) &&
                temNota(data);
    }

    private boolean ehCodigoRequisicaoAlteraNota(int requestCode) {
        return requestCode == CODIGO_REQUISICAO_ALTERA_NOTA;
    }

    private boolean ehResultadoInsereNota(int requestCode, Intent data) {
        return ehCodigoRequisicaoInsereNota(requestCode) &&
                temNota(data);
    }

    private boolean temNota(Intent data) {
        return data != null && data.hasExtra(CHAVE_NOTA);
    }

    private boolean resultadoOk(int resultCode) {
        return resultCode == Activity.RESULT_OK;
    }

    private boolean ehCodigoRequisicaoInsereNota(int requestCode) {
        return requestCode == CODIGO_REQUISICAO_INSERE_NOTA;
    }

    private void configuraRecyclerView() {
        listaNotas = findViewById(R.id.lista_notas_recyclerview);
        consultaTodasNotas();
        listaNotaView.configuraItemTouchHelper(listaNotas);
    }



    private void configuraAdapter(RecyclerView listaNotas) {
        listaNotaView.configuraAdapter(listaNotas);
    }

    @Override
    public void onItemClick(Nota nota, int posicao) {
        Intent abreFormularioComNota = new Intent(ListaNotasActivity.this,
                FormularioNotaActivity.class);
        abreFormularioComNota.putExtra(CHAVE_NOTA, nota);
        startActivityForResult(abreFormularioComNota, CODIGO_REQUISICAO_ALTERA_NOTA);
    }

}
