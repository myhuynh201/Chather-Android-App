package edu.uw.chather.ui.chat;

import android.content.res.Resources;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.core.graphics.ColorUtils;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.shape.CornerFamily;

import java.util.List;

import edu.uw.chather.R;
import edu.uw.chather.databinding.FragmentChatListBinding;
import edu.uw.chather.databinding.FragmentChatroomBinding;
import edu.uw.chather.ui.model.UserInfoViewModel;

import static android.content.ContentValues.TAG;

public class ChatListRecyclerViewAdapter extends RecyclerView.Adapter<ChatListRecyclerViewAdapter.ChatroomViewHolder> {
    private final List<Chatroom> mChatrooms;

    public ChatListRecyclerViewAdapter(List<Chatroom> chatrooms) {
        this.mChatrooms = chatrooms;
    }

    @NonNull
    @Override
    public ChatroomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ChatroomViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.fragment_chatroom, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ChatroomViewHolder holder, int position) {
        holder.setPreview(mChatrooms.get(position));
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ChatroomViewHolder extends RecyclerView.ViewHolder {

        //    public class ChatroomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final View mView;
        private FragmentChatroomBinding binding;

        public ChatroomViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            binding = FragmentChatroomBinding.bind(itemView);
        }

//        @Override
//        public void onClick(View v) {
//            ChatListFragmentDirections.ActionChatListFragmentToNavigationChats directions =
//                    ChatListFragmentDirections.actionChatListFragmentToNavigationChats(
//                            mChatrooms.get(getBindingAdapterPosition()).getChatId());
//            Navigation.findNavController(v).navigate(directions);
//        }

        void setPreview(final Chatroom chatroom) {
            final Resources res = mView.getContext().getResources();
            final MaterialCardView card = binding.cardRoot;

            int standard = (int) res.getDimension(R.dimen.chat_margin);
            int extended = (int) res.getDimension(R.dimen.chat_margin_sided);


            binding.textMessage.setText(chatroom.getChatId() +
                    ": " + UserInfoViewModel.getmJwt());
            ViewGroup.MarginLayoutParams layoutParams =
                    (ViewGroup.MarginLayoutParams) card.getLayoutParams();

            //Set the right margin
            layoutParams.setMargins(standard, standard, extended, standard);
            // Set this View to the left (start) side
            ((FrameLayout.LayoutParams) card.getLayoutParams()).gravity =
                    Gravity.START;

            card.setCardBackgroundColor(
                    ColorUtils.setAlphaComponent(
                            res.getColor(R.color.colorPrimary, null),
                            16));

            card.setStrokeWidth(standard / 5);
            card.setStrokeColor(ColorUtils.setAlphaComponent(
                    res.getColor(R.color.colorPrimaryDark, null),
                    200));

            binding.textMessage.setTextColor(
                    res.getColor(R.color.colorOffWhite, null));

            //Round the corners on the right side
            card.setShapeAppearanceModel(
                    card.getShapeAppearanceModel()
                            .toBuilder()
                            .setTopRightCornerSize(0)
                            .setBottomRightCornerSize(0)
                            .setBottomLeftCornerSize(0)
                            .setTopLeftCornerSize(0)
                            .build());
            card.requestLayout();
            card.setOnClickListener((button) -> {
                Log.d("YOOO CHATLIST", "setPreview: " + chatroom.getChatId());
//                Navigation.findNavController()
            });
        }
    }
}
