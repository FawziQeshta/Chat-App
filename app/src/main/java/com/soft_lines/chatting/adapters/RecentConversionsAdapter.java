package com.soft_lines.chatting.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.soft_lines.chatting.R;
import com.soft_lines.chatting.databinding.ItemContainerRecentConverionBinding;
import com.soft_lines.chatting.listeners.ConversionListener;
import com.soft_lines.chatting.models.ChatMessage;
import com.soft_lines.chatting.models.User;

import java.util.List;

public class RecentConversionsAdapter extends RecyclerView.Adapter<RecentConversionsAdapter.ConversionViewHolder> {

    private final List<ChatMessage> data;
    private final ConversionListener listener;

    public RecentConversionsAdapter(List<ChatMessage> data, ConversionListener listener) {
        this.data = data;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ConversionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemContainerRecentConverionBinding binding = ItemContainerRecentConverionBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ConversionViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecentConversionsAdapter.ConversionViewHolder holder, int position) {
        holder.setUserData(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ConversionViewHolder extends RecyclerView.ViewHolder {

        ItemContainerRecentConverionBinding binding;

        public ConversionViewHolder(ItemContainerRecentConverionBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void setUserData(ChatMessage chatMessage) {
            binding.imageProfile.setImageBitmap(getConversionImage(chatMessage.conversionImage));
            binding.textName.setText(chatMessage.conversionName);
            if (chatMessage.messageText.isEmpty()) {
                binding.textRecentMessage.setText(R.string.image_message);
            } else {
                binding.textRecentMessage.setText(chatMessage.messageText);
            }
            binding.getRoot().setOnClickListener(v -> {
                User user = new User();
                user.id = chatMessage.conversionId;
                user.name = chatMessage.conversionName;
                user.image = chatMessage.conversionImage;
                listener.onConversionClicked(user);
            });
        }
    }

    private Bitmap getConversionImage(String encodedImage) {
        byte[] bytes = Base64.decode(encodedImage, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

}
