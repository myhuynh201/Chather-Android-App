package edu.uw.tcss450.ui.connections.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class ConnectionContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<ConnectionItem> ITEMS = new ArrayList<ConnectionItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, ConnectionItem> ITEM_MAP = new HashMap<String, ConnectionItem>();

    private static final int COUNT = 17;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createDummyItem(i));
        }
    }

    private static void addItem(ConnectionItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.username, item);
    }

    private static ConnectionItem createDummyItem(int position) {
        return new ConnectionItem(String.valueOf(position), getName(position), makeDetails(position));
    }


    private static String getName(int position) {
        String[] names = {"Odin", "Alcis", "Baldr", "Freya", "Freyr", "Heimdall", "Odr", "Tyr", "Ullr", "Mimir", "Njordr", "Frigg",
                "Saxnot", "Vili", "Tuisto", "Sleipnir", "Idunn"};
        return names[position - 1];
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    /**
     * A dummy item representing a piece of content.
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