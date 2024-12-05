package com.chocopi.service;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class IFLYTekTTS {

    private static final String APP_ID = "ga549c15"; // Thay thế bằng AppID của bạn
    private static final String API_KEY = "97050b9909f23e2aba1ada2050a876c1"; // Thay thế bằng API Key của bạn
    private static final String API_URL = "https://api.xfyun.cn/v1/service/v1/tts"; // URL API iFLYTek TTS

    // Đường dẫn thư mục lưu file âm thanh
    private static final String AUDIO_FOLDER = "G:/BTL_OOP_14/Chocopi/ChocopiLibrary/src/main/resources/com/chocopi/sound/";

    public static void main(String[] args) throws IOException {
        String textToSpeak = "Chào bạn!";
        String audioFileName = "output_audio.mp3"; // Tên tệp âm thanh
        String audioPath = AUDIO_FOLDER + audioFileName;

        // Tạo thư mục nếu chưa tồn tại
        createAudioFolderIfNotExists();

        IFLYTekTTS tts = new IFLYTekTTS();
        tts.textToSpeech(textToSpeak, audioPath);

        if (Files.exists(Paths.get(audioPath))) {
            tts.readFile(audioPath);
        } else {
            System.out.println("Tệp âm thanh chưa được tạo.");
        }
    }

    public void textToSpeech(String text, String audioPath) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpPost httpPost = new HttpPost(API_URL);
        httpPost.setHeader("Content-Type", "application/json");
        httpPost.setHeader("X-Appid", APP_ID);
        httpPost.setHeader("X-CurTime", String.valueOf(System.currentTimeMillis() / 1000));
        httpPost.setHeader("X-Param", encodeParam());
        httpPost.setHeader("X-CheckSum", generateCheckSum());
        httpPost.setHeader("X-API-KEY", API_KEY);

        String jsonPayload = createJsonPayload(text);
        httpPost.setEntity(new StringEntity(jsonPayload));

        HttpResponse response = httpClient.execute(httpPost);
        int statusCode = response.getStatusLine().getStatusCode();

        if (statusCode != 200) {
            System.out.println("Lỗi API: Mã trạng thái HTTP " + statusCode);
            System.out.println("Chi tiết phản hồi: " + EntityUtils.toString(response.getEntity()));
            return;
        }

        HttpEntity responseEntity = response.getEntity();
        String responseContent = EntityUtils.toString(responseEntity);

        System.out.println("Phản hồi API: " + responseContent);

        if (responseContent.contains("audio")) {
            byte[] audioData = decodeBase64Audio(responseContent);
            saveAudioToFile(audioData, audioPath);
            System.out.println("Đã lưu âm thanh vào: " + audioPath);
        } else {
            System.out.println("Không tìm thấy dữ liệu âm thanh trong phản hồi.");
        }

        httpClient.close();
    }

    private static void createAudioFolderIfNotExists() throws IOException {
        Path folderPath = Paths.get(AUDIO_FOLDER);
        if (!Files.exists(folderPath)) {
            Files.createDirectories(folderPath);
            System.out.println("Đã tạo thư mục âm thanh: " + AUDIO_FOLDER);
        }
    }

    private String generateCheckSum() {
        String curTime = String.valueOf(System.currentTimeMillis() / 1000);
        String param = encodeParam();
        String dataToSign = API_KEY + curTime + param; // Kết hợp API_KEY, thời gian, và tham số
        return hashSHA256(dataToSign); // Mã hóa dữ liệu bằng SHA-256 (hoặc loại hash API yêu cầu)
    }

    private String hashSHA256(String data) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(data.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Lỗi mã hóa SHA-256", e);
        }
    }

    private String encodeParam() {
        try {
            // Tạo JSON chuỗi chứa các tham số cần thiết
            String paramsJson = "{\n" +
                    "  \"aue\": \"lame\",\n" + // Định dạng âm thanh (ví dụ: MP3 với lame encoder)
                    "  \"auf\": \"audio/L16;rate=16000\",\n" + // Tần số mẫu âm thanh
                    "  \"voice_name\": \"xiaoyan\",\n" + // Tên giọng đọc
                    "  \"speed\": \"50\",\n" + // Tốc độ đọc
                    "  \"volume\": \"50\",\n" + // Âm lượng
                    "  \"pitch\": \"50\"\n" + // Cao độ
                    "}";

            // Mã hóa chuỗi JSON sang Base64
            return Base64.getEncoder().encodeToString(paramsJson.getBytes("UTF-8"));
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi mã hóa tham số: " + e.getMessage(), e);
        }
    }

    private String createJsonPayload(String text) {
        if (text == null || text.trim().isEmpty()) {
            throw new IllegalArgumentException("Văn bản không được để trống.");
        }

        try {
            JSONObject payload = new JSONObject();
            payload.put("text", text);
            payload.put("lan", "zh_cn");
            payload.put("voice_name", "xiaoyan");
            payload.put("speed", "50");
            payload.put("volume", "50");
            payload.put("pitch", "50");

            return payload.toString();
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi tạo JSON payload: " + e.getMessage(), e);
        }
    }

    private byte[] decodeBase64Audio(String responseContent) {
        String base64Audio = extractAudioFromResponse(responseContent);
        return Base64.getDecoder().decode(base64Audio);
    }

    private String extractAudioFromResponse(String responseContent) {
        if (responseContent == null || responseContent.isEmpty()) {
            throw new IllegalArgumentException("Phản hồi từ API trống hoặc null");
        }

        try {
            JSONObject jsonResponse = new JSONObject(responseContent);
            if (jsonResponse.has("audio")) {
                return jsonResponse.getString("audio");
            } else {
                throw new IllegalStateException("Phản hồi không chứa trường 'audio'");
            }
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi phân tích phản hồi API: " + e.getMessage(), e);
        }
    }

    private void saveAudioToFile(byte[] audioData, String filePath) throws IOException {
        Files.write(Paths.get(filePath), audioData);
    }

    public void readFile(String filePath) throws IOException {
        byte[] fileData = Files.readAllBytes(Paths.get(filePath));
        System.out.println("Đọc file: " + filePath);
        System.out.println("Số byte trong file: " + fileData.length);
    }
}
