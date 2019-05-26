import java.util.HashSet;

public class LauncherBourse {

    public static void main(String[] args) {

        Bourse b = new Bourse();
        b.setScooperfield("Дед Мороз", 200, 5);
        b.setSpruts("Гринч", 20,1);
        b.setGadkinz("Гном", 90,5);
        b.setSeller("Снегурочка", 80);
        b.setShares("Подарки", 15);
        b.LiveGo();


    }
}
