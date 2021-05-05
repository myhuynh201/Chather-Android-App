package edu.uw.tcss450.ui.connections;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import edu.uw.tcss450.R;
import edu.uw.tcss450.ui.connections.dummy.ConnectionContent.ConnectionItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link ConnectionItem}.
 * Connection views are recycled
 */
public class MyConnectionRecyclerViewAdapter extends RecyclerView.Adapter<MyConnectionRecyclerViewAdapter.ViewHolder> {

    /**
     * List of connections
     */
    private final List<ConnectionItem> mValues;

    /**
     * Constructs the View Adapter for the connections
     * @param items list of Connections
     */
    public MyConnectionRecyclerViewAdapter(List<ConnectionItem> items) {
        mValues = items;
    }

    /**
     * When view holder is created, inflate
     * @param parent parent of this view
     * @param viewType type of view
     * @return new View holder for this view
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_connection, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Bind view holder
     * @param holder view holder
     * @param position index of this connection
     */
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).username);
        holder.mContentView.setText(mValues.get(position).content);
    }

    /**
     * @return number of connections
     */
    @Override
    public int getItemCount() {
        return mValues.size();
    }

    /**
     * Class for ViewHolder
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        /**
         * fields for view holder
         */
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public ConnectionItem mItem;

        /**
         * Constructor for view holder
         * @param view this view
         */
        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.item_number);
            mContentView = (TextView) view.findViewById(R.id.content);
        }

        /**
         * Overrides toString
         * @return Displays this connection's text
         */
        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}