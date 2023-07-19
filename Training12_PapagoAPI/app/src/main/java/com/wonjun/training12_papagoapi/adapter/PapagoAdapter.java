package com.wonjun.training12_papagoapi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wonjun.training12_papagoapi.R;
import com.wonjun.training12_papagoapi.model.Papago;

import java.util.ArrayList;

public class PapagoAdapter extends  RecyclerView.Adapter<PapagoAdapter.ViewHolder>{

    Context context;
    ArrayList<Papago> papagoArrayList;

    public PapagoAdapter(Context context, ArrayList<Papago> papagoArrayList) {
        this.context = context;
        this.papagoArrayList = papagoArrayList;
    }

    @NonNull
    @Override
    public PapagoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.papago_row, parent, false);
        return new PapagoAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Papago papago = papagoArrayList.get(position);

        holder.txtText.setText(papago.getText());
        holder.txtTranslatedText.setText(papago.getTranslatedText());
        holder.txtTarget.setText(papago.getTarget());
    }

    @Override
    public int getItemCount() {
        return papagoArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtText;
        TextView txtTranslatedText;
        TextView txtTarget;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtText = itemView.findViewById(R.id.txtText);
            txtTranslatedText = itemView.findViewById(R.id.txtTranslatedText);
            txtTarget = itemView.findViewById(R.id.txtTarget);

        }
    }
}
