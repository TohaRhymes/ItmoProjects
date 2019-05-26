public class BourseOverflowException extends RuntimeException {
    private Shares shares;
    int summaShares;
    private Businessman b1;
    private Businessman b2;
    private Businessman b3;
    public BourseOverflowException(){
        super("Произошла неизвестная ошибка на бирже........... OMG.");
    }
    public BourseOverflowException(Shares shares, Businessman b1, Businessman b2, Businessman b3){
        this.shares = shares;
        this.b1=b1;
        this.b2=b2;
        this.b3=b3;
        this.summaShares=b1.getSharesAmount()+b2.getSharesAmount()+b3.getSharesAmount();
    }
    @Override
    public String getMessage() {
        return String.format("О НЕТ! К сожалению, количество акций в обороте превысило 127,\nа программу, отвечающую за куплепродажу акций \"%s\" писал неопытный программист,\nпоэтому тип переменной, считающей количество акций в обороте, был byte.\n\nДавилонская биржа разрушилась, прям как Вавилонская башня((( \n\nМы смогли восстановить последние операции и посчитали вручную, у кого сколько акций:\nВсего в ходу акций \"%s\": %d. Из них у жителя %s акций: %d, у жителя %s акций: %d, а у жителя %s акций: %d.\n\n", shares.getName(), shares.getName(), summaShares, b1.name, b1.getSharesAmount(), b2.name, b2.getSharesAmount(), b3.name, b3.getSharesAmount());
    }
}
