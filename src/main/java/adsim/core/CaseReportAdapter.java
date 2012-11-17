package adsim.core;

public class CaseReportAdapter {
    private ICase cas;

    public CaseReportAdapter(ICase cas) {
        this.cas = cas;
    }

    /**
     * ノードがメッセージを作成したときに呼び出します
     */
    void onMessageCreated(Session sess, Node creator, Message msg) {

    }

    /**
     * ノードが"自分宛の"メッセージを受信したときに呼び出します
     */
    void onMessageReceived(Session sess, Node receiver, Message msg) {

    }

    /**
     * "デバイスが"送信制限によりメッセージを送信しきれなかったときに呼び出します
     */
    void onMessageDisposed(Session sess, Device dev, Message msg) {

    }

    /**
     * ICaseが終了した際のデータを渡します。
     */
    void report(long wholeSent, long wholeReceived) {

    }
}
