package edu.uw.chather.ui.chat;

import java.io.Serializable;

/**
 * Class to encapsulate a ChatRoom
 *
 * @author alecmac (based on Charles Bryan code)
 */
public class ChatRoom implements Serializable {

    //TODO: Refactor these for chatroom

    private final String mPubDate;
    private final String mTitle;
    private final String mTeaser;
    private final String mAuthor;

    /**
     * Helper class for building Credentials.
     *
     * @author alecmac (based on Charles Bryan code)
     */
    public static class Builder {
        private final String mPubDate;
        private final String mTitle;
        private  String mTeaser = "";
        private  String mAuthor = "";


        /**
         * Constructs a new Builder.
         *
         * @param pubDate the published date of the chat post
         * @param title the title of the chat post
         */
        public Builder(String pubDate, String title) {
            this.mPubDate = pubDate;
            this.mTitle = title;
        }

        /**
         * Add an optional teaser for the full chat post.
         * @param val an optional url teaser for the full chat post.
         * @return the Builder of this ChatRoom
         */
        public Builder addTeaser(final String val) {
            mTeaser = val;
            return this;
        }

        /**
         * Add an optional author of the chat post.
         * @param val an optional author of the chat post.
         * @return the Builder of this ChatRoom
         */
        public Builder addAuthor(final String val) {
            mAuthor = val;
            return this;
        }

        /**
         * Builds chatroom
         * @return a constructed ChatRoom
         */
        public ChatRoom build() {
            return new ChatRoom(this);
        }

    }

    /**
     * Constructs Chatroom from builder
     * @param builder used to construct a Chatroom
     */
    private ChatRoom(final Builder builder) {
        this.mPubDate = builder.mPubDate;
        this.mTitle = builder.mTitle;
        this.mTeaser = builder.mTeaser;
        this.mAuthor = builder.mAuthor;
    }

    /**
     * @return publication / sent date
     */
    public String getPubDate() {
        return mPubDate;
    }

    /**
     * @return Title or header
     */
    public String getTitle() {
        return mTitle;
    }


    /**
     * @return chat message
     */
    public String getTeaser() {
        return mTeaser;
    }

    /**
     * @return author or sender
     */
    public String getAuthor() {
        return mAuthor;
    }


}
