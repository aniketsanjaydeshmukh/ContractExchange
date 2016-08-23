package com.contract.exchange.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.contract.exchange.model.Seller;

@Transactional(propagation = Propagation.SUPPORTS, readOnly = true) 
public class SellerDaoImpl implements SellerDao{

	@Autowired
	SessionFactory sessionFactory;

	Session session = null;
	Transaction tx = null;
	
	@Override
	public boolean addOrUpdateSeller(Seller seller) {
		boolean flag = false;
	    try{    
	    	session = sessionFactory.openSession();
			tx = session.beginTransaction();
			session.saveOrUpdate(seller);
			tx.commit();
			session.close();
			flag = true;
	    }catch(Exception e){
	    	e.printStackTrace();
	    }
		return flag;
	}

	@Override
	public Seller getSellerByCompanyId(long companyId) {
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		
		Criteria c = session.createCriteria(Seller.class);
		c.createAlias("company", "c");
		c.add(Restrictions.eq("c.id", companyId));
		
		Seller seller = (Seller)c.uniqueResult();
		tx.commit();
		session.close();
		return seller;
	}
}
