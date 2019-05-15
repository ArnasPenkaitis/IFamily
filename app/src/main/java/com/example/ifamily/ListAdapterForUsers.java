package com.example.ifamily;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ifamily.Models.UsersModel;

import java.util.List;

public class ListAdapterForUsers extends RecyclerView.Adapter{

    public List<UsersModel> users;
    public ListAdapterForUsers(List<UsersModel> usersmodels){this.users= usersmodels;}

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.smallcard,viewGroup,false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ((ListViewHolder) viewHolder ).bindView(i);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }



    private class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView itemTitle;
        private View card;


        public ListViewHolder(View itemView)
        {
            super(itemView);
            itemTitle = (TextView) itemView.findViewById(R.id.smallCardTitle);
            card = (View) itemView.findViewById(R.id.smallCard);
            itemView.setOnClickListener(this);
        }

        public void bindView (final int position){
            itemTitle.setText(users.get(position).name);

            card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Tracker.changeTarget(users.get(position).lattitude,users.get(position).longtitude);
                }
            });
        }

        public void onClick(View view){

        }


    }
}
