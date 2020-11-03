package com.example.knowyourgovernment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class OfficialsAdapter extends RecyclerView.Adapter<OfficialViewHolder> {

    private List<Official> officialsList;
    private MainActivity main;

    public OfficialsAdapter(List<Official> list, MainActivity ma) {
        this.officialsList = list;
        this.main = ma;
    }

    @NonNull
    @Override
    public OfficialViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create inflater from parent's context, then inflate it using the defined layout
        // for each list card
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.official_list_entry, parent, false);

        // calls appropriate functions from main activity when a click/long click is detected
        itemView.setOnClickListener(main);
        itemView.setOnLongClickListener(main);

        return new OfficialViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OfficialViewHolder holder, int position) {
        Official official = officialsList.get(position);
        holder.title.setText(official.getTitle());
        holder.name.setText(String.format("%s (%s)", official.getName(), official.getParty()));
    }

    @Override
    public int getItemCount() {
        return officialsList.size();
    }
}
