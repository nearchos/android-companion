//package io.github.nearchos.archcomp.latest_iss_positions;
//
//import androidx.room.ColumnInfo;
//import androidx.room.Entity;
//import androidx.room.PrimaryKey;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.Date;
//
//import static io.github.nearchos.archcomp.iss_position.IssPosition.SIMPLE_DATE_FORMAT;
//
///**
// * @author Nearchos
// * Created: 14-Jan-20
// */
//@Entity(tableName = "iss_positions")
//public class IssPositionEntity {
//
//    public IssPositionEntity() {
//        super();
//    }
//
//    public IssPositionEntity(final JSONObject issNowJsonObject) throws JSONException {
//        this.timestamp = issNowJsonObject.getLong("timestamp");
//        final JSONObject issPositionJsonObject = issNowJsonObject.getJSONObject("iss_position");
//        this.latitude = issPositionJsonObject.getDouble("latitude");
//        this.longitude = issPositionJsonObject.getDouble("longitude");
//    }
//
//    @PrimaryKey
//    public long timestamp;
//
//    @ColumnInfo(name = "lat")
//    public double latitude;
//
//    @ColumnInfo(name = "lng")
//    public double longitude;
//
//    @Override
//    public String toString() {
//        return latitude + ", " + longitude + " (" + SIMPLE_DATE_FORMAT.format(new Date(timestamp * 1000)) + ")";
//    }
//}