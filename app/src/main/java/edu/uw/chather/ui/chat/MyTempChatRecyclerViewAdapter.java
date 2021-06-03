package edu.uw.chather.ui.chat;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import edu.uw.chather.R;
import edu.uw.chather.ui.chat.dummy.DummyContent.DummyItem;

import java.util.List;

/**
 * View adapter for hard coded chat view
 * @author Alec Mac
 */
public class MyTempChatRecyclerViewAdapter extends RecyclerView.Adapter<MyTempChatRecyclerViewAdapter.ViewHolder> {

    private final List<DummyItem> mValues;

    public MyTempChatRecyclerViewAdapter(List<DummyItem> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_tempchat, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).id);
        holder.mContentView.setText(mValues.get(position).content);
        holder.mView.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(TempChatFragmentDirections.actionTempChatFragmentToChatFragment(position + 1, holder.mItem.content));
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public DummyItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.item_number);
            mContentView = (TextView) view.findViewById(R.id.contact_username_text);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}