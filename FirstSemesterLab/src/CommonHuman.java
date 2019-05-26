public interface CommonHuman {
    String getName();
    boolean checkMood(Mood CurrentMood);
    void setMood(Mood mood);
    void setNeutralMood();
    int getMoneyAmount();
    boolean changeMoney(int delta);
}