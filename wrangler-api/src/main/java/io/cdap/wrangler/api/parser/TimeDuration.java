public class TimeDuration extends Token {
    private final long millis;
    public TimeDuration(String value) {
        super("TIME_DURATION", value);
        millis = parseTime(value);
    }

    private long parseTime(String value) {
        value = value.toLowerCase();
        double number = Double.parseDouble(value.replaceAll("[^0-9.]", ""));
        if (value.endsWith("s")) return (long)(number * 1000);
        if (value.endsWith("ms")) return (long)(number);
        if (value.endsWith("m")) return (long)(number * 60 * 1000);
        return 0;
    }

    public long getMilliseconds() {
        return millis;
    }
}
