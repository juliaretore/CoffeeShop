package com.example.coffeeshop.ui.shop;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

        // Configurar dados dinâmicos nos campos do layout
        holder.title.setText(product.getName());
        holder.description.setText(product.getDescription());
        holder.price.setText(String.format("R$ %.2f", product.getPrice()));

        // Carregar imagem da URL com Glide
        Glide.with(holder.itemView.getContext())
                .load(product.getImage()) // URL da imagem
                .into(holder.image); // Aplica a imagem no ImageView
    }


    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title;
        TextView description;
        TextView price;

        public ProductViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.product_image);
            title = itemView.findViewById(R.id.product_title);
            description = itemView.findViewById(R.id.product_description);
            price = itemView.findViewById(R.id.product_price);
        }
    }
}
