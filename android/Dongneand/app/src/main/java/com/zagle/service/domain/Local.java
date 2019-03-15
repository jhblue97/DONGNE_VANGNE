package com.zagle.service.domain;

public class Local {
	
	private int localCode;
	private String stateName;
	private String cityName;
	private String townName;
	private int stateCode;
	private int cityCode;
	private int townCode;
	
	public Local() {
		super();
	}

	public int getLocalCode() {
		return localCode;
	}

	public void setLocalCode(int localCode) {
		this.localCode = localCode;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getTownName() {
		return townName;
	}

	public void setTownName(String townName) {
		this.townName = townName;
	}

	public int getStateCode() {
		return stateCode;
	}

	public void setStateCode(int stateCode) {
		this.stateCode = stateCode;
	}

	public int getCityCode() {
		return cityCode;
	}

	public void setCityCode(int cityCode) {
		this.cityCode = cityCode;
	}

	public int getTownCode() {
		return townCode;
	}

	public void setTownCode(int townCode) {
		this.townCode = townCode;
	}

	@Override
	public String toString() {
		return "Local [localCode=" + localCode + ", stateName=" + stateName + ", cityName=" + cityName + ", townName="
				+ townName + ", stateCode=" + stateCode + ", cityCode=" + cityCode + ", townCode=" + townCode + "]";
	}

}
