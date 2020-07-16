package io.github.zeroone3010.geogpxparser;

import java.time.LocalDateTime;

public final class Log {
    private final long id;
    private final String user;
    private final LocalDateTime date;
    private final LogType type;
    private final String text;

    private Log(final long id, final String user, final LocalDateTime date, final LogType type, final String text) {
        this.id = id;
        this.user = user;
        this.date = date;
        this.type = type;
        this.text = text;
    }

    public long getId() {
        return id;
    }

    public String getUser() {
        return user;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public LogType getType() {
        return type;
    }

    public String getText() {
        return text;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private long id;
        private String user;
        private LocalDateTime date;
        private LogType type;
        private String text;

        public Builder id(long id) {
            this.id = id;
            return this;
        }

        public Builder user(String user) {
            this.user = user;
            return this;
        }

        public Builder date(LocalDateTime date) {
            this.date = date;
            return this;
        }

        public Builder type(LogType type) {
            this.type = type;
            return this;
        }

        public Builder text(String text) {
            this.text = text;
            return this;
        }

        public Log build() {
            return new Log(id, user, date, type, text);
        }
    }
}
