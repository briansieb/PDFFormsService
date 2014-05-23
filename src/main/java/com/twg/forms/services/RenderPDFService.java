package com.twg.forms.services;

import com.twg.forms.models.*;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("/renderPDFForm/{pdfName}")
@Produces(MediaType.APPLICATION_JSON)
public class RenderPDFService 
{
	@GET
    public PDFFormResponse get(@PathParam("pdfName") String pdfName) 
	{
		PDFFormResponse formResponse = new PDFFormResponse();
		formResponse.setPdfFormName(pdfName);
        return new PDFFormResponse();
    }
	
}
