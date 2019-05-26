public class Scooperfield extends Businessman {
    public Loudmouth loudmouth = new Loudmouth();
    public Scooperfield (){
        super();
    }
    public Scooperfield (String name, int money, int sharesAmount){
        super(name, money, sharesAmount);
    }
    public void changeOppMood(Human human, Mood mood){
        human.setMood(mood);
        if (mood==Mood.FEAR) {
            System.out.println(this.name + " очень сильно испугал персонажа " + human.name + ".");
        }
        if (mood==Mood.NEUTRAL) {
            System.out.println(this.name + " сделал персонажа " + human.name + " абсолютно спокойным.");
        }
        if (mood==Mood.HOPE) {
            System.out.println(this.name + " дал надежду на светлое будущее персонажу "+human.name+".");
        }
        if (mood==Mood.JOY) {
            System.out.println(this.name + " доставил божественное наслаждение персонажу "+human.name+".");
        }
        if (mood==Mood.RUSH) {
            System.out.println(this.name + " заставил персонажа "+human.name+" ужасно спешить.");
        }
    }
    //InnerClass
    public class Loudmouth{
        private boolean tired;
        public Loudmouth(){
            tired=false;
        }
        public void startBuy(Shares shares){
            if (shares.totalDelta()<1&&!tired){
                int number = Scooperfield.this.money/shares.price;
                try{
                    Scooperfield.this.buyShares(shares, number);
                }catch(Exception ee){
                    System.out.printf("Горлодерикам персонажа %s не хватает денег для покупки акций\n", Scooperfield.this.name);
                }
                System.out.printf("Горлодерики cкупили за Скуперфильда %s акции в количестве: %d.\n", Scooperfield.this.name, number);
                tired=true;
            }else{
                System.out.printf("Горлодерики Скуперфильда %s устали, и не смогли скупить акции.\n", Scooperfield.this.name);
                tired=false;
            }
        }
    }
}
