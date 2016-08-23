package com.contract.exchange.services;

import com.contract.exchange.model.Seller;

public interface SellerServices {
	boolean addOrUpdateSeller(Seller seller);
	Seller getSellerByCompanyId(long companyId);
}
