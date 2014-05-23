package com.twg.forms.models;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PDFFormResponse 
{
	private String pdfFormName;
	private String pdfContents;
	
	public String getPdfContents() {
		return pdfContents;
	}

	public void setPdfContents(String pdfContents) {
		this.pdfContents = pdfContents;
	}

	public PDFFormResponse()
	{
		pdfFormName = "emptyPDF.pdf";
	}
	
	public String getPdfFormName() {
		return pdfFormName;
	}

	public void setPdfFormName(String pdfFormName) {
		this.pdfFormName = pdfFormName;
	}
	
}
