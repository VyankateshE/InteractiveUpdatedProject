package com.example.htmlFilePath.Services;

import com.spire.doc.Document;
import com.spire.doc.DocumentObject;
import com.spire.doc.documents.HorizontalAlignment;
import com.spire.doc.fields.DocPicture;
import org.springframework.stereotype.Service;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import com.spire.doc.FileFormat;



@Service
public class HtmlToDocRtfService {
	
	
	
	public byte[] convertHtmlToDoc(InputStream htmlInputStream) throws Exception {
        Document document = new Document();
        document.loadFromStream(htmlInputStream, FileFormat.Html);

//        centerAndResizeImages(document);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        document.saveToStream(outputStream, FileFormat.Doc);
        return outputStream.toByteArray();
    }

    public byte[] convertHtmlToRtf(InputStream htmlInputStream) throws Exception {
        Document document = new Document();
//        document.loadFromStream(htmlInputStream, FileFormat.Html);
        document.loadFromStream(htmlInputStream, FileFormat.Doc);
//        centerAndResizeImages(document);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        document.saveToStream(outputStream, FileFormat.Rtf);
        return outputStream.toByteArray();
    }
    
    
        public byte[] convertDocToRtf(InputStream docInputStream) throws Exception {
        Document document = new Document();
        document.loadFromStream(docInputStream, FileFormat.Doc);  // Changed to FileFormat.Doc
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        document.saveToStream(outputStream, FileFormat.Rtf);
        return outputStream.toByteArray();
    }

    // Convert HTML InputStream to DOC bytes
//    public byte[] convertHtmlToDoc(InputStream htmlInputStream) throws Exception {
//        Document document = new Document();
//        document.loadFromStream(htmlInputStream, FileFormat.Html);
//
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        document.saveToStream(outputStream, FileFormat.Doc);
//        return outputStream.toByteArray();
//    }
//	
//	public byte[] convertHtmlToRtf(InputStream htmlInputStream) throws Exception {
//        Document document = new Document();
//        document.loadFromStream(htmlInputStream, FileFormat.Html);
//
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        document.saveToStream(outputStream, FileFormat.Rtf);
//        return outputStream.toByteArray();
//    }
	
	
	
    

