package models;

import java.util.List;

public class BrandContactDetails {
	private String id;
	private String addressline1;
	private String addressline2;
	private String state;
	private String city;
	private String country;
	private String pincode;
	private List<ContactPersonDetails> contactPersons;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAddressline1() {
		return addressline1;
	}
	public void setAddressline1(String addressline1) {
		this.addressline1 = addressline1;
	}
	public String getAddressline2() {
		return addressline2;
	}
	public void setAddressline2(String addressline2) {
		this.addressline2 = addressline2;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getPincode() {
		return pincode;
	}
	public void setPincode(String pincode) {
		this.pincode = pincode;
	}
	public List<ContactPersonDetails> getContactPersons() {
		return contactPersons;
	}
	public void setContactPersons(List<ContactPersonDetails> contactPersons) {
		this.contactPersons = contactPersons;
	}
	
}
