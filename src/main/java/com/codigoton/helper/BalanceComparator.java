package com.codigoton.helper;

@FunctionalInterface
public interface BalanceComparator<T>{
    boolean compare(T client1, T client2);
}
