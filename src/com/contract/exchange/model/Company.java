package com.contract.exchange.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.Proxy;

import com.contract.exchange.DTO.CompanyDTO;


@Entity
@Table(name = "Company")
@JsonIgnoreProperties(ignoreUnknown = true)
@Proxy(lazy=false)
public class Company implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9071118875083966660L;
	
	@Id
	@GeneratedValue
	@Column(name = "id")
	private long id;
	
	@Column(name = "username")
	private String username;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "company")
	private String company;
	
	@Column(name = "street")
	private String street;
	
	@Column(name = "country")
	private String country;
	
	@Column(name = "email")
	private String email;
	
	@OneToOne(mappedBy="company", cascade=CascadeType.ALL)
	private Buyer buyer;
	
	@OneToOne(mappedBy="company", cascade=CascadeType.ALL)
	private Seller seller;
	
	public Company(CompanyDTO companyDTO){
		this.password=companyDTO.getPassword();
		this.email=companyDTO.getEmail();
		this.street=companyDTO.getStreet();
		this.username=companyDTO.getUsername();
		this.company=companyDTO.getCompany();
		this.country=companyDTO.getCountry();
	}
	
	public Company(){
		
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
