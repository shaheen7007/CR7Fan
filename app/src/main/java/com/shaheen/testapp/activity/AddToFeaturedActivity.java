package com.shaheen.testapp.activity;

import android.os.Bundle;
import android.widget.EditText;

import com.shaheen.testapp.ComboBox;
import com.shaheen.testapp.R;

import androidx.appcompat.app.AppCompatActivity;

public class AddToFeaturedActivity extends AppCompatActivity {

    private ComboBox spnrCountry;
    private EditText etName, etID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_featured);

        spnrCountry =(ComboBox)findViewById(R.id.spnr_country);
        etID = (EditText) findViewById(R.id.tiktokid_editText);
        etName = (EditText) findViewById(R.id.name_editText);


    }
}
