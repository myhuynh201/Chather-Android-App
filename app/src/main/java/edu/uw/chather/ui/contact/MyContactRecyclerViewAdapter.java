package edu.uw.chather.ui.contact;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import edu.uw.chather.MainActivity;
import edu.uw.chather.R;
import edu.uw.chather.ui.chat.ChatNewRoomFragmentDirections;
import edu.uw.chather.ui.chat.ChatNewRoomViewModel;
import edu.uw.chather.ui.contact.dummy.ContactContent.ConnectionItem;
import edu.uw.chather.ui.model.UserInfoViewModel;

import java.security.Provider;
import java.util.ArrayList;
import java.util.List;

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

    private ChatNewRoomViewModel mNewChatModel;

    private Activity mActivity;



    /**
     * Constructs the View Adapter for the connections
     * @param items list of Connections
     */
    public MyContactRecyclerViewAdapter(ContactListViewModel model, Activity activity) {
        mValues = (ArrayList<Contact>) model.getContactList();
        mModel = model;
        mActivity = activity;
        mNewChatModel = new ViewModelProvider((MainActivity) activity).get(ChatNewRoomViewModel.class);
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
            UserInfoViewModel userModel = new ViewModelProvider((MainActivity) mActivity).get(UserInfoViewModel.class);
            mView.findViewById(R.id.contact_start_chat_button).setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            List<String> memberList = new ArrayList<>();
                            memberList.add(userModel.getUsername());
                            memberList.add(mItem.getmUsername());
                            mNewChatModel.createNewChat(UserInfoViewModel.getmJwt(), memberList);

                        }
                    }

            );
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