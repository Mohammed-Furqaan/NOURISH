package com.tap.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class CartItem {

    private int cartItemId;
    private int cartId;
    private int itemId;
    private int quantity;
    private BigDecimal price;
    private BigDecimal subtotal;
    private Timestamp addedAt;

    // Default Constructor
    public CartItem() {

    }

    // Constructor without Primary Key
    public CartItem(int cartId,
                    int itemId,
                    int quantity,
                    BigDecimal price,
                    BigDecimal subtotal) {

        this.cartId = cartId;
        this.itemId = itemId;
        this.quantity = quantity;
        this.price = price;
        this.subtotal = subtotal;
    }

    // Constructor with Primary Key
    public CartItem(int cartItemId,
                    int cartId,
                    int itemId,
                    int quantity,
                    BigDecimal price,
                    BigDecimal subtotal,
                    Timestamp addedAt) {

        this.cartItemId = cartItemId;
        this.cartId = cartId;
        this.itemId = itemId;
        this.quantity = quantity;
        this.price = price;
        this.subtotal = subtotal;
        this.addedAt = addedAt;
    }

    public int getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(int cartItemId) {
        this.cartItemId = cartItemId;
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public Timestamp getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(Timestamp addedAt) {
        this.addedAt = addedAt;
    }

    @Override
    public String toString() {
        return "CartItem [cartItemId=" + cartItemId +
                ", cartId=" + cartId +
                ", itemId=" + itemId +
                ", quantity=" + quantity +
                ", price=" + price +
                ", subtotal=" + subtotal +
                ", addedAt=" + addedAt + "]";
    }
}