package br.com.alura.ceep.asynctask;

import android.os.AsyncTask;

import java.util.List;

import br.com.alura.ceep.dao.NotaDAO;
import br.com.alura.ceep.model.Nota;
import br.com.alura.ceep.ui.recyclerview.adapter.ListaNotasAdapter;

public class TrocaNotasTask extends AsyncTask<Void, Void, List<Nota>> {

    private final NotaDAO notaDao;
    private final ListaNotasAdapter adapter;
    private final int posicaoInicial;
    private final int posicaoFinal;

    public TrocaNotasTask(NotaDAO notaDao, ListaNotasAdapter adapter, int posicaoInicial, int posicaoFinal) {
        this.notaDao = notaDao;
        this.adapter = adapter;
        this.posicaoInicial = posicaoInicial;
        this.posicaoFinal = posicaoFinal;
    }

    @Override
    protected List<Nota> doInBackground(Void... voids) {
        Nota notaInicial = notaDao.buscaNotaPelaPosicao(posicaoInicial);
        Nota notaFinal = notaDao.buscaNotaPelaPosicao(posicaoFinal);
        notaInicial.setPosicao(posicaoFinal);
        notaFinal.setPosicao(posicaoInicial);
        notaDao.alteraNota(notaInicial);
        notaDao.alteraNota(notaFinal);
        return notaDao.todos();
    }

    @Override
    protected void onPostExecute(List<Nota> notas) {
        super.onPostExecute(notas);
        adapter.atualiza(notas);
    }
}
