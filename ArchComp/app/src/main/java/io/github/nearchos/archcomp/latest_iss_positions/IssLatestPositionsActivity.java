//package io.github.nearchos.archcomp.latest_iss_positions;
//
//import android.os.Bundle;
//import android.view.View;
//import android.widget.SeekBar;
//import android.widget.TextView;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.coordinatorlayout.widget.CoordinatorLayout;
//
//import com.google.android.material.snackbar.Snackbar;
//
//import java.util.Vector;
//
//import io.github.nearchos.archcomp.AppDatabase;
//import io.github.nearchos.archcomp.NetworkPolling;
//import io.github.nearchos.archcomp.R;
//
//public class IssLatestPositionsActivity extends AppCompatActivity {
//
//    private CoordinatorLayout rootCoordinatorLayout;
//    private TextView textViewIssPosition;
//    private SeekBar seekBar;
//
//    private AppDatabase db;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_iss_latest_positions);
//
//        rootCoordinatorLayout = findViewById(R.id.rootCoordinatorLayout);
//        textViewIssPosition = findViewById(R.id.textViewLatestIssPositions);
//        seekBar = findViewById(R.id.seekBar);
//        seekBar.setOnSeekBarChangeListener(new MyOnSeekBarChangeListener());
//
//        db = AppDatabase.getDatabase(getApplicationContext());
//
//        getLifecycle().addObserver(new NetworkPolling(this));
//
//        db.githubApiDao().getLatestIssPositions().observe(this, issPositionEntities -> {
//            final int progress = seekBar.getProgress();
//            final int numOfIssPositionEntities = issPositionEntities.size();
//            this.issPositionEntities.clear();
//            this.issPositionEntities.addAll(issPositionEntities);
//            final int max = numOfIssPositionEntities - 1;
//            seekBar.setEnabled(max > -1);
//            seekBar.setMax(max);
//            seekBar.setProgress(Math.min(progress, numOfIssPositionEntities - 1));
//            Snackbar.make(rootCoordinatorLayout, "progress: " + progress + ", max: " + max, Snackbar.LENGTH_SHORT).show();
//            updateSelectedIssPosition(seekBar.getProgress());
//        });
//
//        updateSelectedIssPosition(0);
//    }
//
//    public void clear(View view) {
//        db.githubApiDao().deleteAll();
//    }
//
//    private final Vector<IssPositionEntity> issPositionEntities = new Vector<>();
//
//    private void updateSelectedIssPosition(final int index) {
//        if(issPositionEntities.isEmpty()) {
//            textViewIssPosition.setText("empty");
//        } else {
//            final IssPositionEntity selectedIssPositionEntity = issPositionEntities.get(index);
//            textViewIssPosition.setText(selectedIssPositionEntity.toString());
//        }
//    }
//
//    public class MyOnSeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {
//
//        @Override
//        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//            updateSelectedIssPosition(seekBar.getProgress());
//        }
//
//        @Override
//        public void onStartTrackingTouch(SeekBar seekBar) {
//            // do nothing
//        }
//
//        @Override
//        public void onStopTrackingTouch(SeekBar seekBar) {
//            // do nothing
//        }
//    }
//}