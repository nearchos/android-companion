package io.github.nearchos.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
        public ViewHolder(View View) {
            super(View);
            this.textView = itemView.findViewById(R.id.textView);
        }
    }

    private final Vector<Double> data;
    private final OnItemClickListener onItemClickListener;
    private final OnItemLongClickListener onItemLongClickListener;

    // Provide a suitable constructor (depends on the kind of dataset)
    public NumberAdapter(final Vector<Double> data, OnItemClickListener onItemClickListener, OnItemLongClickListener onItemLongClickListener) {
        this.data = data;
        this.onItemClickListener = onItemClickListener;
        this.onItemLongClickListener = onItemLongClickListener;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public NumberAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        final View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_view, parent, false);

        return new ViewHolder(view);
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