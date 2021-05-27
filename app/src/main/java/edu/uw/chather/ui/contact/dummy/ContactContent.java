package edu.uw.chather.ui.contact.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for connections
 */
public class ContactContent {

    /**
     * An array of sample connections.
     */
    public static final List<ConnectionItem> ITEMS = new ArrayList<ConnectionItem>();

    /**
     * A map of sample connections, by ID.
     */
    public static final Map<String, ConnectionItem> ITEM_MAP = new HashMap<String, ConnectionItem>();

    /**
     * Count of connections
     */
    private static final int COUNT = 17;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createDummyItem(i));
        }
    }

    /**
     * puts connections into a map
     * @param item connection
     */
    private static void addItem(ConnectionItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.username, item);
    }

    /**
     * creates the dummy connection
     * @param position the current index for the dummy
     * @return the dummy connection
     */
    private static ConnectionItem createDummyItem(int position) {
        return new ConnectionItem(String.valueOf(position), getName(position), makeDetails(position));
    }


    /**
     * Hard coded names for connections
     * @param position index of the dummy connection
     * @return the name of the connection for the given position
     */
    public static String getName(int position) {
        String[] names = {"Odin", "Alcis", "Baldr", "Freya", "Freyr", "Heimdall", "Odr", "Tyr", "Ullr", "Mimir", "Njordr", "Frigg",
                "Saxnot", "Vili", "Tuisto", "Sleipnir", "Idunn"};
        return names[position - 1];
    }

    /**
     * Create details
     * @param position customize details based on position
     * @return string details
     */
    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    /**
     * A dummy item representing a Connection.
     */
    public static class ConnectionItem {
        public final String username;
        public final String content;
        public final String details;

        public ConnectionItem(String username, String content, String details) {
            this.username = username;
            this.content = content;
            this.details = details;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}