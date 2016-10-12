package com.rumad.rumadapiapp.rumadapiapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.rumad.rumadapiapp.rumadapiapp.utils.RetrofitFactory;
import com.rumad.rumadapiapp.rumadapiapp.utils.WundergroundService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private EditText state, city;
    private Button getWeather;
    private TextView weatherTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        state = (EditText)findViewById(R.id.stateField);
        city = (EditText)findViewById(R.id.cityField);
        getWeather = (Button)findViewById(R.id.weather_btn);
        weatherTxt = (TextView)findViewById(R.id.weather_txt);
        Retrofit retrofit = RetrofitFactory.getRetrofit();
        final WundergroundService service = retrofit.create(WundergroundService.class);
        final WundergroundCallback wundergroundCallback = new WundergroundCallback();
        getWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = state.getText().toString();
                String c = city.getText().toString();
                Call<ResponseBody> call = service.getForecast(s,c);
                call.enqueue(wundergroundCallback)SA;
            }
        });

    }


    private class WundergroundCallback implements Callback<ResponseBody> {

        @Override
        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            try {
                String body = response.body().string();
                JSONObject jsonObject = new JSONObject(body);
                JSONObject forecast = jsonObject.getJSONObject("forecast");
                JSONObject txtForecast = forecast.getJSONObject("txt_forecast");
                JSONArray days = txtForecast.getJSONArray("forecastday");
                JSONObject dayOne = days.getJSONObject(0);
                weatherTxt.setText(dayOne.getString("fcttext"));
            } catch (IOException e) {

            } catch (JSONException e) {

            }

        }

        @Override
        public void onFailure(Call<ResponseBody> call, Throwable t) {

        }
    }


}
