package io.github.nearchos.justchat;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Vector;

/**
 * @author Nearchos Paspallis
 * Created: 22-Dec-20
 */
class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    // Provide a reference to the views for each data item.
    // Complex data items may need more than one view per item, and you provide access to all the
    // views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView timeTextView;
        public TextView fromTextView;
        public TextView messageTextView;
        public ViewHolder(View View) {
            super(View);
            this.timeTextView = itemView.findViewById(R.id.timeTextView);
            this.fromTextView = itemView.findViewById(R.id.fromTextView);
            this.messageTextView  = itemView.findViewById(R.id.messageTextView);
        }
    }

    private String alias = "You";
    private final Vector<Message> messages;

    // Provide a suitable constructor (depends on the kind of dataset)
    public ChatAdapter(final Vector<Message> messages) {
        this.messages = messages;
    }

    void setAlias(final String alias) {
        this.alias = alias;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ChatAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        final View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.chat_item, parent, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        final Message message = messages.get(position);
        // - replace the contents of the view with that element
        holder.timeTextView.setText(message.getHumanTime());
        holder.fromTextView.setText(message.getFrom());
        holder.messageTextView.setText(message.getMessage());

        if(alias.equals(message.getFrom())) { // message from 'you'
            holder.itemView.setBackgroundResource(R.drawable.shape_bg_outgoing_bubble);
            holder.timeTextView.setGravity(Gravity.END);
            holder.fromTextView.setGravity(Gravity.END);
            holder.messageTextView.setGravity(Gravity.END);
        } else { // message from 'other'
            holder.itemView.setBackgroundResource(R.drawable.shape_bg_incoming_bubble);
            holder.timeTextView.setGravity(Gravity.START);
            holder.fromTextView.setGravity(Gravity.START);
            holder.messageTextView.setGravity(Gravity.START);
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return messages.size();
    }
}