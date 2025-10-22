package com.example.htmlFilePath.Services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.mime.MultipartEntityBuilder;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;

@Service
public class CloudConvertService {

    @Value("${cloudconvert.api.key}")
    private String apiKey;

    private static final String JOB_URL = "https://api.cloudconvert.com/v2/jobs";
    private static final int MAX_PARALLEL_JOBS = 1; // adjust based on your plan

    public byte[] convertHtmlToRtf(MultipartFile htmlFile) throws Exception {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            ObjectMapper mapper = new ObjectMapper();

            // ✅ 0. Wait until there is a free job slot
            while (true) {
                HttpGet getJobs = new HttpGet(JOB_URL);
                getJobs.addHeader("Authorization", "Bearer " + apiKey);
                var getJobsResponse = client.execute(getJobs);
                String jobsBody = EntityUtils.toString(getJobsResponse.getEntity());
                JsonNode jobsJson = mapper.readTree(jobsBody);

                long runningJobs = jobsJson.at("/data").findValues("status").stream()
                        .filter(status -> status.asText().equalsIgnoreCase("waiting") ||
                                          status.asText().equalsIgnoreCase("processing"))
                        .count();

                if (runningJobs < MAX_PARALLEL_JOBS) break; // free slot found
                Thread.sleep(2000); // wait 2 sec before checking again
            }

            // 1️⃣ Create Job
            HttpPost postJob = new HttpPost(JOB_URL);
            postJob.addHeader("Authorization", "Bearer " + apiKey);
            postJob.addHeader("Content-Type", "application/json");

            String jobPayload = """
            {
              "tasks": {
                "import-my-file": {
                  "operation": "import/upload"
                },
                "convert-my-file": {
                  "operation": "convert",
                  "input": "import-my-file",
                  "input_format": "html",
                  "output_format": "rtf"
                },
                "export-my-file": {
                  "operation": "export/url",
                  "input": "convert-my-file"
                }
              }
            }
            """;

            postJob.setEntity(new org.apache.hc.core5.http.io.entity.StringEntity(jobPayload));
            var jobResponse = client.execute(postJob);
            var jobBody = EntityUtils.toString(jobResponse.getEntity());

            if (jobResponse.getCode() != 201) {
                throw new RuntimeException("CloudConvert Job creation failed: " + jobBody);
            }

            JsonNode jobJson = mapper.readTree(jobBody);
            String jobId = jobJson.at("/data/id").asText();

            // 2️⃣ Get import task
            JsonNode importTask = jobJson.at("/data/tasks").findValue("import-my-file");
            String formUrl = importTask.at("/result/form/url").asText();
            JsonNode formParams = importTask.at("/result/form/parameters");

            if (formUrl == null || formUrl.isEmpty()) {
                throw new RuntimeException("Failed to get upload form URL.");
            }

            // 3️⃣ Upload file
            HttpPost uploadPost = new HttpPost(formUrl);
            MultipartEntityBuilder uploadBuilder = MultipartEntityBuilder.create();

            Iterator<Map.Entry<String, JsonNode>> fields = formParams.fields();
            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> entry = fields.next();
                uploadBuilder.addTextBody(entry.getKey(), entry.getValue().asText());
            }

            uploadBuilder.addBinaryBody("file", htmlFile.getInputStream(),
                    org.apache.hc.core5.http.ContentType.TEXT_HTML, htmlFile.getOriginalFilename());

            uploadPost.setEntity(uploadBuilder.build());
            var uploadResponse = client.execute(uploadPost);
            EntityUtils.consume(uploadResponse.getEntity());

            // 4️⃣ Poll for export URL
            String exportUrl = null;
            int attempts = 0;
            while (attempts++ < 30) {
                Thread.sleep(2000); // wait 2s
                HttpGet getJobStatus = new HttpGet(JOB_URL + "/" + jobId);
                getJobStatus.addHeader("Authorization", "Bearer " + apiKey);
                var statusResp = client.execute(getJobStatus);
                var statusBody = EntityUtils.toString(statusResp.getEntity());
                JsonNode statusJson = mapper.readTree(statusBody);
                JsonNode exportFiles = statusJson.at("/data/tasks/2/result/files");

                if (exportFiles.isArray() && exportFiles.size() > 0) {
                    exportUrl = exportFiles.get(0).get("url").asText();
                    if (exportUrl != null && !exportUrl.isEmpty()) break;
                }
            }

            if (exportUrl == null || exportUrl.isEmpty()) {
                throw new RuntimeException("Failed to get export URL after polling.");
            }

            // 5️⃣ Download RTF
            try (InputStream in = new URL(exportUrl).openStream();
                 ByteArrayOutputStream out = new ByteArrayOutputStream()) {
                in.transferTo(out);
                return out.toByteArray();
            }
        }
    }
}
