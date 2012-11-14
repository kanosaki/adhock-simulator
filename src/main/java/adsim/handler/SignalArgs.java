package adsim.handler;

import lombok.Data;

@Data
public class SignalArgs {
    public final static SignalArgs Void = new SignalArgs();
    private boolean handled;

    public void completeHandling() {
        setHandled(true);
    }
}
