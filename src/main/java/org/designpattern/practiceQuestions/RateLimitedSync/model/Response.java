package org.designpattern.practiceQuestions.RateLimitedSync.model;

public class Response {

    private final int    status;
    private final String body;
    private final long   retryAfterMs; // -1 if not provided

    private Response(int status, String body, long retryAfterMs) {
        this.status       = status;
        this.body         = body;
        this.retryAfterMs = retryAfterMs;
    }

    public static Response ok(String body) {
        return new Response(200, body, -1);
    }

    public static Response rateLimited(long retryAfterMs) {
        return new Response(429, null, retryAfterMs);
    }

    public boolean isOk()          { return status == 200; }
    public boolean isRateLimited() { return status == 429; }
    public String  getBody()       { return body; }
    public long    getRetryAfterMs() { return retryAfterMs; }

    @Override
    public String toString() {
        return "Response{status=" + status + ", retryAfterMs=" + retryAfterMs + "}";
    }
}
