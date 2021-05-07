package br.com.alura.ceep.utils;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class Preferencias {

    public static final String MODO_DE_LISTAGEM = "modo_de_listagem";
    public static final String LISTA_CHAVE = "lista";
    private static final String MODO_ACESSO = "MODO_ACESSO";
    private static final String PRIMEIRO_ACESSO = "PRIMEIRO_ACESSO";

    public static void salvarModoDeListagem(int modoDeListagem, Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(MODO_DE_LISTAGEM, MODE_PRIVATE).edit();
        editor.putInt(LISTA_CHAVE, modoDeListagem);
        editor.apply();
    }

    public static int pegarPreferencias(Context context) {
        SharedPreferences preferencias = context.getSharedPreferences(MODO_DE_LISTAGEM, MODE_PRIVATE);
        return preferencias.getInt(LISTA_CHAVE, 0);

    }

    public static boolean primeiroAcesso(Context context) {
        SharedPreferences preferencias = context.getSharedPreferences(MODO_ACESSO, MODE_PRIVATE);
        return preferencias.getBoolean(PRIMEIRO_ACESSO, true);
    }

    public static void salvaAcesso(Context context){
        SharedPreferences.Editor editor = context.getSharedPreferences(MODO_ACESSO, MODE_PRIVATE).edit();
        editor.putBoolean(PRIMEIRO_ACESSO, false);
        editor.apply();
    }
}
