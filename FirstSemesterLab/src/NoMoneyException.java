public class NoMoneyException extends Exception {
    private String name;
    public NoMoneyException(){
        super("ДЕНЕГ не хватило.");
    }
    public NoMoneyException(String name){
        this.name = name;
    }
    @Override
    public String getMessage() {
        return String.format("Персонажу %s не хватает денег для покупки акций\n", this.name);
    }
}
