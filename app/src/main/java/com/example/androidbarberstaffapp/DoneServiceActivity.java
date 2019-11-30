package com.example.androidbarberstaffapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.esotericsoftware.kryo.serializers.FieldSerializer;
import com.example.androidbarberstaffapp.Common.Common;
import com.example.androidbarberstaffapp.Fragments.ShoppingFragment;
import com.example.androidbarberstaffapp.Interface.IBarberServiceLoadListener;
import com.example.androidbarberstaffapp.Interface.IOnShoppingItemSelected;
import com.example.androidbarberstaffapp.Model.BarberServices;
import com.example.androidbarberstaffapp.Model.ShoppingItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import dmax.dialog.SpotsDialog;

public class DoneServiceActivity extends AppCompatActivity implements IBarberServiceLoadListener, IOnShoppingItemSelected {

    @BindView(R.id.txt_customer_name)
    TextView txt_customer_name;

    @BindView(R.id.txt_customer_phone)
    TextView txt_customer_phone;

    @BindView(R.id.chip_group_services)
    ChipGroup chip_group_services;

    @BindView(R.id.chip_group_shopping)
    ChipGroup chip_group_shopping;

    @BindView(R.id.edt_services)
    AppCompatAutoCompleteTextView edt_services;

    @BindView(R.id.img_customer_hair)
    ImageView img_customer_hair;

    @BindView(R.id.add_shopping)
    ImageView add_shopping;

    @BindView(R.id.btn_finish)
    Button btn_finish;

    AlertDialog dialog;

    IBarberServiceLoadListener iBarberServiceLoadListener;

    HashSet<BarberServices> servicesAdded = new HashSet<>();

    List<ShoppingItem> shoppingItems = new ArrayList<>();

    LayoutInflater inflater;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_done_service);

        ButterKnife.bind(this);

        init();

        initView();

        setCustomerInformation();
        loadBarberServices();


    }

    private void initView() {

        getSupportActionBar().setTitle("Checkout");

        add_shopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShoppingFragment shoppingFragment = ShoppingFragment.getInstance(DoneServiceActivity.this);
                shoppingFragment.show(getSupportFragmentManager(), "Shopping");
            }
        });
    }

    private void init() {
        dialog = new SpotsDialog.Builder().setContext(this).setCancelable(false).build();

        inflater = LayoutInflater.from(this);

        iBarberServiceLoadListener = this;
    }

    private void loadBarberServices() {
        dialog.show();

        ///AllSalon/Ahmedabad/Branch/k6KHTxBG3w2SChaVtW4Y/Services/8VBunxd3t3NqLExJNnPw
        FirebaseFirestore.getInstance()
                .collection("AllSalon")
                .document(Common.state_name)
                .collection("Branch")
                .document(Common.selectedSalon.getSalonId())
                .collection("Services")
                .get()
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        iBarberServiceLoadListener.onBarberServiceFailed(e.getMessage());
                    }
                }).addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful())
                {
                    List<BarberServices> barberServices = new ArrayList<>();
                    for(DocumentSnapshot barberSnapShot : task.getResult())
                    {
                        BarberServices services = barberSnapShot.toObject(BarberServices.class);
                        barberServices.add(services);
                    }
                    iBarberServiceLoadListener.onBarberServiceLoadSuccess(barberServices);
                }
            }
        });

    }

    private void setCustomerInformation() {
        txt_customer_name.setText(Common.currentBookingInformation.getCustomerName());
        txt_customer_phone.setText(Common.currentBookingInformation.getCustomerPhone());
    }

    @Override
    public void onBarberServiceLoadSuccess(List<BarberServices> barberServicesList) {
        List<String>  nameServices = new ArrayList<>();

        Collections.sort(barberServicesList, new Comparator<BarberServices>() {
            @Override
            public int compare(BarberServices barberServices, BarberServices o2) {
                return barberServices.getName().compareTo(o2.getName());
            }
        });

        //ADD name of Services after Sorting
        for(BarberServices barberServices : barberServicesList)
        {
            nameServices.add(barberServices.getName());
        }

        //Create Adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.select_dialog_item, nameServices);
        edt_services.setThreshold(1);  //Will working from First Character
        edt_services.setAdapter(adapter);

        edt_services.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                //Add to Chip Group
                int index = nameServices.indexOf(edt_services.getText().toString().trim());


                if (!servicesAdded.contains(barberServicesList.get(index))) {

                    servicesAdded.add(barberServicesList.get(index)); //We don't want to have Duplicate Services
                    Chip item = (Chip) inflater.inflate(R.layout.chip_item, null);
                    item.setText(edt_services.getText().toString());
                    item.setTag(index);
                    edt_services.setText("");

                    item.setOnCloseIconClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            chip_group_services.removeView(view);
                            servicesAdded.remove((int) item.getTag());
                        }
                    });

                    chip_group_services.addView(item);

                }
                else {
                    edt_services.setText("");
                }

            }
        });

        dialog.dismiss();
    }

    @Override
    public void onBarberServiceFailed(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        dialog.dismiss();
    }

    @Override
    public void onShoppingItemSelected(ShoppingItem shoppingItem) {
        //Here, We will create a list to Hold Shopping Item

        shoppingItems.add(shoppingItem);
        Log.d("ShoppingItem", ""+shoppingItems.size());

        Chip item = (Chip) inflater.inflate(R.layout.chip_item, null);
        item.setText(shoppingItem.getName());
        item.setTag(shoppingItems.indexOf(shoppingItem));
        edt_services.setText("");

        item.setOnCloseIconClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chip_group_shopping.removeView(view);
                shoppingItems.remove((int) item.getTag());
            }
        });

        chip_group_shopping.addView(item);




    }
}
