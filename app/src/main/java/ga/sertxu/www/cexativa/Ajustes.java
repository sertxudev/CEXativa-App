package ga.sertxu.www.cexativa;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;

public class Ajustes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        final boolean previouslyStarted = prefs.getBoolean(getString(R.string.notificaciones), true);
        final Switch switch_notificacion = (Switch) findViewById(R.id.switch1);
        if (!previouslyStarted) {
            switch_notificacion.setChecked(false);
        } else {
            switch_notificacion.setChecked(true);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        final boolean previouslyStarted = prefs.getBoolean(getString(R.string.notificaciones), true);
        final Switch switch_notificacion = (Switch) findViewById(R.id.switch1);
        if(!switch_notificacion.isChecked()){
            FirebaseMessaging.getInstance().unsubscribeFromTopic("Noticias");
            SharedPreferences.Editor edit = prefs.edit();
            edit.putBoolean(getString(R.string.notificaciones), Boolean.FALSE);
            edit.commit();
            //Toast.makeText(this, "Notificaciones desactivadas", Toast.LENGTH_LONG).show();
            Log.d("Notificaciones", "Desactivadas");
            //startActivity(new Intent(Ajustes.this, MainActivity.class));
        }else{
            FirebaseMessaging.getInstance().subscribeToTopic("Noticias");
            SharedPreferences.Editor edit = prefs.edit();
            edit.putBoolean(getString(R.string.notificaciones), Boolean.TRUE);
            edit.commit();
            //Toast.makeText(this, "Notificaciones activadas", Toast.LENGTH_LONG).show();
            Log.d("Notificaciones", "Activadas");
            //startActivity(new Intent(Ajustes.this, MainActivity.class));
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();
        switch (itemId) {
            case android.R.id.home:
                startActivity(new Intent(Ajustes.this, MainActivity.class));
                break;
        }
        return true;
    }
}
