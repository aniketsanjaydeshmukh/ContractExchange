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
@Table(name = "Buyer")
@JsonIgnoreProperties(ignoreUnknown = true)
@Proxy(lazy=false)
public class Buyer implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1507212126978843093L;

	@Id
	@GeneratedValue
	@Column(name = "buyerID")
	private long buyerID;
	
	 @OneToOne()
	 @JoinColumn(name="companyId")
	 private Company company;
	 
	 @OneToMany(fetch = FetchType.EAGER,mappedBy="buyer",cascade=CascadeType.ALL)
	 private Set<Contract> contract;

	public long getBuyerID() {
		return buyerID;
	}

	public void setBuyerID(long buyerID) {
		this.buyerID = buyerID;
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
