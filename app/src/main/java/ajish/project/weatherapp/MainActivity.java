package ajish.project.weatherapp;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Calendar;


public class MainActivity extends AppCompatActivity {

    TextView textViewDate,textViewLocation,textViewTemprature,textViewWindspeed,textViewhumidity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewDate=findViewById(R.id.date);
        textViewLocation=(TextView)findViewById(R.id.location);
        textViewTemprature=(TextView)findViewById(R.id.temprature);
        textViewWindspeed=(TextView)findViewById(R.id.windspeed);
        textViewhumidity=(TextView)findViewById(R.id.humidity);

        date();

        DataHelper.PlaceIdtask asyncTask=new DataHelper.PlaceIdtask(new DataHelper.AsyncResponse(){

            @Override
            public void processFinish(String weather_city, String weatther_temprature, String weather_humidity, String weather_pressure) {
                textViewLocation.setText(weather_city);
                textViewTemprature.setText(weatther_temprature);
                textViewWindspeed.setText(weather_pressure);
                textViewhumidity.setText(weather_humidity);
            }
        });
        asyncTask.execute("25.180000", "89.530000");
    }

    public void date(){
        Calendar calendar=Calendar.getInstance();
        String currentdate= DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());

        textViewDate.setText(currentdate);
    }
}
