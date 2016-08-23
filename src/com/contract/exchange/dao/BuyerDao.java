package com.contract.exchange.dao;

import com.contract.exchange.model.Buyer;

public interface BuyerDao {
	boolean addOrUpdateBuyer(Buyer buyer);
	Buyer getBuyerByCompanyId(long companyId);
}
