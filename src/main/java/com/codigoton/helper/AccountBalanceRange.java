package com.codigoton.helper;

import java.util.List;

@FunctionalInterface
public interface AccountBalanceRange {
    boolean test(double balance, double initialValue, double finalValue);
}
