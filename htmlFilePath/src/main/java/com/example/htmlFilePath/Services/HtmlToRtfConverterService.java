package com.example.htmlFilePath.Services;

import org.springframework.stereotype.Service;

import com.aspose.words.SaveFormat;
import com.groupdocs.conversion.Converter;
import com.groupdocs.conversion.licensing.License;
import com.groupdocs.conversion.options.convert.WordProcessingConvertOptions;

import javax.swing.text.*;
import javax.swing.text.html.*;
import javax.swing.text.rtf.RTFEditorKit;
import java.io.*;
import java.net.URL;
//import com.lowagie.text.Document;
//import com.lowagie.text.Font;
//import com.lowagie.text.Image;
//import com.lowagie.text.Paragraph;
//import com.lowagie.text.Table;
//import com.lowagie.text.rtf.RtfWriter2;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//
//import javax.imageio.ImageIO;
//import java.awt.image.BufferedImage;
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.net.URL;
//
//
//import com.groupdocs.conversion.Converter;
//import com.groupdocs.conversion.options.convert.WordProcessingConvertOptions



@Service
public class HtmlToRtfConverterService {

	public byte[] SwingconvertHtmlToRtf(InputStream htmlInputStream, String originalFileName) throws Exception {
		// Create temporary files for processing
		File tempHtml = File.createTempFile("input-", ".html");
		File tempRtf = File.createTempFile("output-", ".rtf");

		// Write uploaded HTML to temporary file
		try (FileOutputStream fos = new FileOutputStream(tempHtml)) {
			htmlInputStream.transferTo(fos);
		}

		// Convert using the inbuilt logic
		convertHtmlFileToRtf(tempHtml, tempRtf);

		// Read RTF result as byte array
		byte[] rtfBytes = java.nio.file.Files.readAllBytes(tempRtf.toPath());

		// Cleanup temporary files
		tempHtml.delete();
		tempRtf.delete();

		return rtfBytes;
	}

	private void convertHtmlFileToRtf(File htmlFile, File rtfFile) throws Exception {
		HTMLEditorKit htmlKit = new HTMLEditorKit();
		HTMLDocument htmlDoc = (HTMLDocument) htmlKit.createDefaultDocument();

		// Set base path to resolve relative URLs
		URL base = htmlFile.getParentFile() != null ? htmlFile.getParentFile().toURI().toURL()
				: htmlFile.toURI().toURL();
		htmlDoc.setBase(base);

		try (FileReader fr = new FileReader(htmlFile)) {
			htmlKit.read(fr, htmlDoc, 0);
		}

		RTFEditorKit rtfKit = new RTFEditorKit();
		try (FileOutputStream fos = new FileOutputStream(rtfFile);
				BufferedOutputStream bos = new BufferedOutputStream(fos)) {
			rtfKit.write(bos, htmlDoc, 0, htmlDoc.getLength());
			bos.flush();
		}
	}

	public byte[] convertHtmlToRtf2(InputStream htmlStream) throws Exception {
	    // Load HTML into Aspose Document
	    com.aspose.words.Document doc = new com.aspose.words.Document(htmlStream);

	    // Save as RTF to a ByteArrayOutputStream
	    ByteArrayOutputStream rtfStream = new ByteArrayOutputStream();
	    doc.save(rtfStream, com.aspose.words.SaveFormat.RTF);

	    // Convert RTF bytes to string
	    String rtfContent = rtfStream.toString("UTF-8");

	    // Remove all Aspose evaluation watermark text using regex
	    // (?s) -> dot matches line breaks, \\Q...\\E -> escape special chars
	    String[] watermarks = new String[] {
	        "Evaluation Only. Created with Aspose.Words. Copyright 2003-2025 Aspose Pty Ltd",
	        "Created with an evaluation copy of Aspose.Words. To remove all limitations, you can use Free Temporary License https://products.aspose.com/words/temporary-license/"
	    };

	    for (String watermark : watermarks) {
	        rtfContent = rtfContent.replaceAll("(?s)\\Q" + watermark + "\\E", "");
	    }

	    // Return cleaned RTF as bytes
	    return rtfContent.getBytes("UTF-8");
	}


	public byte[] convertHtmlToRtf3(InputStream htmlStream) throws IOException {
//Save the uploaded HTML temporarily
		File tempHtml = File.createTempFile("upload-", ".html");
		try (FileOutputStream fos = new FileOutputStream(tempHtml)) {
			htmlStream.transferTo(fos);
		}

//Initialize converter
		Converter converter = new Converter(tempHtml.getAbsolutePath());

//Set WordProcessing options (no need for FileType.RTF)
		WordProcessingConvertOptions options = new WordProcessingConvertOptions();

//Output temp RTF file
		File tempRtf = File.createTempFile("converted-", ".rtf");

//Convert
		converter.convert(tempRtf.getAbsolutePath(), options);

//Read RTF bytes
		byte[] rtfBytes = java.nio.file.Files.readAllBytes(tempRtf.toPath());

//Clean up temp files
		tempHtml.delete();
		tempRtf.delete();

		return rtfBytes;
	}
	
	
}

