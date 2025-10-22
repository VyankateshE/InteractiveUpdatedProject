package com.example.htmlFilePath.Services;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class WordConversionService {

    public byte[] convertDocToRtf(File inputFile) throws Exception {
        // Temporary output file
        Path outputPath = Files.createTempFile("converted-", ".rtf");
        String outputFile = outputPath.toAbsolutePath().toString();

        ActiveXComponent word = new ActiveXComponent("Word.Application");
        try {
            word.setProperty("Visible", new Variant(false));
            Dispatch documents = word.getProperty("Documents").toDispatch();

            Dispatch document = Dispatch.call(documents, "Open",
                    inputFile.getAbsolutePath(), false, true).toDispatch();

            Dispatch.call(document, "SaveAs", outputFile, new Variant(6));

            Dispatch.call(document, "Close", false);
        } finally {
            word.invoke("Quit", 0);
        }

        byte[] data = Files.readAllBytes(outputPath);
        Files.deleteIfExists(outputPath);
        return data;
    }
}
