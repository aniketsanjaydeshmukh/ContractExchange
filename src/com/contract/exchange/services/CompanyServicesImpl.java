package com.contract.exchange.services;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.contract.exchange.dao.CompanyDao;
import com.contract.exchange.model.Company;

@Transactional(propagation = Propagation.SUPPORTS, readOnly = true) 
@Service("companyServices")
public class CompanyServicesImpl implements CompanyServices{

	@Autowired
	CompanyDao companyDao;

	@Override
	public boolean addOrUpdateCompany(Company company) {
		return companyDao.addOrUpdateCompany(company);
	}

	@Override
	public Company login(String username, String password) {
		return companyDao.login(username, password);
	}

	@Override
	public boolean checkUnique(String username) {
		return companyDao.checkUnique(username);
	}

	@Override
	public Company getCompanyById(long companyId) {
		return companyDao.getCompanyById(companyId);
	}

	@Override
	public Company getCompanyByUsername(String username) {
		return companyDao.getCompanyByUsername(username);
	}

	@Override
	public Company getCompanyByCompany(String companyName) {
		return companyDao.getCompanyByCompany(companyName);
	}

	@Override
	public Set<Company> getOtherCompany(long companyID) {
		return companyDao.getOtherCompany(companyID);
	}
}
