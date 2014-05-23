package com.twg.forms.services;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.util.Set;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.twg.forms.models.*;

import java.net.URL;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.codec.binary.Base64;
@Path("/renderPDFForm/{pdfName}")
@Produces(MediaType.APPLICATION_JSON)
public class RenderPDFService 
{
	@GET
    public PDFFormResponse get(@PathParam("pdfName") String pdfName) 
	{
		PDFFormResponse formResponse = new PDFFormResponse();
		try
		{
		
		PdfReader pdfReader = new PdfReader(new URL("http://http://cryptic-headland-7139.herokuapp.com/TWG_Test_Contract.pdf"));
		ByteArrayOutputStream fos = new ByteArrayOutputStream();
		
		PdfStamper stamper = new PdfStamper(pdfReader, fos);
		
		Set<String> fields = (Set<String>) stamper.getAcroFields().getFields().keySet();
		for(String fn : fields)
		{
			System.out.println(fn);
		}
		stamper.getAcroFields().setField("Text Box 1", "Cory Cowgill");
		//stamper.setFormFlattening(true);
		stamper.close();
		pdfReader.close();
		System.out.println("Done");	
		byte[] fileBytes = fos.toByteArray();
		String base64String = Base64.encodeBase64String(fileBytes);
		formResponse.setPdfContents(base64String);
		formResponse.setPdfFormName(pdfName);
		}catch(Exception e)
		{
			e.printStackTrace();
			//Lets handle an exception........
		}
        return formResponse;
    }
	
}
