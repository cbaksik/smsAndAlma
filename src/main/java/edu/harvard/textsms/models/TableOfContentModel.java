package edu.harvard.textsms.models;


public class TableOfContentModel {
	
	private String isbn;
	private String result;
	
	private Boolean hasData;

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public Boolean getHasData() {
		return hasData;
	}

	public void setHasData(Boolean hasData) {
		this.hasData = hasData;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
	
}
