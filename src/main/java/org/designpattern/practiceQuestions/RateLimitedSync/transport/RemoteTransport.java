package org.designpattern.practiceQuestions.RateLimitedSync.transport;

import org.designpattern.practiceQuestions.RateLimitedSync.model.Response;

public interface RemoteTransport {
    Response fetch(String artifactId);
}
