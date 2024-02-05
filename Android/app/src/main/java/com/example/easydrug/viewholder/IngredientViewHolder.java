package com.example.easydrug.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easydrug.R;
import com.example.easydrug.adapter.IngredientAdapter;

public class IngredientViewHolder  extends RecyclerView.ViewHolder {
    private TextView ingredientName;
    private ImageView deleteButton;
    private IngredientAdapter adapter;
    private int position;
    private String TAG = "IngredientViewHolder";

    public IngredientViewHolder(@NonNull View itemView) {
        super(itemView);
        ingredientName = itemView.findViewById(R.id.ingredient_name);
        deleteButton = itemView.findViewById(R.id.delete);

        deleteButton.setOnClickListener(v -> {
            if (adapter != null) {
                adapter.deleteItem(position);
            }
        });
    }

    public void setData(String ingredient, IngredientAdapter adapter, int position) {
        ingredientName.setText(ingredient);
        this.adapter = adapter;
        this.position = position;
    }

    public String getIngredientName() {
        return ingredientName.getText().toString();
    }
}
