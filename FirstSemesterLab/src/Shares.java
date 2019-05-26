public class Shares{
    protected String name;
    protected int price;
    private int[] delta;
    public Shares (String name, int price){
        this.name = name;
        this.price = price;
        this.delta = new int[10];
        for(int i=0; i<delta.length; i++) delta[i]=0;
    }
    public String getName(){
        return name;
    }
    public int getPrice(){
        return this.price;
    }
    public boolean changePrice(int delta){
        if(delta>0&&Integer.MAX_VALUE-delta<price||delta<0&&price<Math.abs(delta)){
            System.out.println("Сегодня биржа закрылась слишком рано, и цена акций так и осталась равной "+this.price+"￥.");
            return false;
        }
        if (delta<0){
            System.out.println("Цена акций увеличилась на "+delta+", и стала равной "+this.price+"￥.");
        }
        if (delta>0){
            System.out.println("Цена акций уменьшилась на "+Math.abs(delta)+", и стала равной "+this.price+"￥.");
        }
        this.price+=delta;
        return true;
    }
    public boolean changeInPercent(int percent){
        int temp = (int) ((double) this.price * (1.0 + (double) percent / 100));
        if (temp <= 0 || temp == Integer.MAX_VALUE) {
            System.out.println("Сегодня биржа закрылась слишком рано, и цена акций так и осталась равной "+this.price+"￥.");
            return false;
        }
        System.out.println("Цена акций стала равной "+this.price+"￥.");
        for(int i=delta.length-1; i>0; i--){
            delta[i]=delta[i-1];
        }
        delta[0] = temp-this.price;
        this.price = temp;
        return true;
    }
    public int totalDelta(){
        int fuldelta=0;
        for(int i=0; i<delta.length; i++){
            fuldelta+=delta[i];
        }
        return fuldelta;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Shares other = (Shares) obj;
        if (price != other.price)
            return false;
        if(!delta.equals(other.price))
            return false;
        if (!name.equals(other.name))
            return false;
        return true;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + price;
        return result;
    }
    @Override
    public String toString() {
        return name+", акции";
    }
}
