package com.contract.exchange.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.contract.exchange.model.Company;
import com.contract.exchange.model.Contract;

@Transactional(propagation = Propagation.SUPPORTS, readOnly = true) 
public class ContractDaoImpl implements ContractDao{

	@Autowired
	SessionFactory sessionFactory;

	Session session = null;
	Transaction tx = null;
	
	@Override
	public boolean addOrUpdateContract(Contract contract) {
		boolean flag = false;
	    try{    
	    	session = sessionFactory.openSession();
			tx = session.beginTransaction();
			session.saveOrUpdate(contract);
			tx.commit();
			session.close();
			flag = true;
	    }catch(Exception e){
	    	e.printStackTrace();
	    }
		return flag;
	}

	@Override
	public Set<Contract> getContractByBuyer(long buyerId) {
			 session = sessionFactory.openSession();
				tx = session.beginTransaction();
				Criteria c = session.createCriteria(Contract.class);
				c.createAlias("buyer", "b");
				c.add(Restrictions.eq("b.buyerID", buyerId));
				List<Contract> contractList = c.list();
				Set<Contract> contractSet = new HashSet<Contract>(contractList); 
				tx.commit();
				session.close();
				return contractSet;
	}

	@Override
	public Set<Contract> getContractByTypeOfGood(String typeOfGood) {
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		Criteria c = session.createCriteria(Contract.class);
		c.add(Restrictions.eq("typeOfGood", typeOfGood));
		List<Contract> contractList = c.list();
		Set<Contract> contractSet = new HashSet<Contract>(contractList); 
		tx.commit();
		session.close();
		return contractSet;
	}

	@Override
	public Contract getContractById(long id) {
		Session session;
		Contract  contract = null;
		try{
			session = sessionFactory.openSession();
			Criteria criteria = session.createCriteria(Contract.class);
			 criteria.add(Restrictions.eq("id", id));
			 Object result=criteria.uniqueResult();
			 contract = (Contract)result;
			 session.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return contract;
	}
}
