package com.contract.exchange.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.contract.exchange.dao.BuyerDao;
import com.contract.exchange.model.Buyer;

@Transactional(propagation = Propagation.SUPPORTS, readOnly = true) 
@Service("buyerServices")
public class BuyerServicesImpl implements BuyerServices{

	@Autowired
	BuyerDao buyerDao;
	
	@Override
	public boolean addOrUpdateBuyer(Buyer buyer) {
		return buyerDao.addOrUpdateBuyer(buyer); 
	}

	@Override
	public Buyer getBuyerByCompanyId(long companyId) {
		return buyerDao.getBuyerByCompanyId(companyId);
	}

}
