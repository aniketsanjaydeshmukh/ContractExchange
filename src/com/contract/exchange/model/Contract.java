package com.contract.exchange.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.Proxy;

import com.contract.exchange.DTO.ContractDTO;


@Entity
@Table(name = "Contract")
@JsonIgnoreProperties(ignoreUnknown = true)
@Proxy(lazy=false)
public class Contract implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3624552271606411539L;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private long id;
	
	@Column(name = "typeOfGood")
	private String typeOfGood;
	
	@Column(name = "price")
	private long price;
	
	@Column(name = "currency")
	private String currency;
	
	@Column(name = "quantity")
	private long quantity;
	
	@Column(name = "quantityUnit")
	private String quantityUnit;
	
	@Column(name = "quality")
	private String quality;
	
	@Column(name = "grade")
	private String grade;
	
	@Column(name = "dateOfDelivery")
	private String dateOfDelivery;
	
	@Column(name = "place")
	private String place;

	public Contract(){
		
	}
	
	public Contract(ContractDTO contractDTO){
		this.currency=contractDTO.getCurrency();
		this.dateOfDelivery=contractDTO.getDateOfDelivery();
		this.grade=contractDTO.getGrade();
		this.place=contractDTO.getPlace();
		this.price=contractDTO.getPrice();
		this.quality=contractDTO.getQuality();
		this.quantity=contractDTO.getQuantity();
		this.quantityUnit=contractDTO.getQuantityUnit();
		this.typeOfGood=contractDTO.getTypeOfGood();
	}

    @ManyToOne
    @JoinColumn(name="buyerId")
	private Buyer buyer;
    
    @ManyToOne
    @JoinColumn(name="sellerId")
	private Seller seller;
	
	
	public Buyer getBuyer() {
		return buyer;
	}

	public void setBuyer(Buyer buyer) {
		this.buyer = buyer;
	}

	public Seller getSeller() {
		return seller;
	}

	public void setSeller(Seller seller) {
		this.seller = seller;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTypeOfGood() {
		return typeOfGood;
	}

	public void setTypeOfGood(String typeOfGood) {
		this.typeOfGood = typeOfGood;
	}

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public long getQuantity() {
		return quantity;
	}

	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}

	public String getQuantityUnit() {
		return quantityUnit;
	}

	public void setQuantityUnit(String quantityUnit) {
		this.quantityUnit = quantityUnit;
	}

	public String getQuality() {
		return quality;
	}

	public void setQuality(String quality) {
		this.quality = quality;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getDateOfDelivery() {
		return dateOfDelivery;
	}

	public void setDateOfDelivery(String dateOfDelivery) {
		this.dateOfDelivery = dateOfDelivery;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}
}
