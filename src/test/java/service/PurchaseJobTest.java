package service;

import model.Purchase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import status.PurchaseStatus;
import status.TransactionStatus;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.EnumSource.Mode.EXCLUDE;


public class PurchaseJobTest {

    private static PurchaseJob purchaseJob = new PurchaseJob();

    static Stream<Purchase> purchaseStatusNegativeDataSet() {

        return Stream.of(
                new Purchase(1L, PurchaseStatus.CONFIRMED, TransactionStatus.NEW),
                new Purchase(2L, PurchaseStatus.FAILED, TransactionStatus.FAILEDCHARGE),
                new Purchase(3L, PurchaseStatus.PENDING, TransactionStatus.PENDINGCHARGE)
        );
    }

    @ParameterizedTest
    @MethodSource("purchaseStatusNegativeDataSet")
    void processPurchase_returnsFalse(final Purchase purchase) {

        Assertions.assertFalse(purchaseJob.process(purchase));
    }

    @ParameterizedTest
    @NullSource
    void processPurchase_withNullPurchase_throwsIllegalArgumentException(Purchase purchase) {

        Assertions.assertThrows(IllegalArgumentException.class, () -> purchaseJob.process(purchase));
    }

    @Test
    void processPurchase_returnsTrue() {

        final Purchase purchase = new Purchase(1L, PurchaseStatus.COMPLETED, TransactionStatus.CHARGED);

        Assertions.assertTrue(purchaseJob.process(purchase));
    }

    @ParameterizedTest
    @EnumSource(value = TransactionStatus.class, mode = EXCLUDE, names = {"CHARGED"})
    void paidPurchases_haveAllTransactionStatusAsCharged(final TransactionStatus transactionStatus) {

        List<Purchase> purchases = purchaseJob.getPaidPurchases()
                .stream()
                .filter(p -> (p.getTransactionStatus().equals(transactionStatus)))
                .collect(Collectors.toList());

        Assertions.assertTrue(purchases.isEmpty());
    }
}