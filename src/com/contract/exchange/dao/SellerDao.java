package com.contract.exchange.dao;

import com.contract.exchange.model.Seller;

public interface SellerDao {
	boolean addOrUpdateSeller(Seller seller);
	Seller getSellerByCompanyId(long companyId);
}
