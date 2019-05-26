/**
 * @author Anton Changalidi
 */
public class Gadkinz extends Businessman implements Comparable<Gadkinz> {
    public Newspaper newspaper;

    public Gadkinz() {
        super();
    }

    public Gadkinz(String name, int money, int sharesAmount) {
        super(name, money, sharesAmount);
    }

    public boolean checkNews(Newspaper news) {
        if (this.newspaper == news) return true;
        return false;
    }

    public void setNews(Newspaper news, Shares shares) {
        this.newspaper = news;
        switch (news) {
            case SADNEWS:
                if (Math.random() < 0.5) {
                    System.out.println("НОВОСТИ: Великая депрессия наступает в наших краях!");
                } else {
                    System.out.println("НОВОСТИ: Грусть-печаль съедает город!");
                }
                break;
            case TENTACLES:
                System.out.println("НОВОСТИ: ШОК! Гигантские щупальца Спрутса начинают воровать акции у Жителей города.");
                break;
            case SHARESDROP:
                int a = (int) (Math.random() * (-7) + 1);
                shares.changeInPercent(a);
                System.out.println("Новый выпуск газеты потряс город, и цена акций упала на " + Math.abs(a) + "%.");
                break;
            case SHARESRAISE:
                int b = (int) (Math.random() + 1) * (4);
                shares.changeInPercent(b);
                System.out.println("Новый выпуск газеты воодушевил город и акции поднялись в цене на " + b + "%.");
                break;
        }
    }

    @Override
    public void buyShares(Shares shares, int amount) throws BourseOverflowException {
        if (amount * shares.getPrice() > this.getMoneyAmount()) {
            throw new BourseOverflowException();
        } else {
            this.sharesAmount += amount;
            System.out.println(this.name + " купил акции, количеством " + amount + " шт.");
        }
    }

    @Override
    public int compareTo(Gadkinz g) {
        Integer sharesA = this.sharesAmount;
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
