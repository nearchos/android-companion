package io.github.nearchos.handlingjson;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    private TextView jsonTextView;
    private TextView resultTextView;

    private String json;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        jsonTextView = findViewById(R.id.activity_main_json);
        resultTextView = findViewById(R.id.activity_main_result);

        // initialize json String
        json = readFromAssets("sample.json");

        jsonTextView.setText(json);
    }

    public void parse(View view) {
        try {
            JSONObject jsonObject = new JSONObject(json); // root
            JSONArray rows = jsonObject.getJSONArray("rows"); // 'rows' array
            JSONObject row0 = rows.getJSONObject(0); // first object in 'rows' array
            JSONArray elements = row0.getJSONArray("elements"); // 'elements' array
            JSONObject element0 = elements.getJSONObject(0); // first element
            JSONObject distance = element0.getJSONObject("distance"); // 'distance' object
            int value = distance.getInt("value"); // 'value' integer property
            resultTextView.setText("Distance: " + value + "m"); // display read value
        } catch (JSONException jsone) {
            Log.e("android-companion", "Error while parsing JSON", jsone);
        }
    }

    public void map(View view) {
//        Gson gson = new Gson();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Result result = gson.fromJson(json, Result.class);
        resultTextView.setText("Distance: " + result.rows[0].elements[0].distance.value + "m");
        Log.d("android-companion", result.toString());
        Toast.makeText(this, "json: " + gson.toJson(result), Toast.LENGTH_SHORT).show();
    }

    /**
     * Reads fully the content of the specified filename and returns the content as a String.
     * @param filename the name of the file in the /assets folder to be read fully.
     * @return the content of the file as a String.
     */
    private String readFromAssets(final String filename) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(getAssets().open(filename)));

            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append("\n"); // process line
            }
            return stringBuilder.toString();
        } catch (IOException ioe) {
            // log the exception
            Log.e("android-companion", "IO error while reading file from assets", ioe);
            return "IO error";
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ioe) {
                    //log the exception
                    Log.e("android-companion", "IO error while closing the assets input", ioe);
                }
            }
        }
    }
}