    // Helper method to center and resize all images
//    private void centerAndResizeImages(Document document) {
//        for (int i = 0; i < document.getSections().getCount(); i++) {
//            var section = document.getSections().get(i);
//            for (int j = 0; j < section.getParagraphs().getCount(); j++) {
//                var paragraph = section.getParagraphs().get(j);
//                if (paragraph.getChildObjects().getCount() > 0) {
//                	for (int k = 0; k < paragraph.getChildObjects().getCount(); k++) {
//                	    DocumentObject obj = paragraph.getChildObjects().get(k);
//                	    if (obj instanceof DocPicture picture) {
//                	        paragraph.getFormat().setHorizontalAlignment(HorizontalAlignment.Center);
//
//                	        if (picture.getWidth() > 400) {
//                	            float aspectRatio = picture.getHeight() / picture.getWidth();
//                	            picture.setWidth(400);
//                	            picture.setHeight(400 * aspectRatio);
//                	        }
//                	    }
//                	}
//                }
//            }
//        }
//    }

    
    
    

    
    
    
	
	
	
	
	
    

//    private static final int MAX_IMAGE_WIDTH = 400;
//
//    // Convert HTML InputStream to DOC
//    public byte[] convertHtmlToDoc(InputStream htmlInputStream) throws Exception {
//        String html = prepareHtml(htmlInputStream);
//
//        Document document = new Document();
//        document.loadFromStream(new ByteArrayInputStream(html.getBytes(StandardCharsets.UTF_8)), FileFormat.Html);
//
//        centerAndResizeImages(document);
//
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        document.saveToStream(outputStream, FileFormat.Doc);
//        return outputStream.toByteArray();
//    }
//
//    // Convert HTML InputStream to RTF
//    public byte[] convertHtmlToRtf(InputStream htmlInputStream) throws Exception {
//        String html = prepareHtml(htmlInputStream);
//
//        Document document = new Document();
//        document.loadFromStream(new ByteArrayInputStream(html.getBytes(StandardCharsets.UTF_8)), FileFormat.Html);
//
//        centerAndResizeImages(document);
//
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        document.saveToStream(outputStream, FileFormat.Rtf);
//        return outputStream.toByteArray();
//    }
//
//    // Inject CSS to remove table borders and center images
//    private String prepareHtml(InputStream htmlInputStream) throws Exception {
//        String html = new String(htmlInputStream.readAllBytes(), StandardCharsets.UTF_8);
//
//        String style = "<style>"
//                + "table { border-collapse: collapse; border: none; } "
//                + "td, th { border: none; padding: 0; } "
//                + "img { display: block; margin: 0 auto; max-width: " + MAX_IMAGE_WIDTH + "px; height: auto; border: none; }"
//                + "</style>";
//
//        return style + html;
//    }
//
//    // Center & resize images
//    private void centerAndResizeImages(Document document) {
//        for (int i = 0; i < document.getSections().getCount(); i++) {
//            Section section = document.getSections().get(i);
//            for (int j = 0; j < section.getParagraphs().getCount(); j++) {
//                Paragraph paragraph = section.getParagraphs().get(j);
//
//                for (int k = 0; k < paragraph.getChildObjects().getCount(); k++) {
//                    DocumentObject obj = paragraph.getChildObjects().get(k);
//                    if (obj instanceof DocPicture picture) {
//                        paragraph.getFormat().setHorizontalAlignment(HorizontalAlignment.Center);
//
//                        // Resize if width > MAX_IMAGE_WIDTH
//                        if (picture.getWidth() > MAX_IMAGE_WIDTH) {
//                            float aspectRatio = picture.getHeight() / picture.getWidth();
//                            picture.setWidth(MAX_IMAGE_WIDTH);
//                            picture.setHeight(MAX_IMAGE_WIDTH * aspectRatio);
//                        }
//                    }
//                }
//            }
//        }
//    }
//    
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
    
    
  //for maplinks tried not working
//  	public byte[] convertHtmlToDoc(InputStream htmlInputStream) throws Exception {
//  	    String html = new String(htmlInputStream.readAllBytes(), StandardCharsets.UTF_8);
//
//  	    // Replace Google Maps iframe with clickable link
//  	    html = replaceGoogleMapsIframeWithPlaceholder(html);
//
//  	    Document document = new Document();
//  	    // Load from the modified HTML string
//  	    document.loadFromStream(new ByteArrayInputStream(html.getBytes(StandardCharsets.UTF_8)), FileFormat.Html);
//
//  	    centerAndResizeImages(document);
//
//  	    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//  	    document.saveToStream(outputStream, FileFormat.Doc);
//  	    return outputStream.toByteArray();
//  	}
//
//  	public byte[] convertHtmlToRtf(InputStream htmlInputStream) throws Exception {
//  	    String html = new String(htmlInputStream.readAllBytes(), StandardCharsets.UTF_8);
//
//  	    // Replace Google Maps iframe with clickable link
//  	    html = replaceGoogleMapsIframeWithPlaceholder(html);
//
//  	    Document document = new Document();
//  	    // Load from the modified HTML string
//  	    document.loadFromStream(new ByteArrayInputStream(html.getBytes(StandardCharsets.UTF_8)), FileFormat.Html);
//
//  	    centerAndResizeImages(document);
//
//  	    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//  	    document.saveToStream(outputStream, FileFormat.Rtf);
//  	    return outputStream.toByteArray();
//  	}
//
//      // Helper method to center and resize all images
//      private void centerAndResizeImages(Document document) {
//          for (int i = 0; i < document.getSections().getCount(); i++) {
//              var section = document.getSections().get(i);
//              for (int j = 0; j < section.getParagraphs().getCount(); j++) {
//                  var paragraph = section.getParagraphs().get(j);
//                  if (paragraph.getChildObjects().getCount() > 0) {
//                  	for (int k = 0; k < paragraph.getChildObjects().getCount(); k++) {
//                  	    DocumentObject obj = paragraph.getChildObjects().get(k);
//                  	    if (obj instanceof DocPicture picture) {
//                  	        // Center the paragraph containing the image
//                  	        paragraph.getFormat().setHorizontalAlignment(HorizontalAlignment.Center);
//
//                  	        // Resize large images (optional)
//                  	        if (picture.getWidth() > 400) {
//                  	            float aspectRatio = picture.getHeight() / picture.getWidth();
//                  	            picture.setWidth(400);
//                  	            picture.setHeight(400 * aspectRatio);
//                  	        }
//                  	    }
//                  	}
//                  }
//              }
//          }
//      }
//
//      
//      
//      private String replaceGoogleMapsIframeWithPlaceholder(String html) {
//          Pattern iframePattern = Pattern.compile(
//                  "<iframe[^>]*src=[\"']([^\"']*maps\\.google\\.com[^\"']*)[\"'][^>]*></iframe>",
//                  Pattern.CASE_INSENSITIVE
//          );
//          Matcher matcher = iframePattern.matcher(html);
//          StringBuffer sb = new StringBuffer();
//
//          while (matcher.find()) {
//              String placeholderImg = "<p>[Google Map cannot be displayed in Word]</p>";
//              matcher.appendReplacement(sb, Matcher.quoteReplacement(placeholderImg));
//          }
//          matcher.appendTail(sb);
//          return sb.toString();
//      }
    
}