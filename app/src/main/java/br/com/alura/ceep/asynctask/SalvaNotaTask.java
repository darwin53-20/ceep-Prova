package br.com.alura.ceep.asynctask;

import br.com.alura.ceep.dao.NotaDAO;
import br.com.alura.ceep.model.Nota;


public class SalvaNotaTask extends BaseNotaFinalizaTask {

    private final NotaDAO notaDao;
    private final Nota nota;


    public SalvaNotaTask (NotaDAO notaDao, Nota nota, FinalizadaListener listener) {
        super(listener);
        this.notaDao = notaDao;
        this.nota = nota;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        if(nota.getPosicao() == -1) {
            nota.setPosicao(notaDao.ultimaPosicao());
        }
        notaDao.alteraNota(nota);
        return null;
    }
}
