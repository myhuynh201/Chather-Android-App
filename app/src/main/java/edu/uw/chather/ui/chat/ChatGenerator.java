package edu.uw.chather.ui.chat;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import edu.uw.chather.ui.contact.dummy.ContactContent;

/**
 * This class is used to create Dummy Chats
 *
 * @author alecmac
 */
public final class ChatGenerator {

    /**
     * Array of chatrooms
     */
    private static final ChatRoom[] CHAT_ROOMS;
    /**
     * Hard coded number of chatrooms
     */
    public static final int COUNT = 6;


    static {
        Random random = new Random();
        CHAT_ROOMS = new ChatRoom[COUNT];
        for (int i = 0; i < CHAT_ROOMS.length; i++) {
            CHAT_ROOMS[i] = new ChatRoom
                    .Builder("2020-04-" + (12-i) + " 12:59 pm",
                    ContactContent.getName(random.nextInt(17) +1 ))
                    .addTeaser(makeChat(i))
                    .build();
        }
    }

    /**
     * Get dummy chatroom text for preview and viewing
     * @param position index of the current chatroom being initialized
     * @return chatroom text
     */
    private static String makeChat(int position) {
        String[] dummyMessages = {
                "What the heck did you just frickin’ say about me, you little whiner? I’ll have you know I graduated top of my class in the Navy Seals, and I’ve been involved in numerous secret raids on Al-Quaeda, and I have over 300 confirmed kills.",
                "According to all known laws of aviation, there is no way a bee should be able to fly. Its wings are too small to get its fat little body off the ground. The bee, of course, flies anyway because bees don't care what humans think is impossible.",
                "Excuse me ma’am, not to be disrespectful or rude but could you please take post down. That is my sister who was killed by a metra train. And it this post is very disrespectful. Idk who you are or if you even know her but I need you to take this down please.",
                "Baby, this is Keke Palmer\r\nHave a good day",
                "We gon be us\uD83D\uDCAF\uD83D\uDCAF\uD83D\uDC95\uD83D\uDE1D, we gon get crazy \uD83C\uDF1A\uD83D\uDE0E☺\uD83D\uDE48\uD83D\uDE0D✋✋✋✋✋hol' up hol' up ✋✋ looks \uD83D\uDC40 like we got a master \uD83C\uDF93 memer \uD83D\uDC38\uD83D\uDC38\uD83D\uDC38 over here \uD83D\uDC48\uD83D\uDC48",
                "Hey xQc my son is a huge fan of yours. Anyway over the past few days of watching you play this game hes got much better at playing baseball (he plays in the 11-13 year old division). I just wanted to stop by and thank you for teaching him how to throw so well."
        };
        return dummyMessages[position];
    }


    /**
     * Get List of dummy chatrooms
     * @return List of chatrooms
     */
    public static List<ChatRoom> getChatList() {
        return Arrays.asList(CHAT_ROOMS);
    }

    private ChatGenerator() { }


}

