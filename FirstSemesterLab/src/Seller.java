public class Seller extends Human {
    public Seller (){
        super();
    }
    public Seller (String name, int money){
        super(name,money);
    }
    public boolean changethePrise(Shares shares,int percent){
        if(percent==0){
            System.out.println("Продавец акций "+this.name+" решил не менять цену акций.");
            return false;
        }
        if(percent>0){
            System.out.println("Продавец акций "+this.name+" увеличил цену акций на "+percent+"%");
        }
        if(percent<0){
            System.out.println("Продавец акций "+this.name+" уменьшил цену акций на "+Math.abs(percent)+"%");
        }
        return shares.changeInPercent(percent);

    }
}
