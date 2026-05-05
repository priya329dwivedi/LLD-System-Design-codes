package org.designpattern.practiceQuestions.RateLimitedSync;

import org.designpattern.practiceQuestions.RateLimitedSync.service.SyncClient;
import org.designpattern.practiceQuestions.RateLimitedSync.transport.MockApi;

import java.util.List;

// Demo: 2 req/sec throttle + MockApi limited to 3 requests per 3-second window.
//
// Expected behaviour:
//   TC-001, TC-002, TC-003 → 200 OK  (3 slots fill the window)
//   TC-004                 → 429     (window still full)
//                            RetryRule waits Retry-After=2000ms, window resets
//                            Retry   → 200 OK
//   TC-005, TC-006         → 200 OK
public class Main {

    public static void main(String[] args) {
        System.out.println("MockApi: 3 req / 3-second window");
        System.out.println("Throttle: 2 req/sec (1 token every 500ms)");
        System.out.println("Expect: 429 on TC-004, 2s Retry-After, then recovery.\n");

        SyncClient client = new SyncClient(new MockApi(), 2.0);
        client.sync(List.of("TC-001", "TC-002", "TC-003", "TC-004", "TC-005", "TC-006"));
    }
}
