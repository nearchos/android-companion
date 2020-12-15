package io.github.nearchos.smarthomeswitch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private View screen; // the parent layout, corresponding to the whole screen
    private TextView textView;

    private DatabaseReference smartHomeDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        screen = findViewById(R.id.screen);
        textView = findViewById(R.id.textView);

        final ToggleButton switchOnOff = findViewById(R.id.switchOnOff);
        switchOnOff.setOnCheckedChangeListener((buttonView, isChecked) -> lightsOnOff(isChecked));

        // initialize firebase RT DB
        final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        smartHomeDatabaseReference = firebaseDatabase.getReference().child("smart-home").child("Living Room");
        smartHomeDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final Boolean onOff = snapshot.getValue(Boolean.class);
                switchOnOff.setChecked(onOff != null && onOff);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("mad-book", "error: " + error);
                throw error.toException();
            }
        });
    }

    private void lightsOnOff(final boolean onOff) {
        screen.setBackgroundColor(onOff ? Color.WHITE : Color.BLACK);
        textView.setTextColor(onOff ? Color.BLACK : Color.WHITE);
        smartHomeDatabaseReference.setValue(onOff);
    }
}