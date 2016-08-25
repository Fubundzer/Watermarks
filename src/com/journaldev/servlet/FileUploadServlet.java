package com.journaldev.servlet;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet("/FileUploadServlet")
@MultipartConfig(fileSizeThreshold=1024*1024*10,
				maxFileSize=1024*1024*50,
				maxRequestSize=1024*1024*100)
public class FileUploadServlet extends HttpServlet{

	private static final long serialVersionUID=205242440643911308L;
	
	/**
	 * Directory where uploaded files will be saved, its relative to
	 * the web application directory.
	 */
	
	private static final String UPLOAD_DIR="uploads";
	
	protected void doPost(HttpServletRequest request,
						HttpServletResponse response) throws ServletException, IOException{
		//gets absolute path of the web application
		String applicationPath=request.getServletContext().getRealPath("");
		//constructs path of the directory to save uploaded file
		String uploadFilePath = applicationPath+File.separator+UPLOAD_DIR;
		
		//creates the save directory if it does not exists
		File fileSaveDir = new File(uploadFilePath);
		if(!fileSaveDir.exists()){
			fileSaveDir.mkdirs();
		}
		System.out.println("Upload File Directory="+fileSaveDir.getAbsolutePath());
		
		String fileName = null;
		//Get all the parts from request and write it to the file on server
		for(Part part : request.getParts()){
			fileName=getFileName(part);
			part.write(uploadFilePath+File.separator+fileName);
		}
		
		String filePath = uploadFilePath+"/"+fileName;
		//BufferedImage bimg=ImageIO.read(new File(filePath));
		//BufferedImage bimg=ImageIO.read(new File(uploadFilePath+"/"+fileName));
		File sourceImageFile= new File(filePath);
		String tempWtrmrkName = fileName.substring(0, fileName.indexOf("."))
				+"Watermarked"
				+fileName.substring(fileName.indexOf("."));
		File destImageFile=new File(uploadFilePath+"/"+tempWtrmrkName);
		addTextWatermark("Watermark", sourceImageFile,destImageFile);
		Path path = FileSystems.getDefault().getPath(uploadFilePath+"/"+fileName);
		System.out.println(path.toString());
		Files.deleteIfExists(path);
		BufferedImage bimg=ImageIO.read(destImageFile);
		int height=bimg.getHeight();
		int width=bimg.getWidth();
		request.setAttribute("fileName", tempWtrmrkName);
		request.setAttribute("height", height);
		request.setAttribute("width", width);
		request.setAttribute("filePath", filePath);
		request.setAttribute("filePath2", "uploads/"+fileName);
		request.setAttribute("filePath3", "uploads/"+tempWtrmrkName);
		request.setAttribute("message", fileName+" File uploaded successfully!");
		request.getServletContext().getRequestDispatcher("/response.jsp").forward(request,response);
		
	}
	
	/**
	 * Utility method to get file name from HTTP header content-disposition
	 */
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
	
	private static void addTextWatermark(String text, File sourceImageFile, File destImageFile){
		try{
			BufferedImage sourceImage=ImageIO.read(sourceImageFile);
			//BufferedImage img= resize(sourceImage,sourceImage.getWidth(),sourceImage.getHeight()+50);
			Graphics2D g22d= (Graphics2D) sourceImage.getGraphics();
			BufferedImage img = new BufferedImage(sourceImage.getWidth(),sourceImage.getHeight()+50,BufferedImage.TRANSLUCENT);
			Graphics2D g2d = (Graphics2D) img.createGraphics();
			g2d.setColor(Color.WHITE);
			g2d.fillRect(0, 0, img.getWidth(), img.getHeight());
			g2d.drawImage(sourceImage, 0, 0, null);
			String tempPath = sourceImageFile.getAbsolutePath();
			System.out.println(tempPath);
			System.out.println(tempPath.length());
			System.out.println(tempPath.indexOf("."));
			System.out.println(tempPath.substring(tempPath.indexOf(".")+1));
			
			//initializes necessary graphic properieties
			AlphaComposite alphaChannel= AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f);
			g2d.setComposite(alphaChannel);
			g2d.setColor(Color.BLUE);
			g2d.setFont(new Font("Arial",Font.BOLD,64));
			FontMetrics fontMetrics = g2d.getFontMetrics();
			Rectangle2D rect = fontMetrics.getStringBounds(text, g2d);
			
			int textWidth = fontMetrics.stringWidth(text);
			int imgWidth=img.getWidth();
			
			double widthRatio=(double)imgWidth/(double)textWidth;
			
			int newFontSize=(int)(64*widthRatio);
			int imgHeight=img.getHeight();
			
			int fontSizeToUse=Math.min(newFontSize, imgHeight);
			
			System.out.println("fontToUse:"+fontSizeToUse);
			g2d.setFont(new Font("Arial",Font.BOLD,fontSizeToUse));
			fontMetrics = g2d.getFontMetrics();
			rect = fontMetrics.getStringBounds(text, g2d);
			textWidth = fontMetrics.stringWidth(text);
			//calculates the coordinate where the String is painted
			int centerX=(img.getWidth()-(int)rect.getWidth())/2;
			int centerY=img.getHeight()/2;
		
			
			
			//paints the textual watermark
			//g2d.drawString(text, centerX, centerY);
			g2d.drawString(text, (img.getWidth()/2)-textWidth/2, img.getHeight());
			ImageIO.write(img, tempPath.substring(tempPath.indexOf(".")+1), destImageFile);
			g2d.dispose();
			
			System.out.println("The text watermark is added to the image");
			}catch(IOException ex){
				System.err.println(ex);
		}
	}
	
	public static BufferedImage resize(BufferedImage image, int width, int height){
		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TRANSLUCENT);
		Graphics2D g2d = (Graphics2D) bi.createGraphics();
		g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));
		g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
		g2d.drawImage(image, 0, 0, width, height, null);
		g2d.dispose();
		return bi;
	}
}