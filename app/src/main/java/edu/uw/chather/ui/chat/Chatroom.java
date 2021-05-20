package edu.uw.chather.ui.chat;

import java.util.List;

public class Chatroom {
    private int mChatId;
    private List<Integer> mChatMembers;

    public Chatroom(int chatid) {
        mChatId = chatid;
    }

    public int getChatId() {
        return mChatId;
    }


}
