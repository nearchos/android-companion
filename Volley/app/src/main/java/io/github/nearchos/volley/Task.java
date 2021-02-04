package io.github.nearchos.volley;

public class Task {

    private final int userId;
    private final int id;
    private final String title;
    private final boolean completed;

    public Task(int userId, int id, String title, boolean completed) {
        this.userId = userId;
        this.id = id;
        this.title = title;
        this.completed = completed;
    }

    public int getUserId() {
        return userId;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public boolean isCompleted() {
        return completed;
    }

    @Override
    public String toString() {
        return title + " - " + (completed ? "completed" : "pending");
    }
}