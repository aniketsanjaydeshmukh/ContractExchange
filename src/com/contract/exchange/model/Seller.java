package com.contract.exchange.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.Proxy;

@Entity
@Table(name = "Seller")
@JsonIgnoreProperties(ignoreUnknown = true)
@Proxy(lazy=false)
public class Seller implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5307372076501362399L;

	@Id
	@GeneratedValue
	@Column(name = "sellerID")
	private long sellerID;
	
	 @OneToOne()
	 @JoinColumn(name="companyId")
	 private Company company;
	 
	 
	 @OneToMany(fetch = FetchType.EAGER,mappedBy="seller",cascade=CascadeType.ALL)
	 private Set<Contract> contract;


	public long getSellerID() {
		return sellerID;
	}


	public void setSellerID(long sellerID) {
		this.sellerID = sellerID;
	}


	public Company getCompany() {
		return company;
	}


	public void setCompany(Company company) {
		this.company = company;
	}


	public Set<Contract> getContract() {
		return contract;
	}


	public void setContract(Set<Contract> contract) {
		this.contract = contract;
	} 
}
