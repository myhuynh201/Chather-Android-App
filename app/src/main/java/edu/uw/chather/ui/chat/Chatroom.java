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

    public List<String> getChatMembers() {
        return mChatMembers;
    }

    public void setmChatMembers(List<String> mChatMembers) {
        this.mChatMembers = mChatMembers;
    }

    public void addChatMember(String member) {
        mChatMembers.add(member);
    }

    public boolean containsMember(String member) {
        return mChatMembers.contains(member);
    }
    /**
     * Stores ID of chat members
     */
    private List<String> mChatMembers;

    /**
     * Generates a chatroom
     * @param chatid id for the chatroom
     */
    public Chatroom(int chatid, List<String> chatMembers) {
        mChatId = chatid;
        mChatMembers = chatMembers;
    }

    /**
     * Gets chat id
     * @return the current chat id
     */
    public int getChatId() {
        return mChatId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String username : mChatMembers) {
            if (sb.length() != 0) {
                sb.append(", ");
            }
            sb.append(username);
        }
        return sb.toString();
    }
}
