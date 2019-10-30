package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import status.PurchaseStatus;
import status.TransactionStatus;

@AllArgsConstructor
public class Purchase {

    private final long id;
    @Getter
    private PurchaseStatus status;
    @Getter
    private TransactionStatus transactionStatus;
}
