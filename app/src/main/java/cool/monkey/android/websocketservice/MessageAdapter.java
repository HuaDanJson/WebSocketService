package cool.monkey.android.websocketservice;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jason on 2018/3/14.
 */

public class MessageAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<MessageBean> mDatas;
    private static final int SEND_MESSAGE = 1;
    private static final int RECEIVE_MESSAGE = 2;

    public MessageAdapter(List<MessageBean> messageBeanList, Context mContext) {
        this.context = mContext;
        this.mDatas = messageBeanList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == SEND_MESSAGE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycle_chat_message_send, parent, false);
            return new SendMessageViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycle_chat_message_receive, parent, false);
            return new ReceiveMessageViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof SendMessageViewHolder) {
            ((SendMessageViewHolder) holder).mSendMessage.setText(mDatas.get(position).getMessage());
        } else if (holder instanceof ReceiveMessageViewHolder) {
            ((ReceiveMessageViewHolder) holder).mReceiveMessage.setText(mDatas.get(position).getMessage());
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mDatas.get(position).getUserId() == mDatas.get(position).getSendUserId()) {
            return SEND_MESSAGE;
        } else {
            return RECEIVE_MESSAGE;
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public static class SendMessageViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_recycle_chat_send_content)
        TextView mSendMessage;

        public SendMessageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public static class ReceiveMessageViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_recycle_chat_receive_content)
        TextView mReceiveMessage;

        public ReceiveMessageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
