package sample.com.organizerapp.ui.event.attendee;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
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
import sample.com.organizerapp.databinding.ItemEventAttendeeBinding;
import sample.com.organizerapp.model.data.Attendee;


public class AttendeeAdapter extends RecyclerView.Adapter<AttendeeAdapter.ViewHolder> {
    private List<Attendee> attendee;
    private final Context context;
    private final AttendeeView view;
    private static final int VIEW_TYPE_DEFAULT = 0;


    public AttendeeAdapter(Context context, AttendeeView view) {
        this.context = context;
        this.view = view;
        attendee = new ArrayList<>();
    }


    @Override
    public int getItemViewType(int position) {
        return VIEW_TYPE_DEFAULT;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemEventAttendeeBinding itemAttendeeBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_event_attendee,
                parent,
                false);

        return new ViewHolder(itemAttendeeBinding);
    }

    @Override
    public void onBindViewHolder(AttendeeAdapter.ViewHolder holder, int position) {
        holder.itemAttendeeBinding.setAttendee(attendee.get(position));
        holder.itemAttendeeBinding.setView(view);


        holder.itemAttendeeBinding.attendeeNumber.setText("Attendee # "+ (position+1));


//        holder.itemAttendeeBinding.eventListDate.setText(DateTimeUtils.eventListTimestampMonDate(event.get(position).getEventTimestamp().get(0).getTimestampStart()));
//        holder.itemAttendeeBinding.eventListYear.setText(DateTimeUtils.eventListTimestampYear(event.get(position).getEventTimestamp().get(0).getTimestampStart()));
//
//        holder.itemAttendeeBinding.eventListLocation.setText(attendee.get(position).getEventLocation().get(0).getLocationName());
//               // +"\n"+ event.get(position).getEventLocation().get(0).getLocationAddress());
//
//
//
//

       if(attendee.get(position).getStatus().equals("1"))
       {
           holder.itemAttendeeBinding.attendeeListIcon.setBackground(context.getResources().getDrawable(R.drawable.ic_attendance_check));
           holder.itemAttendeeBinding.attendeeListIconLayout.setBackgroundColor(context.getResources().getColor(R.color.greenSuccessDark));
       }



    }


    public void clear() {
        attendee.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return attendee.size();
    }

    public void setAttendeeResult(List<Attendee> event) {
        this.attendee = event;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemEventAttendeeBinding itemAttendeeBinding;

        public ViewHolder(ItemEventAttendeeBinding itemAttendeeBinding) {
            super(itemAttendeeBinding.getRoot());
            this.itemAttendeeBinding = itemAttendeeBinding;
        }



    }
}
