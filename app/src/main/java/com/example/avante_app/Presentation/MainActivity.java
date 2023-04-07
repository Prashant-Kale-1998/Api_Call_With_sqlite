package com.example.avante_app.Presentation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.avante_app.Adapter.ItemAdapter;
import com.example.avante_app.Adapter.RetrofitAdapter;
import com.example.avante_app.ApiInterface;
import com.example.avante_app.DataModels.DataModel;
import com.example.avante_app.DataModels.Datum;
import com.example.avante_app.MyDBHandler.MyDbHandler;
import com.example.avante_app.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    public ArrayList<Datum> apidata =new ArrayList<>();
    RecyclerView recyclerview;
    private ItemAdapter adapter;
    MyDbHandler db = new MyDbHandler(MainActivity.this);
    DataModel DataModelMain = new DataModel();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerview = findViewById(R.id.rvRecyclerView);

        loadDataApi();
    }

    private void loadDataApi() {

        ApiInterface apiInterface= RetrofitAdapter.getRetrofitInstance().create(ApiInterface.class);

        Call<DataModel> call = apiInterface.getApiData();
        call.enqueue(new Callback<DataModel>() {
            @Override
            public void onResponse(Call<DataModel> call, Response<DataModel> response) {
                DataModelMain = response.body();
                apidata=(ArrayList<Datum>) DataModelMain.getData();

                if(apidata!=null || apidata.size()!=0){
                    db.allDataDelete();
                }


        for(int i=0;i<DataModelMain.getData().size();i++){
            Datum datum = new Datum();
            datum.setId(DataModelMain.getData().get(i).getId());
            datum.setEmail(DataModelMain.getData().get(i).getEmail());
            datum.setFirstName(DataModelMain.getData().get(i).getFirstName());
            datum.setLastName(DataModelMain.getData().get(i).getLastName());
            datum.setAvatar(DataModelMain.getData().get(i).getAvatar());
            db.addContact(datum);
            Log.d("Tag", i +" Data inserted successfully...!" );

        }

                recyclerview.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                adapter = new ItemAdapter(MainActivity.this,apidata);
                recyclerview.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<DataModel> call, Throwable t) {
                Log.d("TAG", "onFailure :"+t.getMessage());

                ArrayList<Datum> allDataList =db.getAllContacts();

                if(allDataList.size()!=0 && allDataList!=null){
                recyclerview.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                adapter = new ItemAdapter((Context) MainActivity.this,allDataList);
                recyclerview.setAdapter(adapter);
                }else{
                    Toast.makeText(MainActivity.this, "Data Not available in local storage", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}