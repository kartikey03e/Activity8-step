package com.gdb.tests;

import com.gdb.domain.*;
import com.gdb.exceptions.*;

public class TestAccountSubclasses {
    public static void main(String[] args) {
        System.out.println("=== Activity 8: Polymorphism Test ===");

        // Step 1: Test SavingsAccount minimum balance breach
        Account savings = new SavingsAccount("SA101", "Alice", 25, 10000.0, "ACTIVE", "1234", 1000.0, 4.0);
        try {
            savings.withdraw(9500.0, "1234");
            System.out.println("[Savings] Withdraw 9500 (breaches min balance 1000): [FAIL]");
        } catch (MinimumBalanceViolationException e) {
            System.out.println("[Savings] Withdraw 9500 (breaches min balance 1000): Caught MinimumBalanceViolationException [PASS]");
        } catch (Exception e) {
            System.out.println("[Savings] Withdraw 9500 (breaches min balance 1000): [FAIL] - " + e.getMessage());
        }

        // Step 2: Test CurrentAccount valid withdrawal utilizing overdraft facility
        Account current = new CurrentAccount("CA101", "Bob", 30, 5000.0, "ACTIVE", "1234", 25000.0);
        try {
            current.withdraw(10000.0, "1234");
            if (current.getBalance() == -5000.0) {
                System.out.println("[Current] Withdraw with Overdraft (Balance goes to -5000): SUCCESS [PASS]");
            } else {
                System.out.println("[Current] Withdraw with Overdraft (Balance goes to -5000): [FAIL]");
            }
        } catch (Exception e) {
            System.out.println("[Current] Withdraw with Overdraft (Balance goes to -5000): [FAIL] - " + e.getMessage());
        }

        // Step 3: Test CurrentAccount exceeding overdraft limit
        try {
            current.withdraw(30000.0, "1234");
            System.out.println("[Current] Withdraw exceeding Overdraft (exceeds -25000): [FAIL]");
        } catch (InsufficientBalanceException e) {
            System.out.println("[Current] Withdraw exceeding Overdraft (exceeds -25000): Caught InsufficientBalanceException [PASS]");
        } catch (Exception e) {
            System.out.println("[Current] Withdraw exceeding Overdraft (exceeds -25000): [FAIL] - " + e.getMessage());
        }

        // Step 4: Test FixedDepositAccount premature withdrawal block
        Account fd = new FixedDepositAccount("FD101", "Charlie", 40, 50000.0, "ACTIVE", "1234", 12, 6.5);
        try {
            fd.withdraw(5000.0, "1234");
            System.out.println("[FixedDeposit] Withdraw attempt: [FAIL]");
        } catch (AccountException e) {
            System.out.println("[FixedDeposit] Withdraw attempt: Caught AccountException [PASS]");
        } catch (Exception e) {
            System.out.println("[FixedDeposit] Withdraw attempt: [FAIL] - " + e.getMessage());
        }

        System.out.println("All polymorphic behaviors verified!");
    }
}
