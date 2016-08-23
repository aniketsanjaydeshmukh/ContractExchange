package com.contract.exchange.services;

import com.contract.exchange.model.Buyer;

public interface BuyerServices {
	boolean addOrUpdateBuyer(Buyer buyer);
	Buyer getBuyerByCompanyId(long companyId);
}
