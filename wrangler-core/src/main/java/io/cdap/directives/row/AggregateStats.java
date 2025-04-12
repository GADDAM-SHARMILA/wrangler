@Directive(type = Directive.Type.AGGREGATE)
public class AggregateStats implements Directive {
    private String sizeCol, timeCol, sizeOut, timeOut;

    public UsageDefinition define() {
        return UsageDefinition.builder("aggregate-stats")
                .define("sizeCol", TokenType.COLUMN_NAME)
                .define("timeCol", TokenType.COLUMN_NAME)
                .define("sizeOut", TokenType.COLUMN_NAME)
                .define("timeOut", TokenType.COLUMN_NAME)
                .build();
    }

    public void initialize(Arguments args) {
        sizeCol = ((ColumnName) args.value("sizeCol")).value();
        timeCol = ((ColumnName) args.value("timeCol")).value();
        sizeOut = ((ColumnName) args.value("sizeOut")).value();
        timeOut = ((ColumnName) args.value("timeOut")).value();
    }

    public List<Row> execute(List<Row> rows, ExecutorContext ctx) {
        long totalBytes = 0, totalTime = 0;
        for (Row row : rows) {
            totalBytes += new ByteSize(row.getValue(sizeCol).toString()).getBytes();
            totalTime += new TimeDuration(row.getValue(timeCol).toString()).getMilliseconds();
        }
        Row result = new Row();
        result.add(sizeOut, totalBytes / (1024.0 * 1024.0)); // MB
        result.add(timeOut, totalTime / 1000.0); // seconds
        return Collections.singletonList(result);
    }
}
