package com.example.coffeeshop.view.shop;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.coffeeshop.R;
import com.example.coffeeshop.controller.CartController;
import com.example.coffeeshop.model.CartItem;
import com.example.coffeeshop.model.Product;
import com.example.coffeeshop.controller.ShopController;

import java.util.List;
public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ProductViewHolder> {

    private List<Product> productList;
    private CartController cartController;
    private ShopController shopController; // Adiciona referência ao controlador

    public ShopAdapter(List<Product> productList, CartController cartController, ShopController shopController) {
        this.productList = productList;
        this.cartController = cartController;
        this.shopController = shopController;
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

        // Configura os dados básicos do produto
        holder.title.setText(product.getName());
        holder.description.setText(product.getDescription());
        holder.price.setText(String.format("R$ %.2f", product.getPrice()));
        Glide.with(holder.itemView.getContext())
                .load(product.getImage())
                .into(holder.image);

        // Atualiza a interface com a quantidade atual no carrinho
        int currentQuantity = getQuantityFromCart(product);
        holder.incrementValue.setText(String.valueOf(currentQuantity));
        updateTotalPrice(holder, product.getPrice(), currentQuantity);

        // Configura o botão de incrementar
        holder.incrementButton.setOnClickListener(v -> {
            int newQuantity = getQuantityFromCart(product) + 1; // Busca o estado mais recente
            cartController.updateItemQuantity(new CartItem(product, newQuantity)); // Atualiza o carrinho
            refreshItem(holder, product); // Atualiza somente este item
        });

        // Configura o botão de decrementar
        holder.decrementButton.setOnClickListener(v -> {
            int currentQuantityFromCart = getQuantityFromCart(product); // Busca o estado mais recente
            if (currentQuantityFromCart > 0) {
                int newQuantity = currentQuantityFromCart - 1;

                if (newQuantity == 0) {
                    cartController.removeItem(new CartItem(product, 0));
                } else {
                    cartController.updateItemQuantity(new CartItem(product, newQuantity));
                }
                refreshItem(holder, product); // Atualiza somente este item
            }
        });
    }
    private void refreshItem(ProductViewHolder holder, Product product) {
        // Obtém a quantidade atualizada do carrinho
        int updatedQuantity = getQuantityFromCart(product);

        // Atualiza os elementos de interface relacionados
        holder.incrementValue.setText(String.valueOf(updatedQuantity));
        updateTotalPrice(holder, product.getPrice(), updatedQuantity);
    }

    // Método para buscar a quantidade atual do carrinho
    private int getQuantityFromCart(Product product) {
        for (CartItem item : cartController.getCartItems()) {
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
    @Override
    public int getItemCount() {
        return productList == null ? 0 : productList.size();
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
