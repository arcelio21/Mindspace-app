package com.mindspace.app.usecases.listdiario;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mindspace.app.R;
import com.mindspace.app.model.user.DiarioGet;

import java.util.List;

public class DiarioAdapterRV extends RecyclerView.Adapter<DiarioAdapterRV.ItemsDiarioViewHolder> {


    private List<DiarioGet> diarioList;

    public DiarioAdapterRV(List<DiarioGet> diarioList) {
        this.diarioList = diarioList;
    }

    @NonNull
    @Override
    public ItemsDiarioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_notas, parent, false);

        return new ItemsDiarioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemsDiarioViewHolder holder, int position) {

        holder.setData(this.diarioList.get(position));
    }

    @Override
    public int getItemCount() {
        return this.diarioList.size();
    }

    public void setDiarioList(List<DiarioGet> diarioList) {
        this.diarioList = diarioList;
    }

    public static class ItemsDiarioViewHolder extends RecyclerView.ViewHolder{

        private TextView tvTitle;
        private TextView tvCreationDate;
        private TextView tvLastUpdate;
        private DiarioGet diario;

        public ItemsDiarioViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void setData(DiarioGet diario) {
            this.diario = diario;

            this.tvTitle.setText(diario.getTitulo());
            this.tvCreationDate.setText(diario.getFechaCreacion());
            this.tvLastUpdate.setText(diario.getUltimaActualizacion());
        }
    }
}
