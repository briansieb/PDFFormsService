package com.twg.forms.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.twg.forms.models.PDFFormResponse;

//Simple REST Service 

@Path("/renderPDF")
@Produces(MediaType.APPLICATION_JSON)
public class renderPDF 
{
	@GET
    public PDFFormResponse get() {
        return new PDFFormResponse();
    }
}
