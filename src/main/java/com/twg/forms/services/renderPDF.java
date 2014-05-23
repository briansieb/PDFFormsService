package com.twg.forms.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("/renderPDF")
@Produces(MediaType.APPLICATION_JSON)
public class renderPDF 
{
	@GET
    public pdfForm get() {
        return "PDF_CALL";
    }
}
