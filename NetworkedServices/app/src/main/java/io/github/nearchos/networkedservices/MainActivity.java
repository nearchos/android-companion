package io.github.nearchos.networkedservices;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "android-companion";

    public static final String DEFAULT_URL = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=Madrid&destinations=Rome";

    private TextView urlTextView;
    private Button connectButton;
    private TextView resultTextView;
    private Spinner originSpinner;
    private Spinner destinationSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.urlTextView = findViewById(R.id.activity_main_url);
        this.connectButton = findViewById(R.id.activity_main_connect);
        this.resultTextView = findViewById(R.id.activity_main_result);

        // bind to origins spinner, and initialize with a few cities
        originSpinner = findViewById(R.id.activity_main_origin);
        final String [] origins = {
                "Lisbon", "Madrid", "Paris", "London", "Brussels", "The Hague"};
        final ArrayAdapter<String> originsAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, origins);
        originSpinner.setAdapter(originsAdapter);

        // bind to destinations spinner, and initialize with a few other cities
        destinationSpinner = findViewById(R.id.activity_main_destination);
        final String [] destinations = {
                "Rome", "Berlin", "Prague", "Zurich", "Vienna", "Nicosia"};
        final ArrayAdapter destinationsAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, destinations);
        destinationSpinner.setAdapter(destinationsAdapter);
    }

    private String getUrl() {
        final String origin = originSpinner.getSelectedItem().toString();
        final String destination = destinationSpinner.getSelectedItem().toString();
        try {
            return "https://maps.googleapis.com/maps/api/distancematrix/json?origins="
                    + URLEncoder.encode(origin, "UTF-8") + "&destinations="
                    + URLEncoder.encode(destination, "UTF-8");
        } catch (UnsupportedEncodingException uee) {
            throw new RuntimeException(uee);
        }
    }

    public void connect(View view) {
        final String url = getUrl();
        urlTextView.setText(url);
        new DoHttpGetTask().execute(url);
    }

    public String doHttpGet(final String urlAddress)
            throws IOException {
        // must be declared here so it can be ’finally’ closed
        InputStream inputStream = null;

        try {
            URL url = new URL(urlAddress);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET"); // this is the default anyway
            conn.setDoInput(true); // connections can be used for input or output
            conn.connect(); // connects and starts the query
            int response = conn.getResponseCode(); // should be 200 if all is OK
            Log.d(TAG, "The response code is: " + response);
            inputStream = conn.getInputStream();

            // handle response (which can be accessed via the ‘inputStream’)
            final BufferedReader reader = new BufferedReader(
                    new InputStreamReader(inputStream, "utf-8"), 8);
            // the string builder is used to read all the bytes into a single string
            final StringBuilder stringBuilder = new StringBuilder();
            String line; // used as a temporary buffer
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
            return stringBuilder.toString();// this contains the full reply
        } finally {
            // makes sure that the InputStream is closed after we are finished using it
            if (inputStream != null) { inputStream.close(); }
        }
    }

private class DoHttpGetTask
        extends AsyncTask<String, Void, String> {
    @Override
    protected String doInBackground(String... urls) {
        // executes in background, on separate thread
        // cannot edit the UI
        try {
            // call the method that connects and fetches the data and return the reply
            return doHttpGet(urls[0]);
        } catch (IOException ioe) {
            // in case of I/O error, return that message instead
            return "Error: " + ioe.getMessage();
        }
    }
    @Override
    protected void onPostExecute(String result) {
        // executes after the thread finishes
        // can edit the UI
        resultTextView.setText(result);
    }
}

}