//package io.github.nearchos.archcomp.iss_position;
//
//import android.os.Bundle;
//import android.util.Log;
//import android.widget.TextView;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.lifecycle.ViewModelProvider;
//
//import io.github.nearchos.archcomp.NetworkPolling;
//import io.github.nearchos.archcomp.R;
//
//public class IssPositionActivity extends AppCompatActivity {
//
//    private TextView textViewIssPosition;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_iss_position);
//
//        textViewIssPosition = findViewById(R.id.textViewIssPosition);
//
//        final IssPositionViewModel issPositionViewModel = new ViewModelProvider(this).get(IssPositionViewModel.class);
//        issPositionViewModel.getIssPosition().observe(this, issPosition -> {
//            Log.d("mad", "showing issPosition: " + issPosition);
//            textViewIssPosition.setText(issPosition.toString());
//        });
//
//        getLifecycle().addObserver(new NetworkPolling(this));
//    }
//}