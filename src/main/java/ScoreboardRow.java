public record ScoreboardRow(String lhs, String rhs) {
    public static ScoreboardRow empty() {
        return new ScoreboardRow("", "");
    }
}
