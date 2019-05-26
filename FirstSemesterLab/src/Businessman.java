public abstract class Businessman extends Human implements Business{
    protected int sharesAmount;
    public Businessman (){
        this.name = "Безымянный";
        this.money = 0;
        this.sharesAmount = 0;
    }
    public Businessman (String name, int money, int sharesAmount){
        this.name = name;
        this.money = money;
        this.sharesAmount = sharesAmount;
    }
    public int getSharesAmount(){
        return this.sharesAmount;
    }
    public void buyShares(Shares shares, int amount) throws NoMoneyException{
        //здесь можем выкинуть ошибку нехватки денег
        if(amount*shares.getPrice()>this.getMoneyAmount()){
            throw new NoMoneyException(this.name);
        }else{
            this.sharesAmount+=amount;
            System.out.println(this.name+" купил акции, количеством "+amount+" шт.");
        }
    }
    public boolean sellShares(Shares shares, int amount){
        if(this.sharesAmount>=amount&&this.changeMoney(shares.price*amount)){
            this.sharesAmount-=amount;
            return true;
        }
        return false;
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

}
