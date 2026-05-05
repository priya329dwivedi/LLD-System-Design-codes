package org.designpattern.practiceQuestions.ArtifactSyncClient.http;

import java.util.HashMap;
import java.util.Map;

public class ApiResponse {

    private final int statusCode;
    private final String body;
    private final Map<String, String> headers;

    private ApiResponse(int statusCode, String body, Map<String, String> headers) {
        this.statusCode = statusCode;
        this.body = body;
        this.headers = headers;
    }

    public static ApiResponse ok(String body) {
        return new ApiResponse(200, body, new HashMap<>());
    }

    public static ApiResponse tooManyRequests(long retryAfterSeconds) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Retry-After", String.valueOf(retryAfterSeconds));
        return new ApiResponse(429, "Too Many Requests", headers);
    }

    public String getHeader(String name) {
        return headers.get(name);
    }

    public int getStatusCode()      { return statusCode; }
    public String getBody()         { return body; }
    public boolean isSuccess()      { return statusCode >= 200 && statusCode < 300; }
    public boolean isRateLimited()  { return statusCode == 429; }
}
