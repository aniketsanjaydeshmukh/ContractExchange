package com.contract.exchange.dao;


import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.contract.exchange.model.Buyer;

@Transactional(propagation = Propagation.SUPPORTS, readOnly = true) 
public class BuyerDaoImpl implements BuyerDao{

	@Autowired
	SessionFactory sessionFactory;

	Session session = null;
	Transaction tx = null;
	
	@Override
	public boolean addOrUpdateBuyer(Buyer buyer) {
		boolean flag = false;
	    try{    
	    	session = sessionFactory.openSession();
			tx = session.beginTransaction();
			session.saveOrUpdate(buyer);
			tx.commit();
			session.close();
			flag = true;
	    }catch(Exception e){
	    	e.printStackTrace();
	    }
		return flag;
	}

	@Override
	public Buyer getBuyerByCompanyId(long companyId) {
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		
		Criteria c = session.createCriteria(Buyer.class);
		c.createAlias("company", "c");
		c.add(Restrictions.eq("c.id", companyId));
		
		Buyer buyer = (Buyer)c.uniqueResult();
		tx.commit();
		session.close();
		return buyer;
	}
}
