package com.yami.studio.banana.jsonapp;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView text = findViewById(R.id.tv_main);
        new LoadJsonAsync(this, text).execute(R.raw.data);

    }

    private static class LoadJsonAsync extends AsyncTask<Integer, Void, String> {

        private WeakReference<TextView> text;
        private WeakReference<Context> context;

        LoadJsonAsync(Context context, TextView text){
            this.context = new WeakReference<>(context);
            this.text = new WeakReference<>(text);
        }

        @Override
        protected String doInBackground(Integer... integers) {
            String json;
            try {
                InputStream is = context.get().getResources().openRawResource(integers[0]);
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                json = new String(buffer, StandardCharsets.UTF_8);
            } catch (IOException ex) {
                ex.printStackTrace();
                return null;
            }
            return json;
        }

        @Override
        protected void onPostExecute(String json) {
            try {
                JSONObject obj = new JSONObject(json);
                JSONObject person = obj.getJSONObject("person");

                String name = person.getString("name");
                int age = person.getInt("age");
                String gender = person.getString("gender");

                text.get().append("name" + "\t" + ": " + name + "\n");
                text.get().append("age" + "\t" + ": " + age + "\n");
                text.get().append("gender" + "\t" + ": " + gender + "\n");

                JSONArray address = person.getJSONArray("address");
                text.get().append("address" + "\t" + ": " + "\n");
                for(int i=0; i<address.length(); i++){
                    JSONObject objAdd = address.getJSONObject(i);
                    String addName = objAdd.getString("name");
                    String addDetail = objAdd.getString("detail");
                    String addCity = objAdd.getString("city");
                    text.get().append("\t" + addName + " = " + addDetail + ", " + addCity + "\n");
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
