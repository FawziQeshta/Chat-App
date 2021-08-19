package com.soft_lines.chatting.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.soft_lines.chatting.databinding.ItemContainerReceivedMessageBinding;
import com.soft_lines.chatting.databinding.ItemContainerSentMessageBinding;
import com.soft_lines.chatting.models.ChatMessage;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<ChatMessage> data;
    private Bitmap receiverProfileImage;
    private final String senderId;

    public static final int VIEW_TYPE_SENT = 1;
    public static final int VIEW_TYPE_RECEIVED = 2;

    public void setReceiverProfileImage(Bitmap bitmap) {
        receiverProfileImage = bitmap;
    }

    public ChatAdapter(List<ChatMessage> data, Bitmap receiverProfileImage, String senderId) {
        this.data = data;
        this.receiverProfileImage = receiverProfileImage;
        this.senderId = senderId;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_SENT) {
            ItemContainerSentMessageBinding binding = ItemContainerSentMessageBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new SentMessageViewHolder(binding);
        } else {
            ItemContainerReceivedMessageBinding binding = ItemContainerReceivedMessageBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new ReceivedMessageViewHolder(binding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == VIEW_TYPE_SENT) {
            ((SentMessageViewHolder) holder).setData(data.get(position));
        } else {
            ((ReceivedMessageViewHolder) holder).setData(data.get(position), receiverProfileImage);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (data.get(position).senderId.equals(senderId)) {
            return VIEW_TYPE_SENT;
        } else {
            return VIEW_TYPE_RECEIVED;
        }
    }

    static class SentMessageViewHolder extends RecyclerView.ViewHolder {

        private final ItemContainerSentMessageBinding binding;

        public SentMessageViewHolder(ItemContainerSentMessageBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void setData(ChatMessage chatMessage) {
            if (!chatMessage.messageText.isEmpty()) {
                binding.textMessage.setVisibility(View.VISIBLE);
                binding.textMessage.setText(chatMessage.messageText);
            } else {
                binding.textMessage.setVisibility(View.GONE);
            }
            binding.textDateTime.setText(chatMessage.dateTime);
            if (chatMessage.messageImage != null) {
                binding.imageMessage.setVisibility(View.VISIBLE);
                binding.imageMessage.setImageBitmap(getUserBitmapImage(chatMessage.messageImage));
            }
        }
    }

    static class ReceivedMessageViewHolder extends RecyclerView.ViewHolder {

        private final ItemContainerReceivedMessageBinding binding;

        public ReceivedMessageViewHolder(ItemContainerReceivedMessageBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void setData(ChatMessage chatMessage, Bitmap receiverProfileImage) {
            if (!chatMessage.messageText.isEmpty()) {
                binding.textMessage.setVisibility(View.VISIBLE);
                binding.textMessage.setText(chatMessage.messageText);
            } else {
                binding.textMessage.setVisibility(View.GONE);
            }
            binding.textDateTime.setText(chatMessage.dateTime);
            if (chatMessage.messageImage != null) {
                binding.imageMessage.setVisibility(View.VISIBLE);
                binding.imageMessage.setImageBitmap(getUserBitmapImage(chatMessage.messageImage));
            }
            if (receiverProfileImage != null) {
                binding.imageProfile.setImageBitmap(receiverProfileImage);
            }
        }
    }

    private static Bitmap getUserBitmapImage(String encodedImage) {
        byte[] bytes = Base64.decode(encodedImage, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

}
