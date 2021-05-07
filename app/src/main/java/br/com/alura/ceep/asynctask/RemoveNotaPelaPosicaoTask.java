package br.com.alura.ceep.asynctask;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import br.com.alura.ceep.dao.NotaDAO;
import br.com.alura.ceep.model.Nota;
import br.com.alura.ceep.ui.recyclerview.adapter.ListaNotasAdapter;

public class RemoveNotaPelaPosicaoTask extends AsyncTask<Void, Void, List<Nota>> {
    private final NotaDAO notaDao;
    private final ListaNotasAdapter adapter;
    private final int posicao;

    public RemoveNotaPelaPosicaoTask(NotaDAO notaDao, ListaNotasAdapter adapter, int posicao) {
        this.notaDao = notaDao;
        this.adapter = adapter;
        this.posicao = posicao;
    }

    @Override
    protected List<Nota> doInBackground(Void... voids) {
        notaDao.removePelaPosicao(posicao);
        List<Nota> notas = notaDao.todos();
        List<Nota> auxNotas = new ArrayList<>();
        for (int i = 0; i < notas.size(); i++) {
            Nota nota = notas.get(i);
            nota.setPosicao(i);
            auxNotas.add(nota);
        }
        notaDao.altera(auxNotas);
        return auxNotas;
    }

    @Override
    protected void onPostExecute(List<Nota> notas) {
        super.onPostExecute(notas);
        adapter.atualiza(notas);
    }
}
