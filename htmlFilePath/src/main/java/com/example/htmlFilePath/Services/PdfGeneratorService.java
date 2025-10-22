package com.example.htmlFilePath.Services;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;


public class PdfGeneratorService {
	
//	public static void main(String[] args) throws FileNotFoundException, IOException, InterruptedException {
//		
//		 String htmlFilePath = "C:\\Users\\Ariantech 01\\Desktop\\IDFolder\\newhtml.html";
//         String outputPdfPath = "C:\\Users\\Ariantech 01\\Desktop\\IDFolder\\newpdf13.pdf";
//         
//         // Chrome options
//         ChromeOptions options = new ChromeOptions();
//         options.addArguments("--start-maximized");
//         options.addArguments("--disable-extensions");
//         options.addArguments("--ignore-certificate-errors");
//         options.addArguments("--disable-blink-features=AutomationControlled");
//         options.addArguments("--guest");
//         options.addArguments("--headless=new"); // Headless required for PDF
//
//         // Initialize ChromeDriver
//         ChromeDriver driver = new ChromeDriver(options);
//
//         try {
//             // Open HTML file
//             driver.get("file:///" + htmlFilePath);
//             System.out.println("✅ Loading HTML file: " + htmlFilePath);
//
//             // Wait for JS charts to render
//             Thread.sleep(5000); // or use explicit wait
//
//             // Prepare parameters for PDF
//             Map<String, Object> pdfParams = new HashMap<>();
//             pdfParams.put("printBackground", true);
//             pdfParams.put("preferCSSPageSize", true);
////             pdfParams.put("paperWidth", 8.27);   // A4 width in inches
////             pdfParams.put("paperHeight", 11.69); // A4 height in inches
////             pdfParams.put("marginTop", 0.0);     // optional margins
////             pdfParams.put("marginBottom", 0.0);
////             pdfParams.put("marginLeft", 0.1);
////             pdfParams.put("marginRight", 0.1);
//
//             // Execute CDP command to print to PDF
//             Map<String, Object> result = driver.executeCdpCommand("Page.printToPDF", pdfParams);
//
//             // Decode Base64 PDF and save
//             byte[] pdfBytes = Base64.getDecoder().decode((String) result.get("data"));
//             try (FileOutputStream fos = new FileOutputStream(outputPdfPath)) {
//                 fos.write(pdfBytes);
//             }
//
//             System.out.println("✅ PDF generated successfully: " + outputPdfPath);
//
//         } finally {
//             driver.quit();
//         }
//		
//	}
//	
	
//	public static String generatePdfFromHtml(String htmlFilePath, String outputPdfPath) throws Exception {
//		
//		
//    }

}
