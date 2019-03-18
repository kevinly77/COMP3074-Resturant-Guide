package com.example.kevin.comp3074_project_resturant_guide;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;



public class MainActivity extends AppCompatActivity {
 private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button)findViewById(R.id.btn_login);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getSharedPreferences("myPreferences", MODE_PRIVATE).edit();
                EditText name = (EditText)findViewById(R.id.txtUserName);
                editor.putString("name", name.getText().toString());
                editor.apply();
                Intent intent = new Intent(MainActivity.this, Welcome.class);
                startActivity(intent);
            }
        });
    }
}
