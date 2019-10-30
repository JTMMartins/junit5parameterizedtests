package service;

import model.Purchase;
import status.PurchaseStatus;
import status.TransactionStatus;

import java.util.Arrays;
import java.util.List;

public class PurchaseJob {

    public boolean process(final Purchase purchase) {

        if (purchase == null) throw new IllegalArgumentException("Purchase to process cannot be null");

        return PurchaseStatus.COMPLETED.equals(purchase.getStatus());
    }

    public List<Purchase> getPaidPurchases(){
        return Arrays.asList(
                new Purchase(1L, PurchaseStatus.COMPLETED, TransactionStatus.CHARGED),
                new Purchase(2L, PurchaseStatus.COMPLETED, TransactionStatus.CHARGED),
                new Purchase(3L, PurchaseStatus.COMPLETED, TransactionStatus.CHARGED)
        );
    }
}
