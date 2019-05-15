package com.example.ifamily;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ifamily.Models.EventsModel;

import java.util.List;

public class ListAdapterForEvents extends RecyclerView.Adapter {

    public List<EventsModel> events;
    public ListAdapterForEvents(List<EventsModel> eventsmodel){this.events= eventsmodel;}

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
        return events.size();
    }



    private class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView itemTitle;

        public ListViewHolder(View itemView)
        {
            super(itemView);
            itemTitle = (TextView) itemView.findViewById(R.id.smallCardTitle);
            itemView.setOnClickListener(this);
        }

        public void bindView (int position){
            itemTitle.setText(events.get(position).text);
            Log.d("EVENTAS",events.get(position).text);
        }

        public void onClick(View view){

        }


    }
}
