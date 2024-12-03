package com.chocopi.service;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class IFLYTekTTS {

    private static final String APP_ID = "ga549c15"; // Thay thế bằng AppID của bạn
    private static final String API_KEY = "97050b9909f23e2aba1ada2050a876c1"; // Thay thế bằng API Key của bạn
    private static final String API_URL = "https://api.xfyun.cn/v1/service/v1/tts"; // URL API iFLYTek TTS

    public static void main(String[] args) throws IOException {
        String textToSpeak = "Chào mừng bạn đến với thư viện Chocopi!";
        String audioPath = "output_audio.mp3";  // Đảm bảo đường dẫn đúng với nơi bạn lưu file

        IFLYTekTTS tts = new IFLYTekTTS();
        tts.textToSpeech(textToSpeak, audioPath);
        tts.readFile(audioPath);  // Gọi phương thức để đọc và in nội dung file âm thanh
    }

    public void textToSpeech(String text, String audioPath) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpPost httpPost = new HttpPost(API_URL);
        httpPost.setHeader("Content-Type", "application/json");
        httpPost.setHeader("X-Appid", APP_ID);
        httpPost.setHeader("X-CurTime", String.valueOf(System.currentTimeMillis() / 1000));
        httpPost.setHeader("X-Param", encodeParam());
        httpPost.setHeader("X-CheckSum", generateCheckSum());

        String jsonPayload = createJsonPayload(text);

        StringEntity entity = new StringEntity(jsonPayload);
        httpPost.setEntity(entity);

        HttpResponse response = httpClient.execute(httpPost);

        HttpEntity responseEntity = response.getEntity();
        String responseContent = EntityUtils.toString(responseEntity);

        if (responseContent.contains("audio")) {
            byte[] audioData = decodeBase64Audio(responseContent);
            saveAudioToFile(audioData, audioPath);
            System.out.println("Đã lưu âm thanh vào: " + audioPath);
        }

        // Đóng kết nối
        httpClient.close();
    }

    // Tạo mã checksum (Hàm này có thể khác nhau tùy thuộc vào cách bạn cần mã hóa thông tin của iFLYTek)
    private String generateCheckSum() {
        return "YOUR_CHECKSUM";
    }

    // Mã hóa các tham số
    private String encodeParam() {
        return "YOUR_ENCODED_PARAM";
    }

    // Tạo JSON payload
    private String createJsonPayload(String text) {
        return "{\n" +
                "  \"text\": \"" + text + "\",\n" +
                "  \"lan\": \"zh_cn\",\n" +
                "  \"voice_name\": \"xiaoyan\",\n" +
                "  \"speed\": \"50\",\n" +
                "  \"volume\": \"50\",\n" +
                "  \"pitch\": \"50\"\n" +
                "}";
    }

    // Giải mã Base64 âm thanh và lưu vào file
    private byte[] decodeBase64Audio(String responseContent) {
        // Giải mã Base64 và trả về dữ liệu âm thanh
        return new byte[0];  // Cần xử lý theo API trả về
    }

    private void saveAudioToFile(byte[] audioData, String filePath) throws IOException {
        // Lưu dữ liệu âm thanh vào file (ví dụ: MP3)
        java.nio.file.Files.write(java.nio.file.Paths.get(filePath), audioData);
    }

    // Phương thức đọc file và in ra nội dung để debug
    public void readFile(String filePath) throws IOException {
        // Đọc nội dung file vào mảng byte
        byte[] fileData = Files.readAllBytes(Paths.get(filePath));

        // In ra số byte trong file để xác nhận nội dung đã được ghi
        System.out.println("Đọc file: " + filePath);
        System.out.println("Số byte trong file: " + fileData.length);

        // Nếu là file âm thanh MP3, bạn không thể hiển thị trực tiếp nội dung, nhưng có thể kiểm tra dữ liệu byte
        System.out.println("Dữ liệu file (bytes đầu tiên): " + new String(fileData, 0, Math.min(100, fileData.length)));
    }
}
