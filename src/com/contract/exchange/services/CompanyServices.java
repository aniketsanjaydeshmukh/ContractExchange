package com.contract.exchange.services;

import java.util.Set;

import com.contract.exchange.model.Company;

public interface CompanyServices {
	boolean addOrUpdateCompany(Company company);
	Company login(String username,String password);
	boolean checkUnique(String username);
	Company getCompanyById(long companyId);
	Company getCompanyByUsername(String username);
	Company getCompanyByCompany(String companyName);
	Set<Company> getOtherCompany(long companyID);
}
