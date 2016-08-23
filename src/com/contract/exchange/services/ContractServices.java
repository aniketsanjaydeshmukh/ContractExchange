package com.contract.exchange.services;

import java.util.Set;

import com.contract.exchange.model.Contract;

public interface ContractServices {
	boolean addOrUpdateContract(Contract contract);
	Set<Contract> getContractByBuyer(long buyerId);
	Set<Contract> getContractByTypeOfGood(String typeOfGood);
	Contract getContractById(long id);
}
