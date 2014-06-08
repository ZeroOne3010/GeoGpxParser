package zeroone3010.geogpxparser;

import org.joda.time.DateTime;

public class Log {
    private long id;
    private String user;
    private DateTime date;
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

    public DateTime getDate() {
        return date;
    }

    public Log setDate(DateTime date) {
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
