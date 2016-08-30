package com.journaldev.servlet;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.file.Files;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/uploads/*")
public class FileGetter extends HttpServlet{
	
	private static final long serialVersionUID=-1l;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String applicationPath=request.getServletContext().getRealPath("");
		//constructs path of the directory to save uploaded file
		String uploadFilePath = applicationPath+File.separator+"uploads";
		
		String filename=URLDecoder.decode(request.getPathInfo().substring(1),"UTF-8");

		String referer = request.getHeader("Referer").substring(7);
		
		if(!referer.startsWith("localhost:8080/Watermarks"))
		{
			System.out.println("Error");
			String tempWtrmrkName = filename.substring(0, filename.indexOf("."))
					+"Watermarked"
					+filename.substring(filename.indexOf("."));
			File file = new File(uploadFilePath+"/"+tempWtrmrkName);
			response.setHeader("Content-Type", getServletContext().getMimeType(tempWtrmrkName));
			response.setHeader("Content-Length", String.valueOf(file.length()));
			response.setHeader("Content-Disposition", "inline; filename=\""+file.getName()+"\"");
			Files.copy(file.toPath(), response.getOutputStream());
		}
		else
		{
			System.out.println("OK");
			File file = new File(uploadFilePath+"/"+filename);
			response.setHeader("Content-Type", getServletContext().getMimeType(filename));
			response.setHeader("Content-Length", String.valueOf(file.length()));
			response.setHeader("Content-Disposition", "inline; filename=\""+file.getName()+"\"");
			Files.copy(file.toPath(), response.getOutputStream());
		}
	}
}
