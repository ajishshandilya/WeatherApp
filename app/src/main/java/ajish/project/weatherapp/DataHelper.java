package ajish.project.weatherapp;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;

public class DataHelper {

    private static final String WEATHER_MAP_URL="http://api.openweathermap.org/data/2.5/weather?lat=%s&lon=%s&units=metric";

    private static final String WEATHER_MAP_API="797d0c908c330ca61d66d807f6f8ab39";



    public interface AsyncResponse{
        void processFinish(String output1,String output2,String output3,String output4);
    }


    public static class PlaceIdtask extends AsyncTask<String,Void,JSONObject>{

        public AsyncResponse delegate=null;

        public PlaceIdtask(AsyncResponse delegate) {
            this.delegate = delegate;
        }

        @Override
        protected JSONObject doInBackground(String... strings) {

            JSONObject jsonWeather=null;
            try{
                jsonWeather=getWeatherJSON(strings[0],strings[1]);
            }
            catch (Exception e){
                Log.d("Error","Cannot process JSON result");
            }

            return jsonWeather;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {

            try{
                if(jsonObject!=null){
                    JSONObject details=jsonObject.getJSONArray("weather").getJSONObject(0);
                    JSONObject main=jsonObject.getJSONObject("weather");

                    String city=jsonObject.getJSONObject("sys").getString("country").toUpperCase(Locale.US);
                    String temprature=String.format("%.2f",details.getDouble("temp"));
                    String humidity=details.getString("humidity");
                    String pressure=details.getString("pressure");

                    delegate.processFinish(city,temprature,humidity,pressure);
                }

            }
            catch (Exception e){

            }
        }
    }

    public static JSONObject getWeatherJSON(String lat,String lng){
        try{
            URL url=new URL(String.format(WEATHER_MAP_URL,lat,lng));
            HttpURLConnection connection=(HttpURLConnection)url.openConnection();

            connection.addRequestProperty("x-api-key",WEATHER_MAP_API);

            BufferedReader reader=new BufferedReader(new InputStreamReader(connection.getInputStream()));

            StringBuffer json=new StringBuffer(1024);
            String tmp="";
            while ((tmp=reader.readLine())!=null)
                json.append(tmp).append("\n");
                reader.close();

                JSONObject data=new JSONObject(json.toString());

                if (data.getInt("cod")!=200){
                    return null;
                }
                return data;
        }
        catch (Exception e){
            return null;
        }
    }

}
