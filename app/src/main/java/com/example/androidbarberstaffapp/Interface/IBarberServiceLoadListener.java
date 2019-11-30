package com.example.androidbarberstaffapp.Interface;

import com.example.androidbarberstaffapp.Model.BarberServices;

import java.util.List;

public interface IBarberServiceLoadListener {
    void onBarberServiceLoadSuccess(List<BarberServices> barberServicesList);
    void onBarberServiceFailed(String message);
}
