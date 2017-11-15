package com.geog.Model;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped
public class HeadOfState {
	private String code;
	private String headOfState;
	
	public HeadOfState() {
		super();
	}

	public HeadOfState(String code, String headOfState) {
		super();
		this.code = code;
		this.headOfState = headOfState;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getHeadOfState() {
		return headOfState;
	}

	public void setHeadOfState(String headOfState) {
		this.headOfState = headOfState;
	}
}
