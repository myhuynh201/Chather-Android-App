package edu.uw.chather.ui.chat;

import java.io.Serializable;

/**
 * Class to encapsulate a Phish.net Blog Post. Building an Object requires a publish date and title.
 *
 * Optional fields include URL, teaser, and Author.
 *
 *
 * @author Charles Bryan
 * @version 14 September 2018
 */
public class ChatRoom implements Serializable {

    private final String mPubDate;
    private final String mTitle;
    private final String mUrl;
    private final String mTeaser;
    private final String mAuthor;

    /**
     * Helper class for building Credentials.
     *
     * @author Charles Bryan
     */
    public static class Builder {
        private final String mPubDate;
        private final String mTitle;
        private  String mUrl = "";
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
         * Add an optional url for the full chat post.
         * @param val an optional url for the full chat post
         * @return the Builder of this ChatRoom
         */
        public Builder addUrl(final String val) {
            mUrl = val;
            return this;
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

        public ChatRoom build() {
            return new ChatRoom(this);
        }

    }

    private ChatRoom(final Builder builder) {
        this.mPubDate = builder.mPubDate;
        this.mTitle = builder.mTitle;
        this.mUrl = builder.mUrl;
        this.mTeaser = builder.mTeaser;
        this.mAuthor = builder.mAuthor;
    }

    public String getPubDate() {
        return mPubDate;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getUrl() {
        return mUrl;
    }

    public String getTeaser() {
        return mTeaser;
    }

    public String getAuthor() {
        return mAuthor;
    }


}
