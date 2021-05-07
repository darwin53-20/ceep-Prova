package br.com.alura.ceep.ui;

import android.content.Context;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import br.com.alura.ceep.asynctask.BuscaNotaTask;
import br.com.alura.ceep.dao.NotaDAO;
import br.com.alura.ceep.dataBase.NotasDatabase;
import br.com.alura.ceep.ui.recyclerview.adapter.ListaNotasAdapter;
import br.com.alura.ceep.ui.recyclerview.adapter.listener.OnItemClickListener;
import br.com.alura.ceep.ui.recyclerview.helper.callback.NotaItemTouchHelperCallback;

public class ListaNotaView {
    private final ListaNotasAdapter adapter;
    private final NotaDAO dao;

    public ListaNotaView(Context context) {
        adapter = new ListaNotasAdapter(context);
        this.dao = NotasDatabase.getInstance(context)
                .getNotaDAO();
        adapter.setOnItemClickListener((OnItemClickListener) context);
    }


    public void atualizaNotas() {
        new BuscaNotaTask(dao, adapter).execute();
    }

    public void configuraAdapter(RecyclerView listaDeNotas) {
        listaDeNotas.setAdapter(adapter);
    }

    public void configuraItemTouchHelper(RecyclerView listaNotas) {
        ItemTouchHelper itemTouchHelper =
                new ItemTouchHelper(new NotaItemTouchHelperCallback(adapter, dao));
        itemTouchHelper.attachToRecyclerView(listaNotas);
    }
}