//
//public byte[] convertHtmlToRtf(String htmlContent) throws Exception {
//Document document = new Document();
//ByteArrayOutputStream baos = new ByteArrayOutputStream();
//RtfWriter2.getInstance(document, baos);
//document.open();
//
//org.jsoup.nodes.Document htmlDoc = Jsoup.parse(htmlContent);
//Elements elements = htmlDoc.body().children();
//
//for (Element element : elements) {
//  switch (element.tagName().toLowerCase()) {
//      case "p":
//          document.add(new Paragraph(element.text()));
//          break;
//      case "h1":
//          Paragraph h1 = new Paragraph(element.text());
//          h1.setFont(new Font(Font.HELVETICA, 24, Font.BOLD));
//          document.add(h1);
//          break;
//      case "h2":
//          Paragraph h2 = new Paragraph(element.text());
//          h2.setFont(new Font(Font.HELVETICA, 18, Font.BOLD));
//          document.add(h2);
//          break;
//      case "img":
//          String src = element.attr("src");
//          if (!src.isEmpty()) {
//              try {
//                  BufferedImage bufferedImage = ImageIO.read(new URL(src));
//                  Image image = Image.getInstance(bufferedImage, null);
//                  image.scaleToFit(300, 300); // Adjust size as needed
//                  document.add(image);
//              } catch (Exception e) {
//                  document.add(new Paragraph("Failed to load image: " + src));
//              }
//          }
//          break;
//      case "table":
//          Elements rows = element.select("tr");
//          if (!rows.isEmpty()) {
//              int columnCount = rows.first().select("td, th").size();
//              Table table = new Table(columnCount);
//              for (Element row : rows) {
//                  Elements cells = row.select("td, th");
//                  for (Element cell : cells) {
//                      table.addCell(cell.text());
//                  }
////                  table.endRow();
//              }
//              document.add(table);
//          }
//          break;
//      case "ul":
//      case "ol":
//          for (Element li : element.select("li")) {
//              document.add(new Paragraph("• " + li.text()));
//          }
//          break;
//      default:
//          document.add(new Paragraph(element.text()));
//          break;
//  }
//}
//
//// Close the document and write to byte array
//document.close();
//return baos.toByteArray();
//}
//
//
//
//

//String apiKey="eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIxIiwianRpIjoiMjQ3MWQ3YjQwNjhjZmQ0YjRlMjU1MzQzMDlkYjQwN2FkNWZjYTVlM2NjNGE5ZTVhYzUyMjcwM2E5OGJhMzFjNzIxNzhkMWQ0ODU1ODZhYjYiLCJpYXQiOjE3NjA2NzcyOTUuODA4ODA4LCJuYmYiOjE3NjA2NzcyOTUuODA4ODA5LCJleHAiOjQ5MTYzNTA4OTUuODA0MDU3LCJzdWIiOiI3MzIxMTc5NyIsInNjb3BlcyI6WyJ1c2VyLnJlYWQiLCJ1c2VyLndyaXRlIiwidGFzay5yZWFkIiwidGFzay53cml0ZSIsIndlYmhvb2sucmVhZCIsIndlYmhvb2sud3JpdGUiLCJwcmVzZXQucmVhZCIsInByZXNldC53cml0ZSJdfQ.HsOKm-hGcVF1ORnPABaMqBBcg8UeovPqSUx5XK059-LzcdSl3KWlqjIhovTaig2EDi2Ja5iLw2Hmk8R29Snjv1_H3WK8fctfl9XYV2wKc6AmHZGQpgsbEv0uJtK4Kgq-v0J11GCUuS66niBgTr4BJiaZCytQK5iN0oyAlbevaz3oUZUo1LjhRPutOIAxEzi63pYqSx6PbeJ3IBg6xmngwEPyJRLaWN_OPkW-RHiBoeug3FW7kYwazf8WJ7h9sd3JQpkrDpbPcF_6FB8Aid_TkGnxWOkBun6uyVLyxr33vbTXA2n_wasznq0vmnXlaffT6UP0ZUyp2MnjRwk45za_eAIQs8y0mZ-ot4xv6v9GX9ljqIElHopX-jXjcvnKWLo7lFvNmLfnKUqZxPSfnwuOvixT3iVLRek82agtHHWQfHmj1Sfjr-H6s8CKO0wyM8USt3gmsg6ryA91UsGKinQ-OI5dIgBHnBuM7yQ0uwrNZDdeRMmaE5A6AJcjVdXyl5J3uqmtdJqo4zaM4CpYoijj5juealbta6V2dv9YAMBe2Q3X5-sMGgBaPH_de6ni25i00oLBBDmxqXMzjNjqZ-RGK-C3khl3dlwZFCgqdrE1arC6hGTPjB_Mub51T1QhfvasiZKwfXqMZ9ATW4eqekhWgu7ingy8chBkFr7FyDeQduI\r\n"
//+ "";
