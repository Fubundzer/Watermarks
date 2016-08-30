package com.journaldev.servlet;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.file.Files;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;


@WebServlet("/uploads/*")
public class FileGetter extends HttpServlet{
	
	private static final long serialVersionUID=-1l;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String applicationPath=request.getServletContext().getRealPath("");
		//constructs path of the directory to save uploaded file
		String uploadFilePath = applicationPath+File.separator+"uploads";
		String fileName = null;
		File uploadFolder = new File(uploadFilePath);
		for(File f : uploadFolder.listFiles()){
			//System.out.println(f.getName());
		}
		
		String filename=URLDecoder.decode(request.getPathInfo().substring(1),"UTF-8");
		//System.out.println(request.getPathInfo());
		//System.out.println(filename);
		/*File file = new File(uploadFilePath+"/"+filename);
		//System.out.println(getServletContext().getMimeType(filename));
		response.setHeader("Content-Type", getServletContext().getMimeType(filename));
		response.setHeader("Content-Length", String.valueOf(file.length()));
		response.setHeader("Content-Disposition", "inline; filename=\""+file.getName()+"\"");*/
		//System.out.println(response.getContentType());
		//System.out.println(this.getServletConfig().getServletContext().getAttribute("filePath"));
		request.setAttribute("filePath", this.getServletConfig().getServletContext().getAttribute("filePath"));
		//request.getServletContext().getRequestDispatcher("/response.jsp").forward(request,response);
		//String filePath = uploadFilePath+"/"+fileName;
		//System.out.println(uploadFilePath);
		//System.out.println(filePath);
		//System.out.println("Host:"+request.getHeader("Host"));
		//System.out.println("Referer:"+request.getHeader("Referer"));
		String referer = request.getHeader("Referer").substring(7);
		//System.out.println(referer);
		if(!referer.startsWith("localhost:8080/Watermarks"))
		{
			System.out.println("Error");
			String tempWtrmrkName = filename.substring(0, filename.indexOf("."))
					+"Watermarked"
					+filename.substring(filename.indexOf("."));
			System.out.println(tempWtrmrkName);
			File file = new File(uploadFilePath+"/"+tempWtrmrkName);
			//System.out.println(getServletContext().getMimeType(filename));
			response.setHeader("Content-Type", getServletContext().getMimeType(tempWtrmrkName));
			response.setHeader("Content-Length", String.valueOf(file.length()));
			response.setHeader("Content-Disposition", "inline; filename=\""+file.getName()+"\"");
			Files.copy(file.toPath(), response.getOutputStream());
		}else
		{
			System.out.println("OK");
			File file = new File(uploadFilePath+"/"+filename);
			//System.out.println(getServletContext().getMimeType(filename));
			response.setHeader("Content-Type", getServletContext().getMimeType(filename));
			response.setHeader("Content-Length", String.valueOf(file.length()));
			response.setHeader("Content-Disposition", "inline; filename=\""+file.getName()+"\"");
			Files.copy(file.toPath(), response.getOutputStream());
		}
	}

	private String getFileName(Part part){
		String contentDisp=part.getHeader("content-disposition");
		System.out.println("content-disposition header= "+contentDisp);
		String[] tokens = contentDisp.split(";");
		for(String token : tokens){
			if(token.trim().startsWith("filename")){
				return token.substring(token.indexOf("=")+2, token.length()-1);
			}
		}
		return "";
	}
	
}
