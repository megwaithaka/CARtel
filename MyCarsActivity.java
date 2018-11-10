package android.example.com.cartel;


import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Locale;

public class MyCarsActivity extends AppCompatActivity implements View.OnClickListener {

    private HashMap<String , String> carRegistrationData;

    private EditText etCarName;
    private EditText etCarMake;
    private Spinner dealersSpiner;
    private  SharedPreferences sp;

    private static final String BASE_URL = "http://192.168.0.36:8000/api/car ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cars);

        sp = getSharedPreferences("login" , MODE_PRIVATE);
        loadLocale();
        setTitle(getResources().getString(R.string.title_activity_car));

        String[] dealers = sp.getString("dealers","").split(",");

        dealersSpiner = (Spinner) findViewById(R.id.spCarDealer);
        Button  btnAddCar     = (Button)  findViewById(R.id.btnAddCar);
        carRegistrationData   = new HashMap<String, String>();

        etCarMake             = (EditText) findViewById(R.id.etCarMake);
        etCarName             = (EditText) findViewById(R.id.etCarName);


        ArrayAdapter<String> dealersKindArray =  new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item ,dealers);
        dealersKindArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dealersSpiner.setAdapter(dealersKindArray);
        dealersSpiner.setPrompt(getResources().getString(R.string.txt_car_dealer));

        btnAddCar.setOnClickListener(this);
    }

    private void setCarRegistrationData(){
        carRegistrationData.put("owner_id",  String.valueOf(sp.getInt("userid" ,0 )));
        carRegistrationData.put("car_make" , etCarMake.getText().toString());
        carRegistrationData.put("name" , etCarName.getText().toString());
        carRegistrationData.put("dealer_id"   , dealersSpiner.getSelectedItem().toString());
    }

    @Override
    public void onClick(View v) {

        int choice = v.getId();

        switch(choice){

            case R.id.btnAddCar:
                setCarRegistrationData();
                AddCarAsyncTask carAsyncTask = new AddCarAsyncTask();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.CUPCAKE) {
                    carAsyncTask.execute();
                }

                break;
        }

    }


    public class AddCarAsyncTask extends AsyncTask<String , Integer , Bitmap>{

        private String carRegistration ;
        private URL    carRegistrationUrl;
        private ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(MyCarsActivity.this);
            dialog.setMessage(getResources().getString(R.string.txt_cars));
            dialog.show();
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            System.out.println("Results: " + carRegistration);
            dialog.dismiss();

            try{
                JSONObject json = new JSONObject(carRegistration);

                Boolean  result = json.getJSONObject("success").getBoolean("success");
                if(result){
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.txt_car_added) , Toast.LENGTH_LONG ).show();
                }else{
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.txt_car_notadded) , Toast.LENGTH_LONG ).show();
                }
            }catch (JSONException e){
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.txt_car_notadded) , Toast.LENGTH_LONG ).show();
                System.out.println("Error: " + e.getMessage());
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            try{
                carRegistrationUrl = new URL(BASE_URL);
            }catch(MalformedURLException e){
                System.out.println("Error: " + e.getMessage() );
            }
            carRegistration = FetchData.getAllSportGames( carRegistrationUrl , carRegistrationData , "POST" );

            return null;
        }
    }

    public void setLocale(String lang){
        Locale newLocale = new Locale(lang);
        Locale.setDefault(newLocale);
        Configuration config = new Configuration();
        config.locale = newLocale;

        getBaseContext().getResources().updateConfiguration(config , getBaseContext().getResources().getDisplayMetrics() );

        SharedPreferences.Editor editor = getSharedPreferences("Settings" , MODE_PRIVATE).edit();
        editor.putString("My_lang" , lang);
        editor.apply();
        editor.commit();
    }

    public void loadLocale(){

        SharedPreferences prefs = getSharedPreferences("Settings" , MODE_PRIVATE);
        String language         = prefs.getString("My_lang","");
        setLocale(language);
    }


}

