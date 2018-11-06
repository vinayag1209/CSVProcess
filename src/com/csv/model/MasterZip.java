package com.csv.model;

public class MasterZip implements CommonCSVInterface{

	private int zipCode;

	public MasterZip(int zipCode) {
		super();
		this.zipCode = zipCode;
	}

	public int getZipCode() {
		return zipCode;
	}

	public void setZipCode(int zipCode) {
		this.zipCode = zipCode;
	}
}
