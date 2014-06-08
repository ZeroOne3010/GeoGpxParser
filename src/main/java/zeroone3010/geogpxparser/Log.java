package zeroone3010.geogpxparser;

import java.time.LocalDateTime;

public class Log {
    private long id;
    private String user;
    private LocalDateTime date;
    private LogType type;
    private String text;

    public long getId() {
        return id;
    }

    public Log setId(long id) {
        this.id = id;
        return this;
    }

    public String getUser() {
        return user;
    }

    public Log setUser(String user) {
        this.user = user;
        return this;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public Log setDate(LocalDateTime date) {
        this.date = date;
        return this;
    }

    public LogType getType() {
        return type;
    }

    public Log setType(LogType type) {
        this.type = type;
        return this;
    }

    public String getText() {
        return text;
    }

    public Log setText(String text) {
        this.text = text;
        return this;
    }
}
