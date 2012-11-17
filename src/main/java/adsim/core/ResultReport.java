package adsim.core;

import lombok.experimental.Value;

@Value
public class ResultReport {
    private final long messagesCreatedCount;
    private final long messagesAcceptedCount;
    private final long packetsSentCount;
    private final long packetsDisposedCount;
}
