/**
 * 
 */
package com.csv.model;

/**
 * @author uyr334f
 *
 */
public class CarrierRange implements CommonCSVInterface{
	private int lowZip ;
	private int highZip;
	private String carrier;
	
	public CarrierRange(int lowZip, int highZip, String carrier) {
		super();
		this.lowZip = lowZip;
		this.highZip = highZip;
		this.carrier = carrier;
	}
	
	public int getLowZip() {
		return lowZip;
	}
	public void setLowZip(int lowZip) {
		this.lowZip = lowZip;
	}
	public int getHighZip() {
		return highZip;
	}
	public void setHighZip(int highZip) {
		this.highZip = highZip;
	}
	public String getCarrier() {
		return carrier;
	}
	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}
	
	
}
