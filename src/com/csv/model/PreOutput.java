/**
 * 
 */
package com.csv.model;

/**
 * @author uyr334f
 *
 */
public class PreOutput {

	private int zip;
	private String Terminal;
	private String serviceOutbound;
	private int daysOutbound;
	private String serviceInbound;
	private int daysInbound;
	
	public PreOutput(int zip, String terminal, String serviceOutbound,
			int daysOutbound, String serviceInbound, int daysInbound) {
		super();
		this.zip = zip;
		Terminal = terminal;
		this.serviceOutbound = serviceOutbound;
		this.daysOutbound = daysOutbound;
		this.serviceInbound = serviceInbound;
		this.daysInbound = daysInbound;
	}
	
	public int getZip() {
		return zip;
	}
	public void setZip(int zip) {
		this.zip = zip;
	}
	public String getTerminal() {
		return Terminal;
	}
	public void setTerminal(String terminal) {
		Terminal = terminal;
	}
	public String getServiceOutbound() {
		return serviceOutbound;
	}
	public void setServiceOutbound(String serviceOutbound) {
		this.serviceOutbound = serviceOutbound;
	}
	public int getDaysOutbound() {
		return daysOutbound;
	}
	public void setDaysOutbound(int daysOutbound) {
		this.daysOutbound = daysOutbound;
	}
	public String getServiceInbound() {
		return serviceInbound;
	}
	public void setServiceInbound(String serviceInbound) {
		this.serviceInbound = serviceInbound;
	}
	public int getDaysInbound() {
		return daysInbound;
	}
	public void setDaysInbound(int daysInbound) {
		this.daysInbound = daysInbound;
	}
	
}
