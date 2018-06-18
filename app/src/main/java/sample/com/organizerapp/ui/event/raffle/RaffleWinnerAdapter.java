package sample.com.organizerapp.ui.event.raffle;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import sample.com.organizerapp.R;
import sample.com.organizerapp.databinding.ItemEventRaffleBinding;
import sample.com.organizerapp.databinding.ItemEventWinnerBinding;
import sample.com.organizerapp.model.data.Attendee;
import sample.com.organizerapp.model.data.Prize;
import sample.com.organizerapp.ui.event.raffle.RaffleView;


public class RaffleWinnerAdapter extends RecyclerView.Adapter<RaffleWinnerAdapter.ViewHolder> {
    private List<Attendee> winner;
    private final Context context;
    private final RaffleView view;
    private static final int VIEW_TYPE_DEFAULT = 0;
    private List<Boolean> prizeStatus;



    public RaffleWinnerAdapter(Context context, RaffleView view) {
        this.context = context;
        this.view = view;
        winner = new ArrayList<>();

    }


    @Override
    public int getItemViewType(int position) {
        return VIEW_TYPE_DEFAULT;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       final ItemEventWinnerBinding itemWinnerBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_event_winner,
                parent,
                false);





        return new ViewHolder(itemWinnerBinding);
    }

    @Override
    public void onBindViewHolder(final RaffleWinnerAdapter.ViewHolder holder, final int position) {





            holder.itemWinnerBinding.setAttendee(winner.get(position));
            holder.itemWinnerBinding.setView(view);

    }








    public void clear() {
        winner.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return winner.size();
    }

    public void setRaffleResultWinner(List<Attendee> winner) {
        this.winner = winner;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemEventWinnerBinding itemWinnerBinding;

        public ViewHolder(ItemEventWinnerBinding itemWinnerBinding) {
            super(itemWinnerBinding.getRoot());
            this.itemWinnerBinding = itemWinnerBinding;
        }



    }
}
