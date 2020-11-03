package com.example.knowyourgovernment;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class OfficialViewHolder extends RecyclerView.ViewHolder {

    TextView title;
    TextView name;

    OfficialViewHolder(View view) {
        super(view);
        title = view.findViewById(R.id.officialTitleTextView);
        name = view.findViewById(R.id.officialNameTextView);
    }
}
