package adsim.core;

import adsim.misc.Vector;
import lombok.*;

public class RoundPoint {
    @Getter
    private final Vector point;
    @Getter
    private final int meetingInterval;
    @Getter
    private int remainToNextMeeting;
    
    public RoundPoint(Vector point, int meetingInterval) {
        this.point = point;
        this.meetingInterval = meetingInterval;
        this.remainToNextMeeting = meetingInterval;
    }

    public void next() {
        if (remainToNextMeeting < 0)
            remainToNextMeeting = meetingInterval;
        else
            remainToNextMeeting -= 1;
    }

    
}
