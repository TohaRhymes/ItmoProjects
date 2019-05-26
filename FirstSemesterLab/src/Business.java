public interface Business{
    int getSharesAmount();
    void buyShares (Shares shares, int amount) throws Exception;
    boolean sellShares(Shares shares, int amount);
}
