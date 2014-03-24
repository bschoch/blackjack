package blackjack.model;

import org.codehaus.jackson.map.annotate.JsonSerialize;

public class Account {

    private static Integer idCount = 0;
    private Integer id = idCount++;
    private Integer balance;

    public Account(Integer balance) {
        this.balance = balance;
    }

    @JsonSerialize
    public Integer getId() {
        return id;
    }

    @JsonSerialize
    public Integer getBalance() {
        return balance;
    }

    public synchronized boolean adjustBalance(int adjust) {
        if (balance + adjust < 0) {
            return false;
        }
        this.balance += adjust;
        return true;
    }
}
