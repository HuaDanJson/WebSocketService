package cool.monkey.android.websocketservice;

import android.util.Log;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;

public class WebsocketServer extends WebSocketServer {

    public String TAG = "WebsocketServer";
    public WebSocket mClientSession = null;
    private WebsocketServerListener mListener;

    public WebsocketServer(int port) {
        super(new InetSocketAddress(port));
    }

    public interface WebsocketServerListener {

        void onReceiveTextChatMessage(String message);

    }

    public void setWebsocketServerListener(WebsocketServerListener listener) {
        this.mListener = listener;
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake arg1) {
        // TODO Auto-generated method stub
        Log.d(TAG, "Server onOpen getHostAddress:" + conn.getRemoteSocketAddress().getAddress().getHostAddress());
        mClientSession = conn;
    }

    @Override
    public void onMessage(WebSocket conn, String arg1) {
        // TODO Auto-generated method stub
        Log.d(TAG, "Server onMessage getHostAddress:" + conn.getRemoteSocketAddress().getAddress().getHostAddress());
        Log.d(TAG, "Server onMessage msg:" + arg1);
        if (mListener != null) {
            mListener.onReceiveTextChatMessage(arg1);
        }

    }

    @Override
    public void onClose(WebSocket conn, int arg1, String arg2, boolean arg3) {
        // TODO Auto-generated method stub
        Log.d(TAG, "Server onClose getHostAddress:" + conn.getRemoteSocketAddress().getAddress().getHostAddress());

        mClientSession = null;
    }

    @Override
    public void onError(WebSocket conn, Exception arg1) {
        // TODO Auto-generated method stub
        Log.d(TAG, "Server client onError:" + arg1);

        mClientSession = null;
    }

    @Override
    public void onStart() {
        // TODO Auto-generated method stub
        Log.d(TAG, "Server client onStart");
    }
}
