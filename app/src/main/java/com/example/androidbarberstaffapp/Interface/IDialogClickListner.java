package com.example.androidbarberstaffapp.Interface;

import android.content.DialogInterface;

public interface IDialogClickListner {
    void onClickPositiveButton(DialogInterface dialogInterface, String userName, String password);
    void onClickNegativeButton(DialogInterface dialogInterface);
}
