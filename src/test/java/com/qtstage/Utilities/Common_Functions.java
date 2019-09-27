package com.qtstage.Utilities;

import static org.monte.media.FormatKeys.EncodingKey;

import static org.monte.media.FormatKeys.FrameRateKey;
import static org.monte.media.FormatKeys.KeyFrameIntervalKey;
import static org.monte.media.FormatKeys.MediaTypeKey;
import static org.monte.media.FormatKeys.MimeTypeKey;
import static org.monte.media.VideoFormatKeys.CompressorNameKey;
import static org.monte.media.VideoFormatKeys.DepthKey;
import static org.monte.media.VideoFormatKeys.ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE;
import static org.monte.media.VideoFormatKeys.QualityKey;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.io.BufferedInputStream;


import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
//import com.asprise.util.ocr.OCR;



import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.io.FileUtils;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessRead;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.monte.media.Format;
import org.monte.media.FormatKeys;
import org.monte.media.FormatKeys.MediaType;
import org.monte.media.VideoFormatKeys;
import org.monte.media.math.Rational;
import org.monte.screenrecorder.ScreenRecorder;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.w3c.dom.Document;

@SuppressWarnings("unused")
public class Common_Functions {
		
	private ScreenRecorder screenRecorder;

	static String snappath=null;	
	
	public void startRecording() throws Exception
    {
		GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
		   this.screenRecorder = new ScreenRecorder(gc,
		           gc.getBounds(),
		           new Format(MediaTypeKey, MediaType.FILE, MimeTypeKey, FormatKeys.MIME_QUICKTIME),
		           new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, VideoFormatKeys.ENCODING_QUICKTIME_ANIMATION,
		                   CompressorNameKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
		                   DepthKey, 24, FrameRateKey, Rational.valueOf(15),
		                   QualityKey, 1.0f,
		                   KeyFrameIntervalKey, 15 * 60),
		           new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, "black", FrameRateKey, Rational.valueOf(30)),
		           null,
		           new File(Constants.videoFolder));
		  // new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, "black", FrameRateKey, Rational.valueOf(30)), null,new File(targetFolder));    
		   this.screenRecorder.start();        
}
 public void stopRecording() throws Exception
    {
		Thread.sleep(2000);

      this.screenRecorder.stop();
    }
	
	public String GetXMLTagValue(String xmlpath, String tagname){
		
		String val=null;

		
		try {
			
			// Method to get the xml tag value from any given xml
				
			File f = new File(xmlpath);
			
			DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			
			Document doc = docBuilder.parse(f);
			
			val = doc.getElementsByTagName(tagname).item(0).getTextContent();
			
				
			}
		 catch (Exception e) {
			e.printStackTrace();
		}
		
		return val;
		
	}
	
	public By locatorParser(String locator) {

		By loc = By.id(locator);

		if (locator.contains("id"))
		    loc = By.id(locator.substring(locator.indexOf("\"") + 1,
		        locator.length() - 2));

		else if (locator.contains("name"))
		    loc = By.name(locator.substring(locator.indexOf("\"") + 1,
		        locator.length() - 2));
		else if (locator.contains("cssSelector"))
		    loc = By.cssSelector(locator.substring(locator.indexOf("\"") + 1,
		        locator.length() - 2));

		if (locator.contains("xpath"))
		    loc = By.xpath(locator.substring(locator.indexOf("\"") + 1,
		        locator.length() - 2));

		return loc;

		}
	
	public static String captureScreenshot(WebDriver driver){
		
		try{
			
			File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
	        //The below method will save the screen shot in d drive with name "screenshot.png"
			String name = scrFile.getName();
            FileUtils.copyFile(scrFile, new File(Constants.snapshotsPath+name));
            String newPath = System.getProperty("user.dir") +"/";
            snappath = newPath+Constants.snapshotsPath+name;
            
		}catch(Exception e){
			
			System.out.println("Issue with snapshot capture");
			
		}
		return snappath;
	}

	//System.out.println(getLatestFilefromDir(home+"/Downloads/"));
	
private static String getLatestFilefromDir(String dirPath){
    File dir = new File(dirPath);
    File[] files = dir.listFiles();
    if (files == null || files.length == 0) {
        return null;
    }

    File lastModifiedFile = files[0];
    for (int i = 1; i < files.length; i++) {
       if (lastModifiedFile.lastModified() < files[i].lastModified()) {
           lastModifiedFile = files[i];
       }
    }
    return lastModifiedFile.getName();
}

public static void ImageReader(WebDriver driver) throws IOException, InterruptedException{
	System.setProperty("webdriver.chrome.driver", "E://chromedriver.exe");
	 driver = new ChromeDriver();


driver.get("http://2.bp.blogspot.com/-42SgMHAeF8U/Uk8QlYCoy-I/AAAAAAAADSA/TTAVAAgDhio/s1600/love.jpg");
Thread.sleep(1000);
driver.manage().window().maximize();
driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
Thread.sleep(2000);
String imageUrl=driver.findElement(By.xpath("/html/body/img")).getAttribute("src");
System.out.println("Image source path : \n"+ imageUrl);

URL url = new URL(imageUrl);
Image image = ImageIO.read(url);
/*//String s = new OCR().recognizeCharacters((RenderedImage) image);
System.out.println("Text From Image : \n"+ s);
System.out.println("Length of total text : \n"+ s.length());*/
driver.quit();
	
}
@SuppressWarnings({ "rawtypes", "resource" })
public static void WordReader(){
	
	
	try {

		String fileName = "E:\\documents\\abc.docx";
		FileInputStream fis = new FileInputStream(fileName);
		XWPFDocument xdoc = new XWPFDocument(OPCPackage.open(fis));
		Iterator bodyElementIterator = xdoc.getBodyElementsIterator();
		while (bodyElementIterator.hasNext()) {
			IBodyElement element = (IBodyElement) bodyElementIterator.next();

			if ("TABLE".equalsIgnoreCase(element.getElementType().name())) {
				List<XWPFTable> tableList = element.getBody().getTables();
				
				for (XWPFTable table : tableList) {
					//System.out.println("Total Number of Rows of Table:" + table.getNumberOfRows());
					//System.out.println(table.getRow(0).getCell(0).getText());
					//System.out.println(table.getRow(0).getCell(1).getText());

					// for (int i = 0; i < table.getRows().size(); i++) {
					int i = 0;
					int noCols=table.getRow(i).getTableCells().size();
					
					Map<String,String> map=new HashMap<String,String>();
					for (int j = 0; j < noCols; j++) {
					//	System.out.println(table.getRow(i).getCell(j).getText());

						map.put(table.getRow(0).getCell(j).getText().trim(), table.getRow(1).getCell(j).getText());
					/*	if (table.getRow(0).getCell(j).getText().trim().equalsIgnoreCase("Second")) {
							System.out.println(table.getRow(j).getCell(1).getText());

						}*/

						// }
					}
					
					
				//	System.out.println("Out put");
					
					for (Map.Entry m : map.entrySet()) {
				//		System.out.println("out key="+m.getKey()+"  -   out value="+m.getValue());
					}
					
					Scanner sc=new Scanner(System.in);
				System.out.print("column name  ");
					String con=sc.next();
					
					System.out.println(getData(map,con));

				}
			}
		}
	}

	catch (Exception ex) {
		ex.printStackTrace();
	}
	
}

private static String getData(Map<String, String> map, String con) {
	String str=null;
	str=map.get(con);
	

	return str;
	
	
}
	

}