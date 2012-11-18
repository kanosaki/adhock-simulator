package adsim.core;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import adsim.Util;
import adsim.misc.Vector;

@Slf4j
public class Session {
    private int DEFAULT_STEP_LIMIT = 100;
    private int DEFAULT_MAX_FRIENDSHIPS = 10;
    private int DEFAULT_MIN_FRIENDSHIPS = 1;
    private ICase cas;

    @Getter
    private long step;

    @Getter
    private long stepLimit;

    @Getter
    private long createdMessagesCount;

    @Getter
    private long reachedMessagesCount;

    public List<Node> getNodes() {
        return cas.getNodes();
    }

    private final @Getter
    Field field;

    public Session(ICase cas) {
        this.cas = cas;
        this.field = new Field(cas.getFieldSize());
        this.step = 0;
        this.stepLimit = cas.getStepLimit();
        stepCheck();
        init();
        log.debug("Session for " + cas.toString() + " initialized");
    }

    private void init() {
        for (val node : getNodes()) {
            node.setSession(this);
        }
        initField();
        log.debug("Creating friendships...");
        createFriendships();
        log.debug("Creating roundpoints...");
        registerRoundPoints();
        for (val node : getNodes()) {
            node.onSessionInitialized();
        }
    }

    private void initField() {
        val nodes = cas.getNodes();
        field.expandCapacity(nodes.size());
        for (val node : nodes) {
            field.addDevice(node.getDevice());
        }
    }

    private void stepCheck() {
        if (stepLimit == 0) {
            log.warn("This case has no StepLimit, applying default limit");
            stepLimit = DEFAULT_STEP_LIMIT;
        }
    }

    public void next() {
        log.debug("Step " + this.step);
        step += 1;
        for (val node : cas.getNodes()) {
            node.next(this);
        }
        this.field.next();
    }

    public void start() {
        try {
            while (step < stepLimit) {
                next();
            }
            onCompleted();
            log.info("Session finished.");
        } catch (SessionFinishedException e) {
            log.info("Session aborted.");
        }
    }

    protected void onCompleted() {
        cas.tellResult(new ResultReport(createdMessagesCount,
                reachedMessagesCount, field.getWholeSentCount(), field
                        .getWholeDisposedCount()));
    }

    /**
     * メッセージを送信する際に全くランダムに送るのでは無く、「知っている人物に定期的に送る」という挙動を実現するために事前に"Friends"
     * として各ノードにメッセージの送信先ノードを登録しておきます
     * 。事前に登録されるFriendの数は、DEFAULT_MAX_FRIENDSHIPS以下
     * 、DEFAULT_MIN_FRIENDSHIPS以上で、疑似一様分布に従って選択されます。
     */
    private void createFriendships() {
        val nodes = cas.getNodes();
        if (nodes.size() < 2)
            return;
        val max = (int) Math
                .min(DEFAULT_MAX_FRIENDSHIPS, nodes.size() - 1);
        val min = (int) Math.min(max, DEFAULT_MIN_FRIENDSHIPS);
        for (val me : nodes) {
            // generate random number between max and min
            val friendCount = Util.randInt(min, max);
            while (me.getFriends().size() < friendCount) {
                val newfriend = Util.randomSelect(nodes);
                me.addFriend(newfriend);
                newfriend.addFriend(me);
            }
        }
    }

    private Vector createRandomPoint() {
        return new Vector(Math.random() * cas.getFieldSize(), Math.random()
                * cas.getFieldSize());
    }

    private void registerRoundPoints() {
        int groupHop = 3;
        long pointCount = getNodes().size() * 3;
        for (int i = 0; i < pointCount; i++) {
            val centerNodes = Util.randomSelect(getNodes());
            recAppendRoundPoint(centerNodes, createRandomPoint(), groupHop,
                    new TreeSet<Node>());
        }
    }

    private void recAppendRoundPoint(Node tgt, Vector point, int recRemain,
            Set<Node> visited) {
        if (recRemain == 0 || visited.contains(tgt))
            return;
        else {
            visited.add(tgt);
            for (val friend : tgt.getFriends()) {
                friend.addRoundPoint(point);
                recAppendRoundPoint(friend, point, recRemain - 1, visited);
            }
        }
    }

    public void onMessageCreated(Node node, Message msg) {
        createdMessagesCount += 1;
    }

    public void onMessageReached(Node node, Message msg) {
        reachedMessagesCount += 1;
    }
}
