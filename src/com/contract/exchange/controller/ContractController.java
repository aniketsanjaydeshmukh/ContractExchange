package com.contract.exchange.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.contract.exchange.DTO.CompanyDTO;
import com.contract.exchange.DTO.ContractDTO;
import com.contract.exchange.model.Buyer;
import com.contract.exchange.model.Company;
import com.contract.exchange.model.Contract;
import com.contract.exchange.model.Seller;
import com.contract.exchange.services.BuyerServices;
import com.contract.exchange.services.CompanyServices;
import com.contract.exchange.services.ContractServices;
import com.contract.exchange.services.SellerServices;

import flexjson.JSONSerializer;

@Controller
@RequestMapping("/contract")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ContractController {

	@Autowired
	CompanyServices companyServices;
	
	@Autowired
	SellerServices sellerServices;
	
	@Autowired
	BuyerServices buyerServices;
	
	@Autowired
	ContractServices contractServices;
	
	@RequestMapping(value = "/signup/",method = RequestMethod.POST,  headers = "content-type=application/json")
	public @ResponseBody
	void add(@RequestBody CompanyDTO companyDTO,HttpServletRequest request,HttpServletResponse response) throws Exception {
			Map<String,Object> obj = new HashMap<String,Object>();
			 if((!companyServices.checkUnique(companyDTO.getUsername()))){
				   obj.put("signup", "0");
				   obj.put("reason", "username must be  Unique");
				}			
				else{ 
					Company company = new Company(companyDTO);
					if(companyServices.addOrUpdateCompany(company)){
						Buyer buyer = new Buyer();
						buyer.setCompany(company);
						buyerServices.addOrUpdateBuyer(buyer);
						Seller seller = new Seller();
						seller.setCompany(company);
						sellerServices.addOrUpdateSeller(seller);
						obj.put("signup", "1");
					}	
				}
				response.setContentType("application/json; charset=UTF-8"); 
				response.getWriter().print(new JSONSerializer().exclude("class","*.class","authorities").deepSerialize(obj));
	}
	
	@RequestMapping(value = "/login/", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody
	void login(@RequestBody CompanyDTO companyDTO,HttpServletRequest request,HttpServletResponse response) throws Exception {
		Map<String,Object> obj = new HashMap<String,Object>();
		Company company = companyServices.login(companyDTO.getUsername(),companyDTO.getPassword());
			if(company != null){
				 obj.put("login", "1");
				 obj.put("buyerID", company.getId());
				 obj.put("buyerName", company.getCompany());
			}else{
				obj.put("login", "0");
			}
			response.setContentType("application/json; charset=UTF-8"); 
			response.getWriter().print(new JSONSerializer().exclude("class","*.class","authorities").deepSerialize(obj));		
	}
	
	@RequestMapping(value = "/editCompany/",method = RequestMethod.POST,  headers = "content-type=application/json")
	public @ResponseBody
	void edit(@RequestBody CompanyDTO companyDTO,HttpServletRequest request,HttpServletResponse response) throws Exception {
		Map<String,Object> obj = new HashMap<String,Object>();
		Company oldCompany = companyServices.getCompanyById(companyDTO.getId());
		oldCompany.setCompany(companyDTO.getCompany());
		oldCompany.setCountry(companyDTO.getCountry());
		oldCompany.setEmail(companyDTO.getEmail());
		oldCompany.setPassword(companyDTO.getPassword());
		oldCompany.setStreet(companyDTO.getStreet());
		oldCompany.setUsername(companyDTO.getUsername());
		if(!oldCompany.getUsername().equals(companyDTO.getUsername())){
		 if((!companyServices.checkUnique(companyDTO.getUsername()))){
			   obj.put("edit", "0");
			   obj.put("reason", "username must be  Unique");
			}
			else{ 
				
				if(companyServices.addOrUpdateCompany(oldCompany)){
					obj.put("edit", "1");
				}	
			}
		}
		 else{
				if(companyServices.addOrUpdateCompany(oldCompany)){
					obj.put("edit", "1");
				}	
		 }
			response.setContentType("application/json; charset=UTF-8"); 
			response.getWriter().print(new JSONSerializer().exclude("class","*.class","authorities").deepSerialize(obj));
	}
	
	@RequestMapping(value = "/viewCompany/",method = RequestMethod.POST,  headers = "content-type=application/json")
	public @ResponseBody
	void view(@RequestBody CompanyDTO companyDTO,HttpServletRequest request,HttpServletResponse response) throws Exception {
		Map<String,Object> obj = new HashMap<String,Object>();
		Company oldCompany = companyServices.getCompanyByUsername(companyDTO.getUsername());
		obj.put("company", oldCompany.getCompany());
		obj.put("country", oldCompany.getCountry());
		obj.put("email", oldCompany.getEmail());
		obj.put("password", oldCompany.getPassword());
		obj.put("id", oldCompany.getId());
		obj.put("password", oldCompany.getPassword());
		obj.put("street", oldCompany.getStreet());
		obj.put("username", oldCompany.getUsername());
		
		response.setContentType("application/json; charset=UTF-8"); 
		response.getWriter().print(new JSONSerializer().exclude("class","*.class","authorities").deepSerialize(obj));
	}
	
	@RequestMapping(value = "/addContract/",method = RequestMethod.POST,  headers = "content-type=application/json")
	public @ResponseBody
	void addContract(@RequestBody ContractDTO contractDTO,HttpServletRequest request,HttpServletResponse response) throws Exception {
		Map<String,Object> obj = new HashMap<String,Object>();
		
		Buyer buyer = buyerServices.getBuyerByCompanyId(contractDTO.getBuyerID());
		Seller seller = sellerServices.getSellerByCompanyId(contractDTO.getSellerID());
		
		Contract contract = new Contract(contractDTO);
		contract.setBuyer(buyer);
		contract.setSeller(seller);
		
		if(contractServices.addOrUpdateContract(contract)){
			obj.put("contractAdded", "1");
		}else{
			obj.put("contractAdded", "0");
		}
		
		response.setContentType("application/json; charset=UTF-8"); 
		response.getWriter().print(new JSONSerializer().exclude("class","*.class","authorities").deepSerialize(obj));
	}
	
	@RequestMapping(value = "/searchByBuyer/",method = RequestMethod.POST,  headers = "content-type=application/json")
	public @ResponseBody
	void searchByBuyer(@RequestBody ContractDTO contractDTO,HttpServletRequest request,HttpServletResponse response) throws Exception {
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		Company company = companyServices.getCompanyByCompany(contractDTO.getCompany());
		if(company != null){
		Buyer buyer = buyerServices.getBuyerByCompanyId(company.getId());
		Set<Contract> contractList = contractServices.getContractByBuyer(buyer.getBuyerID());
		for (Contract contract : contractList) {
			Map<String,Object> obj = new HashMap<String,Object>();
			obj.put("id", contract.getId());
			obj.put("buyer", contract.getBuyer().getCompany().getCompany());
			obj.put("currency", contract.getCurrency());
			obj.put("dateOfDelivery", contract.getDateOfDelivery());
			obj.put("grade", contract.getGrade());
			obj.put("place", contract.getPlace());
			obj.put("price", contract.getPrice());
			obj.put("quality", contract.getQuality());
			obj.put("quantity", contract.getQuantity());
			obj.put("quantityUnit", contract.getQuantityUnit());
			obj.put("seller", contract.getSeller().getCompany().getCompany());
			obj.put("typeOfGood", contract.getTypeOfGood());
			list.add(obj);
		}
		}else{
			Set<Contract> contractList = contractServices.getContractByTypeOfGood(contractDTO.getCompany());
			for (Contract contract : contractList) {
				Map<String,Object> obj = new HashMap<String,Object>();
				obj.put("id", contract.getId());
				obj.put("buyer", contract.getBuyer().getCompany().getCompany());
				obj.put("currency", contract.getCurrency());
				obj.put("dateOfDelivery", contract.getDateOfDelivery());
				obj.put("grade", contract.getGrade());
				obj.put("place", contract.getPlace());
				obj.put("price", contract.getPrice());
				obj.put("quality", contract.getQuality());
				obj.put("quantity", contract.getQuantity());
				obj.put("quantityUnit", contract.getQuantityUnit());
				obj.put("seller", contract.getSeller().getCompany().getCompany());
				obj.put("typeOfGood", contract.getTypeOfGood());
				list.add(obj);
			}
		}
		response.setContentType("application/json; charset=UTF-8"); 
		response.getWriter().print(new JSONSerializer().exclude("class","*.class","authorities").deepSerialize(list));
	}
	
	@RequestMapping(value = "/searchByGood/",method = RequestMethod.POST,  headers = "content-type=application/json")
	public @ResponseBody
	void searchByGood(@RequestBody ContractDTO contractDTO,HttpServletRequest request,HttpServletResponse response) throws Exception {
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		/*Company company = companyServices.getCompanyByCompany(contractDTO.getCompany());
		Buyer buyer = buyerServices.getBuyerByCompanyId(company.getId());*/
		Set<Contract> contractList = contractServices.getContractByTypeOfGood(contractDTO.getTypeOfGood());
		for (Contract contract : contractList) {
			Map<String,Object> obj = new HashMap<String,Object>();
			obj.put("id", contract.getId());
			obj.put("buyer", contract.getBuyer().getCompany().getCompany());
			obj.put("currency", contract.getCurrency());
			obj.put("dateOfDelivery", contract.getDateOfDelivery());
			obj.put("grade", contract.getGrade());
			obj.put("place", contract.getPlace());
			obj.put("price", contract.getPrice());
			obj.put("quality", contract.getQuality());
			obj.put("quantity", contract.getQuantity());
			obj.put("quantityUnit", contract.getQuantityUnit());
			obj.put("seller", contract.getSeller().getCompany().getCompany());
			obj.put("typeOfGood", contract.getTypeOfGood());
			list.add(obj);
		}
		response.setContentType("application/json; charset=UTF-8"); 
		response.getWriter().print(new JSONSerializer().exclude("class","*.class","authorities").deepSerialize(list));
	}
	
	@RequestMapping(value = "/getOtherCompany/",method = RequestMethod.POST,  headers = "content-type=application/json")
	public @ResponseBody
	void getOtherCompany(@RequestBody CompanyDTO companyDTO,HttpServletRequest request,HttpServletResponse response) throws Exception {
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		Set<Company> companies = companyServices.getOtherCompany(companyDTO.getId());
		for (Company company : companies) {
			Map<String,Object> obj = new HashMap<String,Object>();
			obj.put("companyID", company.getId());
			obj.put("companyName", company.getCompany());
			list.add(obj);
		}
		response.setContentType("application/json; charset=UTF-8"); 
		response.getWriter().print(new JSONSerializer().exclude("class","*.class","authorities").deepSerialize(list));
	}
	
	@RequestMapping(value = "/viewContract/",method = RequestMethod.POST,  headers = "content-type=application/json")
	public @ResponseBody
	void viewContract(@RequestBody ContractDTO contractDTO,HttpServletRequest request,HttpServletResponse response) throws Exception {
		Map<String,Object> obj = new HashMap<String,Object>();
		
		Contract contract = contractServices.getContractById(contractDTO.getId());
		
		obj.put("id", contract.getId());
		obj.put("buyer", contract.getBuyer().getCompany().getCompany());
		obj.put("currency", contract.getCurrency());
		obj.put("dateOfDelivery", contract.getDateOfDelivery());
		obj.put("grade", contract.getGrade());
		obj.put("place", contract.getPlace());
		obj.put("price", contract.getPrice());
		obj.put("quality", contract.getQuality());
		obj.put("quantity", contract.getQuantity());
		obj.put("quantityUnit", contract.getQuantityUnit());
		obj.put("seller", contract.getSeller().getCompany().getCompany());
		obj.put("typeOfGood", contract.getTypeOfGood());
		
		response.setContentType("application/json; charset=UTF-8"); 
		response.getWriter().print(new JSONSerializer().exclude("class","*.class","authorities").deepSerialize(obj));
	}
}
