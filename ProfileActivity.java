package android.example.com.cartel;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;


import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import java.util.Locale;

public class ProfileActivity extends AppCompatActivity {

    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    private TextView tvUserDetails;
    private TextView tvUserAssets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        setTitle(getResources().getString(R.string.title_activity_profile));

        sp = getSharedPreferences("login" , MODE_PRIVATE);
        String userCars = sp.getString("usercars","");
        editor = sp.edit();

        tvUserDetails = (TextView)findViewById(R.id.tvUserDetails);
        tvUserAssets  = (TextView)findViewById(R.id.tvUserCarDetails);

        tvUserDetails.setText(setTvUserDetails(editor));
        tvUserAssets.setText(userCars);

    }

    private String setTvUserDetails(SharedPreferences.Editor editor){
        String userDetails  = "Name: "+sp.getString("name","")+"\n";

        userDetails += "Username: "+sp.getString("username","")+"\n";
        userDetails += "Email:    "+sp.getString("email" , "") + "\n";
        userDetails += "Phone Number: "+ sp.getString("phonenumber","")+"\n";
        userDetails += "Gender: "+sp.getString("gender", "") + "\n";
        userDetails += "Residence: "+sp.getString("residence", "") + "\n";


        return userDetails;

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
