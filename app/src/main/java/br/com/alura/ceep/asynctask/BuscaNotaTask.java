package br.com.alura.ceep.asynctask;

import android.os.AsyncTask;

import java.util.List;

import br.com.alura.ceep.dao.NotaDAO;
import br.com.alura.ceep.model.Nota;
import br.com.alura.ceep.ui.recyclerview.adapter.ListaNotasAdapter;

public class BuscaNotaTask extends AsyncTask<Void, Void, List<Nota>> {

    private final NotaDAO notaDao;
    private final ListaNotasAdapter adapter;

    public BuscaNotaTask(NotaDAO notaDao, ListaNotasAdapter adapter) {
        this.notaDao = notaDao;
        this.adapter = adapter;
    }

    @Override
    protected List<Nota> doInBackground(Void... voids) {
        return notaDao.todos();
    }

    @Override
    protected void onPostExecute(List<Nota> notas) {
        super.onPostExecute(notas);
        adapter.atualiza(notas);
    }
}
