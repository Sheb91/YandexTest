package com.example.yandextest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by Н on 24.04.2016.
 */
public class ArtistDescrActivity extends Activity
{
    TextView artistDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_artistdescr);

        artistDescription = (TextView) findViewById(R.id.txtArtistDescr);
        Intent intent = getIntent(); // получаем данные из какого-либо Activity
        String descr = intent.getStringExtra("artistKey"); // по ключу artistKey
        artistDescription.setText(descr); // отображаем полученный текст )))
    }
}
