package com.example.kevin.comp3074_project_resturant_guide;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kevin.comp3074_project_resturant_guide.database.AppDatabase;
import com.example.kevin.comp3074_project_resturant_guide.model.Restaurant;

public class Welcome extends AppCompatActivity {

    private AppDatabase db;
    private Button gotolist, btnAdd;
    private EditText aName,aAddress,aPhone,aDescription;
    private RatingBar aRating;
    private Spinner spinnerTags;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        db = AppDatabase.getInstance(this);
        gotolist = findViewById(R.id.btnLoadData);
        btnAdd = findViewById(R.id.btnAdd);
        aName = findViewById(R.id.txtAddName);
        aAddress = findViewById(R.id.txtRestuantAddress);
        aPhone = findViewById(R.id.txtAddPhone);
        aRating = findViewById(R.id.rbResturant);
        aDescription = findViewById(R.id.txtAddDescription);
        spinnerTags = findViewById(R.id.spinnerResturantTags);






        SharedPreferences preferences = getSharedPreferences("myPreferences", MODE_PRIVATE);
        String userName = preferences.getString("name", null);
        TextView name = (TextView)findViewById(R.id.txtWelcome);

        if(userName != null) {
            name.setText("Welcome " + userName);
        }

        Toast.makeText(this,"Welcome "+ userName, Toast.LENGTH_LONG).show();

        Spinner spinner = (Spinner) findViewById(R.id.spinnerResturantTags);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(Welcome.this,R.array.resturant_tags,R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        //First ask if there are already data in the database, if not, import data
        int restaurantCount = db.restaurantDao().countRestaurants();
        if (restaurantCount == 0){
            //insert into the database
            db.restaurantDao().insertAll(new Restaurant(
                    "Green Papaya",
                    "2401 Yonge Street, Toronto, ON M4P 3H1",
                    "We fusion asian dishes",
                    "thai, asian",
                    "905-111-1111",
                    2.5f));
            db.restaurantDao().insertAll(new Restaurant(
                    "Blue Papaya",
                    "2440 Yonge Street, Toronto, ON M4P 3H1",
                    "We serve blue papayas",
                    "asian",
                    "123-123-1234",
                    5.0f));
            db.restaurantDao().insertAll(new Restaurant(
                    "Isshin Ramen",
                    "421 College St, Toronto, ON M5T 1T1",
                    "authentic japanese ramen",
                    "asian",
                    "911-911-9111",
                    5.0f));

            Log.i("DATABASE-TEST","OnCreate: data inserted");
            Toast.makeText(this, "onCreate: inserted data", Toast.LENGTH_LONG).show();

        } else {
            Log.i("DATABASE-TEST","onCreate: data already exists");
            Toast.makeText(this, "onCreate: data already exists", Toast.LENGTH_LONG).show();
        }

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String addName,addAddress,addDescription,addTag,addPhone;
                float addRating;

                addName = aName.getText().toString();
                addAddress = aAddress.getText().toString();
                addDescription = aDescription.getText().toString();
                addTag = spinnerTags.getSelectedItem().toString();
                addPhone = aPhone.getText().toString();
                addRating = aRating.getRating();

                db.restaurantDao().insertAll(new Restaurant(
                        addName,
                        addAddress,
                        addDescription,
                        addTag,
                        addPhone,
                        addRating
                ));
                Toast.makeText(Welcome.this, "Added to database!", Toast.LENGTH_LONG).show();

                aName.setText("");
                aAddress.setText("");
                aDescription.setText("");
                aPhone.setText("");

            }
        });



        gotolist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ResturantList.class);
                startActivity(intent);
                finish();
            }
        });












    }





    protected void onDestroy() {
        AppDatabase.destroyInstance();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_layout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemAddResturant:
                startActivity(new Intent(this, Welcome.class));
                finish();
                return true;

            case R.id.itemResturantList:
                startActivity(new Intent(this, ResturantList.class));
                return true;

            case R.id.itemAboutUs:
                startActivity(new Intent(this, AboutUs.class));
                return true;

            case R.id.itemSplashScreen:
                startActivity(new Intent(this, SplashScreen.class));
                return true;



            default:
                return super.onOptionsItemSelected(item);

        }

    }

}
