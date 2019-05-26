public abstract class Businessman implements Business{
    protected int sharesAmount;
    protected String name;
    protected int money;
    protected Mood mood;

    public String getName(){
        return name;
    }

    public Businessman (String name, int money, int sharesAmount){
        this.name = name;
        this.money = money;
        this.sharesAmount = sharesAmount;
    }
    public int getSharesAmount(){
        return this.sharesAmount;
    }
    public int getMoneyAmount(){
        return this.sharesAmount;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Businessman other = (Businessman) obj;
        if (!name.equals(other.name))
            return false;
        if (money != other.money)
            return false;
        if (!mood.equals(other.mood))
            return false;
        if (sharesAmount != other.sharesAmount)
            return false;
        return true;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + money;
        result = prime * result + ((mood == null) ? 0 : mood.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + sharesAmount;
        return result;
    }
    @Override
    public String toString() {
        return name+", homo sapiens";
    }
    public Businessman (){
        this.name = "Безымянный";
        this.money = 0;
        this.sharesAmount = 0;
    }

}
