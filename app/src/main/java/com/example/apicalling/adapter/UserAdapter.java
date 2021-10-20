package com.example.apicalling.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apicalling.R;
import com.example.apicalling.pojo.Root;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class UserAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Root> resultsList;
    private Context context;
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private boolean isLoadingAdded = false;

    public UserAdapter(ArrayList<Root> resultsList, Context context) {
        this.resultsList = resultsList;
        this.context = context;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(context).inflate(R.layout.card_view_layout, parent, false);
            return new ItemViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            populateItemRows((ItemViewHolder) holder, position);
        } else if (holder instanceof LoadingViewHolder) {
            showLoadingView((LoadingViewHolder) holder, position);
        }
    }

    @Override
    public int getItemCount() {
        if (resultsList.size() != 0) {
            return resultsList.size();
        }else {
            return 0;
        }
    }

    /**
     * The following method decides the type of ViewHolder to display in the RecyclerView
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        return (position == resultsList.size() - 1 && isLoadingAdded) ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView userName, userEmail;


        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.profile_image);
            userName = itemView.findViewById(R.id.user_name);
            userEmail = itemView.findViewById(R.id.user_email);
        }

    }

    public class LoadingViewHolder extends RecyclerView.ViewHolder {

        ProgressBar progressBar;

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
        }

    }

        private void showLoadingView(LoadingViewHolder viewHolder, int position) {
        }

        private void populateItemRows(UserAdapter.ItemViewHolder viewHolder, int position) {
            Root userList = resultsList.get(position);
           // Picasso.get().load( userList.getData().get(position).getAvatar()).placeholder(R.drawable.user).into(viewHolder.image);
            viewHolder.userName.setText(userList.getData().get(position).getFirst_name() + " " + userList.getData().get(position).getLast_name());
            viewHolder.userEmail.setText(userList.getData().get(position).getEmail());

        }

    public void add(Root r) {
        resultsList.add(r);
        notifyItemInserted(resultsList.size() - 1);
    }

    public void addAll(List<Root> moveResults) {
        for (Root result : moveResults) {
            add(result);
        }
    }

    public void remove(Root r) {
        int position = resultsList.indexOf(r);
        if (position > -1) {
            resultsList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        isLoadingAdded = false;
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }


    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new Root());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = resultsList.size() - 1;
        Root result = getItem(position);

        if (result != null) {
            resultsList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public Root getItem(int position) {
        return resultsList.get(position);
    }


}
