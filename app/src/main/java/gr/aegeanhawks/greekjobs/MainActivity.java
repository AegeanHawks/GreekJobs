package gr.aegeanhawks.greekjobs;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Load Database from resources
        dbHelper = new DBHelper(this.getApplicationContext());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void Search_Clicked(View v) {
        //Get Search text
        String searchfield = ((EditText) findViewById(R.id.searchfield)).getText().toString();

        //Check nothing set to be searched
        if (searchfield.isEmpty()) {
            Toast.makeText(getApplicationContext(), R.string.search_nothing, Toast.LENGTH_SHORT).show();
            return;
        }

        //Search in database
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        //Execute Query for searching into database
        String Query = "SELECT * FROM Advert WHERE Description LIKE '%" + searchfield + "%' OR Area LIKE '%" + searchfield + "%' OR Title LIKE '%" + searchfield + "%' OR Specialty LIKE '%" + searchfield + "%' OR Company LIKE '%" + searchfield + "%'";
        Cursor resultSet = db.rawQuery(Query, null);

        //Check if there are no results
        if (resultSet.getCount() == 0) {
            Toast.makeText(getApplicationContext(), R.string.search_empty, Toast.LENGTH_SHORT).show();
            return;
        }

        //An class to hold the results
        ArrayList<Ads> resList = new ArrayList<>();

        //Else get all results and save them/ Send the result set to the other activity
        if (resultSet.moveToFirst()) {
            while (resultSet.isAfterLast() == false) {
                String Title = resultSet.getString(resultSet
                        .getColumnIndex("Title"));
                String Description = resultSet.getString(resultSet
                        .getColumnIndex("Description"));
                String Area = resultSet.getString(resultSet
                        .getColumnIndex("Area"));
                String Specialty = resultSet.getString(resultSet
                        .getColumnIndex("Specialty"));
                String Company = resultSet.getString(resultSet
                        .getColumnIndex("Company"));
                Integer Type = Integer.parseInt(resultSet.getString(resultSet
                        .getColumnIndex("Type")));
                String Contact = resultSet.getString(resultSet
                        .getColumnIndex("Contact"));
                //Add the adevert to our list
                resList.add(new Ads(Title, Area, Company, Type, Specialty, Contact, Description)); //Eisagvsgh se lista h kateu8eia ektupwsh
                resultSet.moveToNext();
            }
        }

        Log.v("DATABASE_RESULT", resList.toString());

        Intent i = new Intent(MainActivity.this, AllAds.class);
        i.putExtra("resList", resList);

        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i); //Στην onCreate της MainActivity κάνω retrieve το object Icarus - φτιάξε τα υπόλοιπα, δες τι έκανε και ο Χάρης
        MainActivity.this.finish();

    }
}
