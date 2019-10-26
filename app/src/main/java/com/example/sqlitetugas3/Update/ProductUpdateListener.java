package com.example.sqlitetugas3.Update;

import com.example.sqlitetugas3.pojo.Product;

public interface ProductUpdateListener {
    void onProductInfoUpdated(Product product, int position);
}
