package com.twg.forms.services;

import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.commons.codec.binary.Base64;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.twg.forms.models.PDFFormResponse;

//Simple REST Web Service to render a PDF

@Path("/renderPDFForm/{pdfName}")
@Produces(MediaType.APPLICATION_JSON)
public class RenderPDFService 
{
	//Expose a HTTP GET REST Service
	@GET
    public PDFFormResponse get(@PathParam("pdfName") String pdfName) 
	{
		PDFFormResponse formResponse = new PDFFormResponse();
		try
		{
			//Use iText Opensource Library to Render a PDF Document
			//Read a PDF from a URL, hardcode the URL to test out.....
			PdfReader pdfReader = new PdfReader(new URL("http://cryptic-headland-7139.herokuapp.com/TWG_Test_Contract.pdf"));
			ByteArrayOutputStream fos = new ByteArrayOutputStream();
			
			//PDFStamper allows you to merge data with the fields in an AcroForm
			PdfStamper stamper = new PdfStamper(pdfReader, fos);
			
			//We should put this into a seperate class / method which should merge the data into the Form
			Set<String> fields = (Set<String>) stamper.getAcroFields().getFields().keySet();
			stamper.getAcroFields().setField("Text Box 1", "Cory Cowgill");
			stamper.close();
			pdfReader.close();
			
			//Once the PDF is Rendered and data is merged, we need to write out the PDF from memory into output
			//For this simple REST Service we can simply Base64 Encode the Bytes. We can change this to return the file directly instead of JSON / Base64 String if we want to
			byte[] fileBytes = fos.toByteArray();
			String base64String = Base64.encodeBase64String(fileBytes);
			formResponse.setPdfContents(base64String);
			formResponse.setPdfFormName(pdfName);
		}catch(Exception e)
		{
			e.printStackTrace();
			//Lets handle an exception but for POC simply write to the Heroku Logs
		}
        return formResponse;
    }

	//Expose a HTTP POST REST Service
	@POST
	@Consumes("application/x-www-form-urlencoded")
    public PDFFormResponse post(MultivaluedMap<String, String> formParams) 
	{
		PDFFormResponse formResponse = new PDFFormResponse();
		try
		{
			//Use iText Opensource Library to Render a PDF Document
			//Read a PDF from a URL, hardcode the URL to test out.....
			PdfReader pdfReader = new PdfReader(new URL("http://cryptic-headland-7139.herokuapp.com/TWG_Contract_PDF2.pdf"));
			ByteArrayOutputStream fos = new ByteArrayOutputStream();
			
			//PDFStamper allows you to merge data with the fields in an AcroForm
			PdfStamper stamper = new PdfStamper(pdfReader, fos);
			
			//We should put this into a seperate class / method which should merge the data into the Form
			Set<String> fields = (Set<String>) stamper.getAcroFields().getFields().keySet();
			for(String fieldName : formParams.keySet())
			{
				try
				{
					stamper.getAcroFields().setField(fieldName, formParams.getFirst(fieldName));
					System.out.println(fieldName);
				}catch(Exception e)
				{
					//If Field doesn't exist on the Form move on don't die......
				}
			}
			stamper.close();
			pdfReader.close();
			
			//Once the PDF is Rendered and data is merged, we need to write out the PDF from memory into output
			//For this simple REST Service we can simply Base64 Encode the Bytes. We can change this to return the file directly instead of JSON / Base64 String if we want to
			byte[] fileBytes = fos.toByteArray();
			String base64String = Base64.encodeBase64String(fileBytes);
			formResponse.setPdfContents(base64String);
			formResponse.setPdfFormName("ReturnedPayload.pdf");
		}catch(Exception e)
		{
			e.printStackTrace();
			//Lets handle an exception but for POC simply write to the Heroku Logs
		}
        return formResponse;
    }	
	
	
}
