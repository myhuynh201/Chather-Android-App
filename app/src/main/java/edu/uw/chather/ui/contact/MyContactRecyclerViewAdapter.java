package edu.uw.chather.ui.contact;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import edu.uw.chather.R;
import edu.uw.chather.ui.contact.dummy.ContactContent.ConnectionItem;

import java.util.ArrayList;

/**
 * {@link RecyclerView.Adapter} that can display a {@link ConnectionItem}.
 * Connection views are recycled
 */
public class MyContactRecyclerViewAdapter extends RecyclerView.Adapter<MyContactRecyclerViewAdapter.ViewHolder> {

    /**
     * List of connections
     */
    private final ArrayList<Contact> mValues;

    private ContactListViewModel mModel;



    /**
     * Constructs the View Adapter for the connections
     * @param items list of Connections
     */
    public MyContactRecyclerViewAdapter(ContactListViewModel model) {
        mValues = (ArrayList<Contact>) model.getContactList();
        mModel = model;
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
                .inflate(R.layout.fragment_contact_card, parent, false);
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
        holder.mContentView.setText(holder.mItem.getmUsername());

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
        public Contact mItem;

        /**
         * Constructor for view holder
         * @param view this view
         */
        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.item_number);
            mContentView = (TextView) view.findViewById(R.id.contact_username_text);
            mView.findViewById(R.id.contact_delete_request_button).setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mModel.contactDelete(mItem.getmMemberId());
                        }
                    }
            );
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