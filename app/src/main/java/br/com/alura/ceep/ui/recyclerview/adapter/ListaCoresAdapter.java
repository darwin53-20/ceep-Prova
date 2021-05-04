package br.com.alura.ceep.ui.recyclerview.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.alura.ceep.R;
import br.com.alura.ceep.ui.recyclerview.adapter.listener.OnColorClickListener;

public class ListaCoresAdapter extends RecyclerView.Adapter<ListaCoresAdapter.CorViewHolder> {

    private final List<String> cores;
    private final Context context;
    private OnColorClickListener onColorClickListener;

    public ListaCoresAdapter(List<String> cores, Context context) {
        this.cores = cores;
        this.context = context;
    }

    public void setOnColorClickListener(OnColorClickListener onColorClickListener) {
        this.onColorClickListener = onColorClickListener;
    }

    @Override
    public CorViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {

        View viewCriada = LayoutInflater.from(context).inflate(R.layout.item_cor, parent, false);
        return new CorViewHolder(viewCriada);
    }

    @Override
    public void onBindViewHolder(CorViewHolder holder, final int position) {
        final String cor = cores.get(position);
        holder.setCorDeFundo(cor);
        holder.corDeFundo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onColorClickListener.onItemClick(cor);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cores.size();
    }

    class CorViewHolder extends RecyclerView.ViewHolder{
        ImageView corDeFundo;
        public CorViewHolder(View itemView){
            super(itemView);
            corDeFundo = itemView.findViewById(R.id.opcao_item_cor);
        }

        public void setCorDeFundo(String cor){
            ShapeDrawable biggerCircle = new ShapeDrawable(new OvalShape());
            biggerCircle.getPaint().setColor(Color.parseColor(cor)); //Dá para colocar qualquer cor aqui
            corDeFundo.setBackgroundDrawable(biggerCircle);
        }

    }
}
