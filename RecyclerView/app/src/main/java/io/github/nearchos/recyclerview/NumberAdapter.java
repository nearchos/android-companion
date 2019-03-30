package io.github.nearchos.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Locale;
import java.util.Vector;

/**
 * @author Nearchos
 * Created: 28-Jun-18
 */
public class NumberAdapter extends RecyclerView.Adapter<NumberAdapter.ViewHolder> {

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and you provide access to all the
    // views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView textView;
        public ViewHolder(TextView textView) {
            super(textView);
            this.textView = textView;
        }
    }

    private Vector<Double> data;
    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;

    // Provide a suitable constructor (depends on the kind of dataset)
    public NumberAdapter(final Vector<Double> data, OnItemClickListener onItemClickListener, OnItemLongClickListener onItemLongClickListener) {
        this.data = data;
        this.onItemClickListener = onItemClickListener;
        this.onItemLongClickListener = onItemLongClickListener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public NumberAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        TextView textView = (TextView) LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.number_view, parent, false);

        final ViewHolder viewHolder = new ViewHolder(textView);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.textView.setText(String.format(Locale.US, "%.1f", data.get(position)));
        holder.itemView.setOnClickListener(v -> onItemClickListener.itemClicked(position, data.get(position)));
        holder.itemView.setOnLongClickListener(v -> onItemLongClickListener.itemLongClicked(position, data.get(position)));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return data.size();
    }

    interface OnItemClickListener {
        void itemClicked(int pos, double value);
    }

    interface OnItemLongClickListener {
        boolean itemLongClicked(int pos, double value);
    }
}