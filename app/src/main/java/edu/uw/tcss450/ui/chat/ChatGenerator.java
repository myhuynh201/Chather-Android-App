package edu.uw.tcss450.ui.chat;

import java.util.Arrays;
import java.util.List;

/**
 * This class is used to create Dummy Blogs. Use it for development.
 * In future labs, connect to an API to gain actual Blog Data.
 */
public final class ChatGenerator {

    private static final ChatRoom[] CHAT_ROOMS;
    public static final int COUNT = 5;


    static {
        CHAT_ROOMS = new ChatRoom[COUNT];
        for (int i = 0; i < CHAT_ROOMS.length; i++) {
            CHAT_ROOMS[i] = new ChatRoom
                    .Builder("2020-04-" + (12-i) + " 12:59 pm",
                    "John Doe #" + (i + 43))
                    .addTeaser(makeChat(i))
                    .addUrl("http://phish.net/blog/1472930164/dicks1-when-mercury-comes-out-at-night")
                    .build();
        }
    }

    private static String makeChat(int position) {
        String[] dummyMessages = {
                "What the heck did you just frickin’ say about me, you little whiner? I’ll have you know I graduated top of my class in the Navy Seals, and I’ve been involved in numerous secret raids on Al-Quaeda, and I have over 300 confirmed kills.",
                "According to all known laws of aviation, there is no way a bee should be able to fly. Its wings are too small to get its fat little body off the ground. The bee, of course, flies anyway because bees don't care what humans think is impossible.",
                "Excuse me ma’am, not to be disrespectful or rude but could you please take post down. That is my sister who was killed by a metra train. And it this post is very disrespectful. Idk who you are or if you even know her but I need you to take this down please.",
                "Baby, this is Keke Palmer\r\nHave a good day",
                "We gon be us\uD83D\uDCAF\uD83D\uDCAF\uD83D\uDC95\uD83D\uDE1D, we gon get crazy \uD83C\uDF1A\uD83D\uDE0E☺\uD83D\uDE48\uD83D\uDE0D✋✋✋✋✋hol' up hol' up ✋✋ looks \uD83D\uDC40 like we got a master \uD83C\uDF93 memer \uD83D\uDC38\uD83D\uDC38\uD83D\uDC38 over here \uD83D\uDC48\uD83D\uDC48"
        };
        return dummyMessages[position];
    }

    public static List<ChatRoom> getChatList() {
        return Arrays.asList(CHAT_ROOMS);
    }

    public static ChatRoom[] getChatRooms() {
        return Arrays.copyOf(CHAT_ROOMS, CHAT_ROOMS.length);
    }

    private ChatGenerator() { }


}

