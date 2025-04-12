@Test
public void testAggregateStats() throws Exception {
    List<Row> rows = new ArrayList<>();
    rows.add(new Row().add("size", "1MB").add("time", "2s"));
    rows.add(new Row().add("size", "512KB").add("time", "500ms"));

    String[] recipe = {
        "aggregate-stats :size :time total_size_mb total_time_sec"
    };
    List<Row> result = TestingRig.execute(recipe, rows);
    assertEquals(1, result.size());
    assertEquals(1.5, (double) result.get(0).getValue("total_size_mb"), 0.001);
    assertEquals(2.5, (double) result.get(0).getValue("total_time_sec"), 0.001);
}
