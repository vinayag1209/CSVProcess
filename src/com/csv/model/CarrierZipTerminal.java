/**
 * 
 */
package com.csv.model;

/**
 * @author uyr334f
 *
 */
public class CarrierZipTerminal implements CommonCSVInterface{
	
	public CarrierZipTerminal(int lowZip, int highZip, String terminal,
			String servDorIn, int servDaysIn, int servDaysOut, String servDorOut) {
		super();
		this.lowZip = lowZip;
		this.highZip = highZip;
		this.terminal = terminal;
		this.servDorIn = servDorIn;
		this.servDaysIn = servDaysIn;
		this.servDaysOut = servDaysOut;
		this.servDorOut = servDorOut;
	}
	private int lowZip;
	private int highZip;
	private String terminal;
	private String servDorIn;
	private int servDaysIn;
	private int servDaysOut;
	private String servDorOut;
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
	public String getTerminal() {
		return terminal;
	}
	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}
	public String getServDorIn() {
		return servDorIn;
	}
	public void setServDorIn(String servDorIn) {
		this.servDorIn = servDorIn;
	}
	public int getServDaysIn() {
		return servDaysIn;
	}
	public void setServDaysIn(int servDaysIn) {
		this.servDaysIn = servDaysIn;
	}
	public int getServDaysOut() {
		return servDaysOut;
	}
	public void setServDaysOut(int servDaysOut) {
		this.servDaysOut = servDaysOut;
	}
	public String getServDorOut() {
		return servDorOut;
	}
	public void setServDorOut(String servDorOut) {
		this.servDorOut = servDorOut;
	}
	

}
