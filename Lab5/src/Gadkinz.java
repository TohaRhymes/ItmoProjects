/**
 * @author Anton Changalidi
 */
public class Gadkinz extends Businessman implements Comparable<Gadkinz> {

    public Gadkinz() {
        super();
    }

    public Gadkinz(String name, int money, int sharesAmount) {
        super(name, money, sharesAmount);
    }




    @Override
    public int compareTo(Gadkinz g) {
        Integer sharesA = this.getSharesAmount();
        Integer sharesB = g.getSharesAmount();
        int result = sharesA.compareTo(sharesB);
        if (result == 0) {
            Integer moneyA = this.getMoneyAmount();
            Integer moneyB = g.getMoneyAmount();
            result = moneyA.compareTo(moneyB);
        }
        if (result == 0) {
            result = this.name.compareTo(g.name);
        }
        return result;
    }

    @Override
    public String toString() {
        return "{\"name\":\"" + name +
                "\", \"money\":" + money +
                ", \"shares\":" + sharesAmount +
                '}';
    }
}
