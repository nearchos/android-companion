package io.github.nearchos.justchat;

import androidx.annotation.NonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

class Message implements Comparable<Message> {

    private String timestamp;
    private String from;
    private String message;

    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS", Locale.ENGLISH);
    public static final SimpleDateFormat HUMAN_DATE_FORMAT = new SimpleDateFormat("E hh:mm", Locale.ENGLISH);

    public Message() {} // empty constructor required for automatic inflating of data from Firebase

    public Message(String timestamp, String from, String message) {
        this.timestamp = timestamp;
        this.from = from;
        this.message = message;
    }

    public Message(final long timestampAsLong, String from, String message) {
        this(DATE_FORMAT.format(new Date(timestampAsLong)), from, message);
    }

    @com.google.firebase.database.Exclude
    public long getTime() {
        try {
            return DATE_FORMAT.parse(timestamp).getTime();
        } catch (ParseException pe) {
            throw new RuntimeException(pe);
        }
    }

    @com.google.firebase.database.Exclude
    public String getHumanTime() {
        return HUMAN_DATE_FORMAT.format(new Date(getTime()));
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getFrom() {
        return from;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message1 = (Message) o;
        return Objects.equals(timestamp, message1.timestamp) &&
                Objects.equals(from, message1.from) &&
                Objects.equals(message, message1.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timestamp, from, message);
    }

    @Override
    public int compareTo(final Message other) {
        return timestamp.compareTo(other.timestamp);
    }

    @NonNull
    @Override
    public String toString() {
        return String.format(Locale.ENGLISH, "[%s] %s -> %s", timestamp, from, message);
    }
}