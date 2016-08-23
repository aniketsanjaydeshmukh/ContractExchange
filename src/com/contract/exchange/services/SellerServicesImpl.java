package com.contract.exchange.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.contract.exchange.dao.SellerDao;
import com.contract.exchange.model.Seller;

@Transactional(propagation = Propagation.SUPPORTS, readOnly = true) 
@Service("sellerServices")
public class SellerServicesImpl implements SellerServices{

	@Autowired
	SellerDao sellerDao;
	
	@Override
	public boolean addOrUpdateSeller(Seller seller) {
		return sellerDao.addOrUpdateSeller(seller);
	}

	@Override
	public Seller getSellerByCompanyId(long companyId) {
		return sellerDao.getSellerByCompanyId(companyId);
	}

}
