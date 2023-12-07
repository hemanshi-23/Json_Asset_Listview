package com.example.jsonassetlistview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ListView;

import com.example.jsonassetlistview.CustomAdapter.CustomAdapter;
import com.example.jsonassetlistview.ModelClass.Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Model> modelArrayList;
    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        modelArrayList = new ArrayList<>();

        try{

            JSONObject jsonObject = new JSONObject(loadJSONFromAsset());
            JSONArray jsonArray = jsonObject.getJSONArray("json");

            for (int i=0 ; i<jsonArray.length() ; i++ )
            {
                JSONObject jsonObjectInsideArray = jsonArray.getJSONObject(i);

                int id = jsonObjectInsideArray.getInt("id");
                double lat = jsonObjectInsideArray.getDouble("latitude");
                double lon = jsonObjectInsideArray.getDouble("longitude");

                modelArrayList.add(new Model(id,lat,lon));
            }

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        customAdapter = new CustomAdapter(modelArrayList,getApplicationContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(customAdapter);

    }

    public String loadJSONFromAsset()
    {
        String json = null;

        try {
            InputStream inputStream = MainActivity.this.getAssets().open("JsonObject.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer,"UTF-8");

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}