package com.example.androidbarberstaffapp.Interface;

import com.example.androidbarberstaffapp.Model.ShoppingItem;

import java.util.List;

public interface IShoppingDataLoadListener {
        void onShoppingDataLoadSuccess(List<ShoppingItem> shoppingItemList);
        void onShoppingDataLoadFailed(String message);
}
