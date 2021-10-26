package com.codigoton.helper;

import com.codigoton.dto.AccountDTO;
import com.codigoton.dto.ClientDTO;

import java.util.List;

@FunctionalInterface
public interface TotalBalanceCalculator{
    double calculate(List<AccountDTO> accounts);
}
