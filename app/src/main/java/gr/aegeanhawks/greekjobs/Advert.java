package gr.aegeanhawks.greekjobs;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class Advert extends ActionBarActivity {
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advert);

        //Get info from previews activity
        Intent intent = getIntent();
        int advertID = intent.getIntExtra("id", -1);//Check if there are no results


        //Load Database from resources
        dbHelper = new DBHelper(this.getApplicationContext());
        //Open database
        SQLiteDatabase db = dbHelper.getReadableDatabase();


        //Execute Query for searching into database
        String Query = "SELECT * FROM Advert WHERE ID='" + advertID + "'";
        Cursor resultSet = db.rawQuery(Query, null);

        //Check if there are no results
        if (resultSet.getCount() == 0) {
            Toast.makeText(getApplicationContext(), R.string.search_nothing, Toast.LENGTH_SHORT).show();
            return;
        }

        //Else get all results and save them/ Send the result set to the other activity
        resultSet.moveToFirst();

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

        //Initialize text fields variables
        TextView txtTitle = (TextView) this.findViewById(R.id.txt_results_bar);
        TextView txtDescription = (TextView) this.findViewById(R.id.description_text);
        final TextView txtArea = (TextView) this.findViewById(R.id.location_text);
        TextView txtSpecialty = (TextView) this.findViewById(R.id.specialty_text);
        final TextView txtCompany = (TextView) this.findViewById(R.id.CompanySubtitle);
        TextView txtType = (TextView) this.findViewById(R.id.employment_text);
        final TextView txtContact = (TextView) this.findViewById(R.id.contact_text);

        //Set text to text fields
        txtTitle.setText(Title);
        txtDescription.setText(Description.replace("\\n", "\n"));
        txtArea.setText(Area);
        txtSpecialty.setText(Specialty);
        txtCompany.setText(Company);
        if (Type == 0) {
            txtType.setText(R.string.employment_partial);
        } else if (Type == 1) {
            txtType.setText(R.string.employment_full);
        }
        txtContact.setText(Contact);
        txtContact.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + txtContact.getText().toString().replaceAll("[^0-9|\\+]", "")));
                startActivity(intent);
            }
        });

        txtArea.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?q=" + txtArea.getText().toString()));
                startActivity(intent);
            }
        });

        txtCompany.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://www.google.com/#q=" + txtCompany.getText().toString().replace(" ", "+"));
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_advert, menu);
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
}
