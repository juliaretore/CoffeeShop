package com.example.coffeeshop.ui.shop;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.coffeeshop.R;
import com.example.coffeeshop.model.Product;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<Product> productList;

    public ProductAdapter(List<Product> productList) {
        this.productList = productList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate layout and create ViewHolder
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        Product product = productList.get(position);

        holder.title.setText(product.getName());
        holder.description.setText(product.getDescription());
        holder.price.setText(String.format("R$ %.2f", product.getPrice()));

        holder.incrementValue.setText("0");
        holder.totalPrice.setVisibility(View.GONE); // Ocultar inicialmente

        Glide.with(holder.itemView.getContext())
                .load(product.getImage())
                .into(holder.image);

        holder.decrementButton.setOnClickListener(v -> {
            int currentValue = Integer.parseInt(holder.incrementValue.getText().toString());
            if (currentValue > 0) {
                holder.incrementValue.setText(String.valueOf(currentValue - 1));
                updateTotalPrice(holder, product.getPrice(), currentValue - 1);
            }
        });

        holder.incrementButton.setOnClickListener(v -> {
            int currentValue = Integer.parseInt(holder.incrementValue.getText().toString());
            holder.incrementValue.setText(String.valueOf(currentValue + 1));
            updateTotalPrice(holder, product.getPrice(), currentValue + 1);
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    private void updateTotalPrice(ProductViewHolder holder, double price, int quantity) {
        double total = price * quantity;
        if (quantity > 0) {
            holder.totalPrice.setText(String.format("Valor total: R$ %.2f", total));
            holder.totalPrice.setVisibility(View.VISIBLE); // Exibir total quando a quantidade for maior que 0
        } else {
            holder.totalPrice.setVisibility(View.GONE); // Ocultar total quando a quantidade for 0
        }
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title;
        TextView description;
        TextView price;
        TextView incrementValue;
        Button incrementButton;
        Button decrementButton;
        TextView totalPrice;

        public ProductViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.product_image);
            title = itemView.findViewById(R.id.product_title);
            description = itemView.findViewById(R.id.product_description);
            price = itemView.findViewById(R.id.product_price);
            incrementValue = itemView.findViewById(R.id.increment_value);
            incrementButton = itemView.findViewById(R.id.increment_button);
            decrementButton = itemView.findViewById(R.id.decrement_button);
            totalPrice = itemView.findViewById(R.id.product_total_price);
        }
    }
}
