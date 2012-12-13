package adsim.handler;

import java.util.HashMap;
import java.util.HashSet;

import lombok.*;
import adsim.core.Message;
import adsim.core.Message.Envelope;
import adsim.core.Message.TellNeighbors;
import adsim.core.Node;
import adsim.core.Session;

public class ReachedEnvelopeDisposer extends NodeHandlerBase {

    @Override
    public void initialize(Node node) {

    }

    @Override
    public void interval(Session sess, Node node) {

    }

    @Override
    public void onReceived(Node self, Message packet) {
        HashMap<Long, Envelope> map = new HashMap<Long, Envelope>(self
                .getBuffer().size());
        for (val envelope : self.getBuffer()) {
            map.put(envelope.getId(), envelope);
        }
        if (packet.getType() == Message.TYPE_TELLNEIGHBORS) {
            TellNeighbors msg = (TellNeighbors) packet;
            for (val entry : msg.getEntries()) {
                for (val msgId : entry.getReceivedEnvelopes()) {
                    if (map.containsKey(msgId)) {
                        val disposeMsg = map.get(msgId);
                        self.disposeMessage(disposeMsg);
                    }
                }
            }
        }
    }

}
