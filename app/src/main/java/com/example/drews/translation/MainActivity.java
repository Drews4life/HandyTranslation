package com.example.drews.translation;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private EditText text;
    private TextView translated;
    private Button translateBtn;
    private Button translateBtn2;

    private final String URL = "https://translate.yandex.net";
    private final String KEY = "trnsl.1.1.20171223T114042Z.6940ed4fd3030f23.f36f71b68efc1c81e25707e025f728ed64fb85d3";
    private Gson gson = new GsonBuilder().create();

    private Retrofit retrofit = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(URL)
            .build();

    private Link intf = retrofit.create(Link.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        text = findViewById(R.id.text);
        translated = findViewById(R.id.translated);
        translateBtn = findViewById(R.id.translatedBtn);
        translateBtn2 = findViewById(R.id.translateBtn2);
    }

    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.translatedBtn:
                Map<String, String> mapJson = new HashMap<String, String>();
                mapJson.put("key", KEY);
                mapJson.put("text", text.getText().toString());
                mapJson.put("lang", "en-ru");

                Call<Object> call = intf.translate(mapJson);

                try {
                    Response<Object> response = call.execute();

                    Map<String, String> map = gson.fromJson(response.body().toString(), Map.class);
                    if (map == null) {
                        Toast toast = Toast.makeText(getApplicationContext(),
                                "Something went wrong", Toast.LENGTH_SHORT);
                        toast.show();
                    }

                    for (Map.Entry e : map.entrySet()) {
                        if (e.getKey().equals("text")) {
                            translated.setText(e.getValue().toString());
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            case R.id.translateBtn2:
                mapJson = new HashMap<String, String>();
                mapJson.put("key", KEY);
                mapJson.put("text", text.getText().toString());
                mapJson.put("lang", "ru-en");

                call = intf.translate(mapJson);

                try {
                    Response<Object> response = call.execute();

                    Map<String, String> map = gson.fromJson(response.body().toString(), Map.class);
                    if (map == null) {
                        Toast toast = Toast.makeText(getApplicationContext(),
                                "Something went wrong", Toast.LENGTH_SHORT);
                        toast.show();
                    }

                    for (Map.Entry e : map.entrySet()) {
                        if (e.getKey().equals("text")) {
                            translated.setText(e.getValue().toString());
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }


}