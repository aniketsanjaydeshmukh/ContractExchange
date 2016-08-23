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

@Transactional(propagation = Propagation.SUPPORTS, readOnly = true) 
public class CompanyDaoImpl implements CompanyDao{

	@Autowired
	SessionFactory sessionFactory;

	Session session = null;
	Transaction tx = null;
	
	@Override
	public boolean addOrUpdateCompany(Company company) {
		boolean flag = false;
	    try{    
	    	session = sessionFactory.openSession();
			tx = session.beginTransaction();
			session.saveOrUpdate(company);
			tx.commit();
			session.close();
			flag = true;
	    }catch(Exception e){
	    	e.printStackTrace();
	    }
		return flag;
	}
	@Override
	public Company login(String username, String password) {
		Company company = null;
	    try{  	
	    session = sessionFactory.openSession();
		Criteria c = session.createCriteria(Company.class);
		c.add(Restrictions.eq("username",username));
		c.add(Restrictions.eq("password", password));
		  company  = (Company)c.uniqueResult();
			
	    }catch(Exception e){
	    	e.printStackTrace();
	    }
	    session.close();
		return company;
	}
	
	@Override
	public boolean checkUnique(String username) {
		boolean flag=false;
		try{  	
				session = sessionFactory.openSession();
				Criteria c = session.createCriteria(Company.class);
				c.add(Restrictions.eq("username", username));
				Object o = c.uniqueResult();
				if(o==null){
					flag=true;
				}
				session.close();
			
		 }catch(Exception e){
		    	e.printStackTrace();
		    }
		return flag;	
	}
	@Override
	public Company getCompanyById(long companyId) {
		Session session;
		Company  company = null;
		try{
			session = sessionFactory.openSession();
			Criteria criteria = session.createCriteria(Company.class);
			 criteria.add(Restrictions.eq("id", companyId));
			 Object result=criteria.uniqueResult();
			 company = (Company)result;
			 session.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return company;
	}
	
	@Override
	public Company getCompanyByUsername(String username) {
		Session session;
		Company  company = null;
		try{
			session = sessionFactory.openSession();
			Criteria criteria = session.createCriteria(Company.class);
			 criteria.add(Restrictions.eq("username", username));
			 Object result=criteria.uniqueResult();
			 company = (Company)result;
			 session.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return company;
	}
	
	@Override
	public Company getCompanyByCompany(String companyName) {
		Session session;
		Company  company = null;
		try{
			session = sessionFactory.openSession();
			Criteria criteria = session.createCriteria(Company.class);
			 criteria.add(Restrictions.eq("company", companyName));
			 Object result=criteria.uniqueResult();
			 company = (Company)result;
			 session.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return company;
	}
	@Override
	public Set<Company> getOtherCompany(long companyID) {
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		
		Criteria c = session.createCriteria(Company.class);
		
		
		List<Company> list =  c.list();
		Set<Company> restaurantList = new HashSet<Company>(list); 
		Set<Company> set = new HashSet<Company>();
		for (Company company : restaurantList) {
			if(!(company.getId()==companyID)){
				set.add(company);
			}
		}
		
		
		
		tx.commit();
		session.close();
		return set;
	}
}
