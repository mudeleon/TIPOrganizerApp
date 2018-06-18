package sample.com.organizerapp.ui.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v4.app.ShareCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import sample.com.organizerapp.R;
import sample.com.organizerapp.app.Endpoints;
import sample.com.organizerapp.databinding.ItemEventListBinding;
import sample.com.organizerapp.model.data.Event;
import sample.com.organizerapp.ui.event.EventActivity;
import sample.com.organizerapp.util.DateTimeUtils;


public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.ViewHolder> {
    private List<Event> event;
    private final Context context;
    private final EventListView view;
    private static final int VIEW_TYPE_DEFAULT = 0;


    public EventListAdapter(Context context, EventListView view) {
        this.context = context;
        this.view = view;
        event = new ArrayList<>();
    }


    @Override
    public int getItemViewType(int position) {
        return VIEW_TYPE_DEFAULT;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemEventListBinding itemEventBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_event_list,
                parent,
                false);


        itemEventBinding.eventListLinear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Intent activityChangeIntent = new Intent(context, EventActivity.class);
                context.startActivity(activityChangeIntent);


            }
        });

        itemEventBinding.eventListShare.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                ShareCompat.IntentBuilder.from((Activity) context)
                        .setType("text/plain")
                        .setChooserTitle("Share Event")
                        .setText("https://eventsapp.dgts.ph/")
                        .startChooser();


            }
        });



        return new ViewHolder(itemEventBinding);
    }

    @Override
    public void onBindViewHolder(EventListAdapter.ViewHolder holder, int position) {
        holder.itemEventBinding.setEvent(event.get(position));
        holder.itemEventBinding.setView(view);


      holder.itemEventBinding.eventListDate.setText(DateTimeUtils.eventListTimestampMonDate(event.get(position).getEventTimestamp().get(0).getTimestampStart()));
        holder.itemEventBinding.eventListYear.setText(DateTimeUtils.eventListTimestampYear(event.get(position).getEventTimestamp().get(0).getTimestampStart()));

       holder.itemEventBinding.eventListLocation.setText(event.get(position).getEventLocation().get(0).getLocationName());
               // +"\n"+ event.get(position).getEventLocation().get(0).getLocationAddress());




        Glide.with(holder.itemView.getContext())
                .load(Endpoints.EVENT_URL_IMAGE+event.get(position).getEventImage())
                .centerCrop()
                .error(R.drawable.sample_event2)
                .into(holder.itemEventBinding.eventListImage);



    }


    public void clear() {
        event.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return event.size();
    }

    public void setEventResult(List<Event> event) {
        this.event = event;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemEventListBinding itemEventBinding;

        public ViewHolder(ItemEventListBinding itemEventBinding) {
            super(itemEventBinding.getRoot());
            this.itemEventBinding = itemEventBinding;
        }



    }
}
