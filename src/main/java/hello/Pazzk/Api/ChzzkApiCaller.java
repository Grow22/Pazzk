package hello.Pazzk.Api;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


public class ChzzkApiCaller {


    public static String getThumbnailUrl(String clipId) {
        // 비공식 클립 API 엔드포인트
        String url = "https://api.chzzk.naver.com/service/v1/videos/" + clipId;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/537.36 Safari/537.36")
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // API 호출 성공 시
            if (response.statusCode() == 200) {
                // JSON 파싱을 위한 ObjectMapper
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode rootNode = objectMapper.readTree(response.body());

                // 'content' 노드에서 'thumbnailUrl' 추출
                JsonNode contentNode = rootNode.get("content");
                if (contentNode != null) {
                    JsonNode thumbnailUrlNode = contentNode.get("thumbnailUrl");
                    if (thumbnailUrlNode != null) {
                        return thumbnailUrlNode.asText();
                    }
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null; // 썸네일을 가져오지 못한 경우 null 반환
    }

    public static void main(String[] args) {
        // 예시 클립 ID
        String clipId = "clp_1j6Fw7LhQc_t";
        String thumbnailUrl = getThumbnailUrl(clipId);

        if (thumbnailUrl != null) {
            System.out.println("클립 썸네일 URL: " + thumbnailUrl);
        } else {
            System.out.println("썸네일 URL을 가져오는 데 실패했습니다.");
        }
    }


}

