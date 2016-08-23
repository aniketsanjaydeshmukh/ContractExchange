package com.contract.exchange.services;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.contract.exchange.dao.ContractDao;
import com.contract.exchange.model.Contract;

@Transactional(propagation = Propagation.SUPPORTS, readOnly = true) 
@Service("contractServices")
public class ContractServicesImpl implements ContractServices{

	@Autowired
	ContractDao contractDao;
	
	@Override
	public boolean addOrUpdateContract(Contract contract) {
		return contractDao.addOrUpdateContract(contract);
	}

	@Override
	public Set<Contract> getContractByBuyer(long buyerId) {
		return contractDao.getContractByBuyer(buyerId);
	}

	@Override
	public Set<Contract> getContractByTypeOfGood(String typeOfGood) {
		return contractDao.getContractByTypeOfGood(typeOfGood);
	}

	@Override
	public Contract getContractById(long id) {
		return contractDao.getContractById(id);
	}

}
