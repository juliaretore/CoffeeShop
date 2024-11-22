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
import com.example.coffeeshop.data.CartManager;
import com.example.coffeeshop.model.CartItem;
import com.example.coffeeshop.model.Product;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<Product> productList;
    private CartManager cartManager;

    public ProductAdapter(List<Product> productList, CartManager cartManager) {
        this.productList = productList;
        this.cartManager = cartManager;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
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

        // Obter a quantidade atual do carrinho
        int[] currentQuantity = {getQuantityFromCart(product)}; // Use um array para encapsular a variável
        holder.incrementValue.setText(String.valueOf(currentQuantity[0]));
        updateTotalPrice(holder, product.getPrice(), currentQuantity[0]);

        Glide.with(holder.itemView.getContext())
                .load(product.getImage())
                .into(holder.image);

        // Incrementar quantidade
        holder.incrementButton.setOnClickListener(v -> {
            int newQuantity = currentQuantity[0] + 1;
            currentQuantity[0] = newQuantity; // Atualizar o valor no array
            holder.incrementValue.setText(String.valueOf(newQuantity));
            updateTotalPrice(holder, product.getPrice(), newQuantity);

            // Atualizar o carrinho
            cartManager.addItem(new CartItem(product, newQuantity));
        });

        // Decrementar quantidade
        holder.decrementButton.setOnClickListener(v -> {
            if (currentQuantity[0] > 0) {
                int newQuantity = currentQuantity[0] - 1;
                currentQuantity[0] = newQuantity; // Atualizar o valor no array
                holder.incrementValue.setText(String.valueOf(newQuantity));
                updateTotalPrice(holder, product.getPrice(), newQuantity);

                if (newQuantity == 0) {
                    cartManager.removeItem(new CartItem(product, 0));
                } else {
                    cartManager.addItem(new CartItem(product, newQuantity));
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return productList.size();
    }

    private int getQuantityFromCart(Product product) {
        for (CartItem item : cartManager.getCartItems()) {
            if (item.getProduct().getName().equals(product.getName())) {
                return item.getQuantity();
            }
        }
        return 0;
    }

    private void updateTotalPrice(ProductViewHolder holder, double price, int quantity) {
        double total = price * quantity;
        if (quantity > 0) {
            holder.totalPrice.setText(String.format("Valor total: R$ %.2f", total));
            holder.totalPrice.setVisibility(View.VISIBLE);
        } else {
            holder.totalPrice.setVisibility(View.GONE);
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
