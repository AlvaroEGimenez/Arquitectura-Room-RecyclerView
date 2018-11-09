package com.example.alva.arquitecturaroom;

import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AdaptadorNota extends ListAdapter <Nota, AdaptadorNota.NotaHolder> {


    private OnItemClickListener listener;

    public AdaptadorNota() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Nota> DIFF_CALLBACK = new DiffUtil.ItemCallback<Nota>() {
        @Override
        public boolean areItemsTheSame(@NonNull Nota oldItem, @NonNull Nota newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Nota oldItem, @NonNull Nota newItem) {
            return oldItem.getTitulo().equals(newItem.getTitulo()) &&
                    oldItem.getDescripcion().equals(newItem.getDescripcion()) &&
                    oldItem.getPrioridad()== newItem.getPrioridad();
        }
    };

    @NonNull
    @Override
    public NotaHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_nota, viewGroup, false);
        return new NotaHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NotaHolder notaHolder, int posicion) {
        Nota notaActual = getItem(posicion);

        notaHolder.textViewtitulo.setText(notaActual.getTitulo());
        notaHolder.textViewDescripcion.setText(notaActual.getDescripcion());
        notaHolder.textViewPrioriodad.setText(String.valueOf(notaActual.getPrioridad()));
    }



    public Nota getNotaEn(int posicion) {
        return getItem(posicion);
    }


    class NotaHolder extends RecyclerView.ViewHolder {
        private TextView textViewtitulo;
        private TextView textViewDescripcion;
        private TextView textViewPrioriodad;

        public NotaHolder(@NonNull View itemView) {
            super(itemView);
            textViewtitulo = itemView.findViewById(R.id.tvtitulo);
            textViewDescripcion = itemView.findViewById(R.id.tvDescripcion);
            textViewPrioriodad = itemView.findViewById(R.id.tvPrioridad);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int posicion = getAdapterPosition();
                    if (listener != null && posicion != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(posicion));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Nota nota);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;

    }
}
