package org.designpattern.practiceQuestions.ArtifactSyncClient.config;

import java.time.Duration;

public final class ThrottlingSettings {

    private final int rate;
    private final Duration unit;

    private ThrottlingSettings(int rate, Duration unit) {
        this.rate = rate;
        this.unit = unit;
    }

    public static ThrottlingSettings create(int ratePerMinute) {
        if (ratePerMinute <= 0) throw new IllegalArgumentException("Rate must be positive");
        return new ThrottlingSettings(ratePerMinute, Duration.ofMinutes(1));
    }


    // Converts to permits/second for the token bucket
    public double getPermitsPerSecond() {
        return rate / (double) unit.getSeconds();
    }

    public int getRate() { return rate; }

    @Override
    public String toString() {
        return rate + " req/min (" + String.format("%.2f", getPermitsPerSecond()) + " req/sec)";
    }
}