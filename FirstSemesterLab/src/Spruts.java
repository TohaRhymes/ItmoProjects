public class Spruts extends Businessman{
    public Tentacles tentacles = new Tentacles ();
    private double invisibility = 1.0;
    public Spruts (){
        super();
    }
    public Spruts (String name, int money, int sharesAmount){
        super(name, money, sharesAmount);
    }
    //InnerClass
    public class Tentacles{
        private double length;
        private boolean tire;
        public Tentacles(){
            this.length = 3;
            this.tire = false;
        }
        public double getLength() {
            return length;
        }
        public void setLength(double length) {
            this.length = length;
        }
        public boolean StealShares(Businessman man){
            if(this.tire && length!=0 && Math.random() / length >0.1){
                System.out.printf("Щупальца персонажа %s очень устали и он не смог дотянуться ими до карманана акционера по имени %s.\n", Spruts.this.name, man.name);
                length+=1;
                tire=false;
                return false;
            }else {
                if (invisibility <= 0.4) invisibility *= 3.0;
                int delta = (int) ((double) man.sharesAmount * invisibility * 0.4);
                if (Math.random() * 2 * invisibility > 0.2 && delta != 0) {
                    invisibility /= 2.0;
                    man.sharesAmount -= delta;
                    Spruts.this.sharesAmount += delta;
                    System.out.printf("Персонажу %s, используя свои гигантские щупальца, удалось украсть у персонажа %s акции в размере %d штук! Страсти накаляются!", Spruts.this.name, man.name, delta);
                    tire=true;
                    length*=0.5;
                    return true;
                }
            }
            tire=false;
            System.out.printf("Персонаж %s был засечен при попытке кражи у персонажа %s акции! Ему выражен общественный дизреспект!\n", Spruts.this.name, man.name);
            return false;
        }
    }
}
