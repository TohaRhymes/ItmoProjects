public abstract class Human implements CommonHuman {
    protected String name;
    protected int money;
    protected Mood mood;
    public Human (){
        this.name = "Безымянный";
        this.money = 0;
        this.mood = Mood.NEUTRAL;
    }
    public Human (String name, int money){
        this.name = name;
        this.money = money;
        this.mood = Mood.NEUTRAL;
    }
    public String getName(){
        return name;
    }
    public boolean checkMood(Mood CurrentMood){
        if(this.mood==CurrentMood) return true;
        return false;
    }
    public void setMood(Mood mood){
        this.mood = mood;
    }
    public Mood getMood(){
        return this.mood;
    }
    public void setNeutralMood(){
        this.mood = Mood.NEUTRAL;
    }
    public int getMoneyAmount(){
        return money;
    }
    public boolean changeMoney(int delta){
        if(delta>0&&Integer.MAX_VALUE-delta<money||delta<0&&money<Math.abs(delta)) return false;
        money+=delta;
        return true;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Human other = (Human) obj;
        if (!name.equals(other.name))
            return false;
        if (money != other.money)
            return false;
        if (!mood.equals(other.mood))
            return false;
        return true;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + money;
        result = prime * result+((mood == null) ? 0 : mood.hashCode());
        result = prime * result+((name == null) ? 0 : name.hashCode());
        return result;
    }
    @Override
    public String toString() {
        return name+", homo sapiens";
    }
}
