//package io.github.nearchos.archcomp.iss_position;
//
//import androidx.annotation.NonNull;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.Locale;
//
//public class IssPosition {
//
//    public static final SimpleDateFormat SIMPLE_DATE_FORMAT
//            = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'.000Z'", Locale.US);
//
//    private long timestamp;
//    private double latitude;
//    private double longitude;
//
//    public IssPosition(final JSONObject issNowJsonObject) throws JSONException {
//        this.timestamp = issNowJsonObject.getLong("timestamp");
//        final JSONObject issPositionJsonObject = issNowJsonObject.getJSONObject("iss_position");
//        this.latitude = issPositionJsonObject.getDouble("latitude");
//        this.longitude = issPositionJsonObject.getDouble("longitude");
//    }
//
//    public long getTimestamp() {
//        return timestamp;
//    }
//
//    public double getLatitude() {
//        return latitude;
//    }
//
//    public double getLongitude() {
//        return longitude;
//    }
//
//    @NonNull
//    @Override
//    public String toString() {
//        return latitude + ", " + longitude + " (" + SIMPLE_DATE_FORMAT.format(new Date(timestamp * 1000)) + ")";
//    }
//}