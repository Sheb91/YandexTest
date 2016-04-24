package com.example.yandextest;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;


public class MainActivity extends Activity implements AdapterView.OnItemClickListener
{
    private static String jsonResourse = "http://download.cdn.yandex.net/mobilization-2016/artists.json";
    private static final String TAG = "MyLogs";
    private ListView artistList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
        artistList = (ListView)findViewById(R.id.menu_list);
        artistList.setOnItemClickListener(this);

        Log.d(TAG, "started");
        new ParseTask().execute(); // запуск асинхронной задачи в которой выполняется получение данных из внешнего ресурса.
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) // выбор исполнителя
    {
        Intent intent = new Intent(this, ArtistDescrActivity.class);
        intent.putExtra("artistKey", ((Artist)artistList.getItemAtPosition(position)).getDesc()); // отправляем данные по ключу
        startActivity(intent);
    }

    private class ParseTask extends AsyncTask<Void, Void, String>
    {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String resultJson = "";

        @Override
        protected String doInBackground(Void... params) // получаем данные с ресурса jsonResourse = "http://download.cdn.yandex.net/mobilization-2016/artists.json";
        {
            Log.d(TAG, "doInBackground started");
            try
            {
                URL url = new URL(jsonResourse);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null)
                {
                    buffer.append(line);
                }
                resultJson = buffer.toString();
                reader.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return resultJson;
        }

        @Override
        protected void onPostExecute(String strJson) // парсинг данных из JSON-объекта
        {
            super.onPostExecute(strJson);
            JSONArray dataJsonArray = null;
            String[] menu = null;
            Artist [] artists = null;
            Log.d(TAG, strJson);
            try
            {
                dataJsonArray = new JSONArray(strJson);
                int arrayLength = dataJsonArray.length();
                artists = new Artist[arrayLength];
                for (int i = 0 ; i < arrayLength; i++)
                {
                    artists[i] = new Artist();
                    artists[i].setId(Integer.parseInt(dataJsonArray.getJSONObject(i).get("id").toString()));
                    artists[i].setName(dataJsonArray.getJSONObject(i).get("name").toString());
                    artists[i].setDesc(dataJsonArray.getJSONObject(i).get("description").toString());
                }
                ArrayAdapter<Artist> artistAdapter = new ArrayAdapter<Artist>(MainActivity.this,android.R.layout.simple_list_item_1, artists);
                artistList.setAdapter(artistAdapter);
            }
            catch (JSONException e)
            {
                e.printStackTrace();
                Log.d(TAG, "ошибка");
            }
        }
    }
}
