package edu.uw.chather.ui.chat;

import java.util.List;

/**
 * Chatroom stores information about a chatroom
 * @author Alec Mac
 */
public class Chatroom {
    /**
     * ID of the chat
     */
    private int mChatId;
    /**
     * Stores ID of chat members
     */
    private List<Integer> mChatMembers;

    /**
     * Generates a chatroom
     * @param chatid id for the chatroom
     */
    public Chatroom(int chatid) {
        mChatId = chatid;
    }

    /**
     * Gets chat id
     * @return the current chat id
     */
    public int getChatId() {
        return mChatId;
    }


}
