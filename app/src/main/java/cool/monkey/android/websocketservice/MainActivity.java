package cool.monkey.android.websocketservice;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.LogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements WebsocketServer.WebsocketServerListener, View.OnLayoutChangeListener {

    @BindView(R.id.rv_message_main_activity) RecyclerView mRecyclerView;
    @BindView(R.id.edt_input_message_main_activity) EditText mEditText;
    @BindView(R.id.ll_input_main_activity) LinearLayout mInputLinearLayout;
    @BindView(R.id.ll_message_main_activity) LinearLayout mMainActivity;

    private List<MessageBean> messageBeanList = new ArrayList<>();
    private MessageAdapter messageAdapter;

    private WebsocketServer mServer = null;
    private int screenHeight = 0;
    private int keyHeight = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        bindServer();
        initRecyclerView();
        initEditTextSendClick();
    }

    public void initEditTextSendClick() {
        mEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (!TextUtils.isEmpty(mEditText.getText().toString())) {
                    if (null != mServer.mClientSession) {
                        mServer.mClientSession.send(mEditText.getText().toString());
                        MessageBean messageBean = new MessageBean();
                        messageBean.setCreateAt(System.currentTimeMillis());
                        messageBean.setSendUserId(1);
                        messageBean.setUserId(1);
                        messageBean.setMessage(mEditText.getText().toString());
                        messageBeanList.add(messageBean);
                        initRecyclerView();
                        LogUtils.d("MainActivity Send message success()");
                        mEditText.setText("");
                    } else {
                        LogUtils.d("MainActivity Send message Failed not connect ");
                        Toast.makeText(MainActivity.this, "not connect", Toast.LENGTH_LONG).show();
                        bindServer();
                    }
                }
                return true;
            }
        });
        screenHeight = getWindowManager().getDefaultDisplay().getHeight();
        keyHeight = screenHeight / 3;
    }

    public void initRecyclerView() {
        if (mRecyclerView != null) {
            if (messageAdapter == null && mRecyclerView != null) {
                mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                messageAdapter = new MessageAdapter(messageBeanList, this);
                mRecyclerView.setAdapter(messageAdapter);
            } else if (messageAdapter != null && mRecyclerView != null) {
                mRecyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        if (messageAdapter != null) {
                            messageAdapter.notifyDataSetChanged();
                            mRecyclerView.smoothScrollToPosition((messageAdapter.getItemCount() - 1));
                        }
                    }
                });
            }
        }
    }

    private void bindServer() {
        LogUtils.d("MainActivity bindServer()");
        int port = 9000; //端口
        mServer = new WebsocketServer(port);
        mServer.setReuseAddr(true);
        mServer.setWebsocketServerListener(this);
        mServer.start();
    }

    @Override
    public void onReceiveTextChatMessage(String message) {
        LogUtils.d("MainActivity onReceiveTextChatMessage message = " + message);
        MessageBean messageBean = new MessageBean();
        messageBean.setCreateAt(System.currentTimeMillis());
        messageBean.setSendUserId(2);
        messageBean.setUserId(1);
        messageBean.setMessage(message);
        messageBeanList.add(messageBean);
        initRecyclerView();
    }

    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        if (oldBottom != 0 && bottom != 0 && (oldBottom - bottom > keyHeight)) {
            if (messageAdapter != null && messageAdapter.getItemCount() > 1) {
                mRecyclerView.smoothScrollToPosition((messageAdapter.getItemCount() - 1));
            }
        }
    }
}
