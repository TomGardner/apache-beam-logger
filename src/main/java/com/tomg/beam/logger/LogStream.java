package com.tomg.beam.logger;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * This may be better implemented as 'AutoValue' : https://github.com/google/auto/tree/master/value
 *
 */
public class LogStream {

    private String sourceIp;
    private String destinationIp;
    private LocalDateTime timestamp;
    private Integer bytes;
    private Integer sourcePort;
    private Integer destinationPort;
    private Boolean authorized;
    private Long logId;

    public LogStream() {}

    public LogStream(String sourceIp, String destinationIp, String timestamp, Integer bytes, Integer sourcePort, Integer destinationPort, Boolean authorized, Long logId) {
        this.sourceIp = sourceIp;
        this.destinationIp = destinationIp;
        this.timestamp = parseDateString(timestamp);
        this.bytes = bytes;
        this.sourcePort = sourcePort;
        this.destinationPort = destinationPort;
        this.authorized = authorized;
        this.logId = logId;
    }

    /**
     * This could probably be done a little better.
     *
     * @param timestamp could be passed as a long or a string: "2018-09-15T15:53:00"
     * @return
     */
    private LocalDateTime parseDateString(String timestamp) {
        try {
            return LocalDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong(timestamp)), ZoneId.systemDefault());
        } catch (NumberFormatException e) {
            if (timestamp.contains(":")) {
                return LocalDateTime.parse(timestamp, DateTimeFormatter.ISO_DATE_TIME);
            }
            return null;
        }
    }

    public String getSourceIp() {
        return sourceIp;
    }

    public String getDestinationIp() {
        return destinationIp;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public Integer getBytes() {
        return bytes;
    }

    public Integer getSourcePort() {
        return sourcePort;
    }

    public Integer getDestinationPort() {
        return destinationPort;
    }

    public Boolean getAuthorized() {
        return authorized;
    }

    public Long getLogId() {
        return logId;
    }

    public String toString() {
        String log = "{";
        log += "\"sourceIp\":\"" + this.sourceIp + "\"";
        log += ",\"destinationIp\":\"" + this.destinationIp + "\"";
        log += ",\"timestamp\":\"" + this.timestamp + "\"";
        log += ",\"bytes\":" + this.bytes;
        log += ",\"sourcePort\":" + this.sourcePort;
        log += ",\"destinationPort\":" + this.destinationPort;
        log += ",\"authorized\":" + this.authorized;
        log += ",\"logId\":" + this.logId;
        log += "}";
        return log;
    }
}
