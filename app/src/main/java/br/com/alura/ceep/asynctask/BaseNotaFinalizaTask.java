package br.com.alura.ceep.asynctask;

import android.os.AsyncTask;

public class BaseNotaFinalizaTask extends AsyncTask<Void, Void, Void> {

    private final FinalizadaListener listener;

    public BaseNotaFinalizaTask(FinalizadaListener listener) {
        this.listener = listener;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        listener.quandoFinalizada();
    }

    public interface FinalizadaListener {
        void quandoFinalizada();
    }
}
