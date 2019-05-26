public class Bourse {
    //Инициализация объектов
    Scooperfield scooperfield;
    Spruts spruts;
    Gadkinz gadkinz;
    Seller seller;
    Shares shares;
    Spruts.Tentacles tentacles;
    Scooperfield.Loudmouth loudmouth;
    public void setScooperfield(String name, int money, int shares){
        scooperfield = new Scooperfield (name, money, shares);
        loudmouth = scooperfield.new Loudmouth();
    }
    public void setSpruts(String name, int money, int shares){
        spruts = new Spruts (name, money, shares);
        tentacles = spruts.new Tentacles();
    }
    public void setGadkinz(String name, int money, int shares){
        gadkinz = new Gadkinz (name, money, shares);
    }
    public void setSeller(String name, int money){
        seller = new Seller (name, money);
    }
    public void setShares(String name, int price){
        shares = new Shares (name, price);
    }
    //Полезные методы для задачи общих параметров, используются в LiveGo
    protected void setMoodAll(Mood mood){
        scooperfield.setMood(mood);
        spruts.setMood(mood);
        gadkinz.setMood(mood);
        seller.setMood(mood);
    }
    protected void changeAllMoney(int a){
        scooperfield.changeMoney(a);
        spruts.changeMoney(a/2);
        gadkinz.changeMoney(a);
        seller.changeMoney(a);
    }
    // Здесь происходит взаимодействие

    public void LiveGo(){
        System.out.println("Сейчас мы поведаем тебе огромную тайну учебы в ИТМО (просто ботай прогу)))\nНуууу, или иди в экономический ВУЗ. Но в экономике все очень сложно...\nСейчас покажем сложные экономические связи на примере жителей Цветочного города,\nиз которого родом сам Незнайка. В этот раз действие происходит\nв канун Нового года.\n\nЕсть 4 персонажа:");
        System.out.printf("1)Манипулятор %s Скуперфильд, на данный момент у него сантиков(￥): %d, а акций: %d.\n", scooperfield.name, scooperfield.money, scooperfield.sharesAmount);
        System.out.printf("2)Бизнесмен %s Спрутс, имеет очень опасные щупальца. На данный момент у него сантиков(￥): %d, а акций: %d.\n", spruts.name, spruts.money, spruts.sharesAmount);
        System.out.printf("3)Владелец местной газеты, %s Гадкинз, на данный момент у него сантиков(￥): %d.\n", gadkinz.name, gadkinz.money);
        System.out.printf("4)Продавец акций, уважаемый %s , на данный момент у него сантиков(￥): %d.\n\n", seller.name, seller.money);
        gadkinz.setNews(Newspaper.TENTACLES, shares);
        scooperfield.setMood(Mood.FEAR);
        gadkinz.setMood(Mood.FEAR);
        boolean bourseOK = true;
        int i=0;
        do {
            try {
                i++;
                System.out.println("\n\nПошел " + i + "-й день.");
                if (gadkinz.newspaper == Newspaper.TENTACLES) {
                    boolean checkstole = false;
                    if (Math.random() > 0.7) {
                        if (tentacles.StealShares(scooperfield)) {
                            scooperfield.setMood(Mood.FEAR);
                            spruts.setMood(Mood.JOY);
                            scooperfield.changeOppMood(gadkinz, Mood.HOPE);
                        } else {
                            checkstole = true;
                        }
                    } else {
                        if (tentacles.StealShares(gadkinz)) {
                            gadkinz.setMood(Mood.FEAR);
                            spruts.setMood(Mood.JOY);
                            scooperfield.setMood(Mood.HOPE);
                        } else {
                            checkstole = !checkstole;
                        }
                    }
                    if (checkstole || Math.random() < 0.9) scooperfield.changeOppMood(spruts, Mood.FEAR);
                    if (Math.random() < 0.5) {
                        gadkinz.setNews(Newspaper.SHARESDROP, shares);
                    }
                } else if (gadkinz.newspaper == Newspaper.SHARESRAISE) {
                    int deltaShares = (int) (Math.random() * 20.0 - 15);
                    if (deltaShares != 0) {
                        seller.changethePrise(shares, deltaShares);
                        if (deltaShares < 0) {
                            setMoodAll(Mood.JOY);
                            gadkinz.setNews(Newspaper.SHARESRAISE, shares);
                        } else {
                            setMoodAll(Mood.NEUTRAL);
                        }
                    }
                } else if (gadkinz.newspaper == Newspaper.SHARESDROP) {
                    //AnonClass
                    Businessman rich = new Businessman("Тефтель", 0, (int) Math.round(Math.random() * 4)) {
                        @Override
                        public boolean sellShares(Shares shares, int amount) {
                            double a = Math.random();
                            if (a < 0.38) {
                                gadkinz.sharesAmount += amount;
                                this.sharesAmount = 0;
                                System.out.printf("Богач %s продал все свои акции персонажу %s.\n", this.name, gadkinz.name);
                            } else if (a > 0.62) {
                                scooperfield.sharesAmount += amount;
                                this.sharesAmount = 0;
                                System.out.printf("Богач %s продал все свои акции персонажу %s.\n", this.name, scooperfield.name);
                            } else {
                                spruts.sharesAmount += amount;
                                this.sharesAmount = 0;
                                System.out.printf("Богач %s был вынужден продать все свои акции персонажу %s.\n", this.name, spruts.name);
                            }
                            return true;
                        }
                    };
                    rich.sellShares(shares, rich.sharesAmount);
                    //здесь ловим ошибку при покупке Акций
                    if (gadkinz.money / shares.price != 0){
                        try{
                            gadkinz.buyShares(shares, (Math.random() > 0.1 ? spruts.money : spruts.money+100 ) / shares.price);
                        }catch(Exception ee){

                        }
                    }
                    if (spruts.money / shares.price != 0){
                        try{
                            spruts.buyShares(shares, (Math.random() > 0.2 ? spruts.money : spruts.money+100 )/ shares.price);
                        }catch(Exception ee){

                        }
                    }
                    if (scooperfield.money / shares.price != 0){
                        loudmouth.startBuy(shares);
                    }
                    changeAllMoney(27);
                } else if (gadkinz.newspaper == Newspaper.SADNEWS) {
                    setMoodAll(Mood.FEAR);
                    if (Math.random() > 0.5) {
                        seller.changethePrise(shares, (int) ((-1) * (Math.random() * 5.0 + 1.0)));
                    }
                }
                if (scooperfield.getMood() != Mood.RUSH && scooperfield.getMood() != Mood.FEAR) {
                    gadkinz.setNews(Newspaper.TENTACLES, shares);
                    Mood sc = scooperfield.getMood();
                    setMoodAll(Mood.FEAR);
                    if (Math.random() < 0.3) scooperfield.setMood(sc);
                } else {
                    double random = Math.random();
                    if (random < 0.25 && gadkinz.getMood() == Mood.FEAR) {
                        gadkinz.setNews(Newspaper.SADNEWS, shares);
                    } else if (random < 0.4) {
                        gadkinz.setNews(Newspaper.SHARESRAISE, shares);
                    } else {
                        gadkinz.setNews(Newspaper.SHARESDROP, shares);
                        changeAllMoney((int) (random * (-100.0)));
                        scooperfield.changeMoney((int) (random * (-100.0)));
                    }
                }
                if (shares.price <= 2) {
                    int delta = (int) (Math.random() * 5 + 2);
                    System.out.println("Что-то неладное случилось в городе, но цена акций воросла на " + delta + "￥.");
                }
                if (gadkinz.getSharesAmount() + spruts.getSharesAmount() + scooperfield.getSharesAmount() < 128) {
                    System.out.printf("На данный момент в ходу акций: %d. Из них у жителя %s акций: %d, у жителя %s акций: %d, а у жителя %s акций: %d.\n\n", spruts.getSharesAmount()+gadkinz.getSharesAmount()+spruts.getSharesAmount(), scooperfield.name, scooperfield.getSharesAmount(), spruts.name, spruts.getSharesAmount(), gadkinz.name, gadkinz.getSharesAmount());
                } else {
                    throw new BourseOverflowException(shares, spruts, gadkinz, scooperfield);
                }
            }catch (BourseOverflowException ee) {
                System.out.flush();
                bourseOK=false;
                ee.printStackTrace();
            }
        } while(bourseOK);
    }
}
