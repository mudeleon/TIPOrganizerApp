package sample.com.organizerapp.ui.event.raffle;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import sample.com.organizerapp.R;
import sample.com.organizerapp.app.Endpoints;
import sample.com.organizerapp.databinding.ItemEventRaffleBinding;
import sample.com.organizerapp.model.data.Prize;


public class RafflePrizeAdapter extends RecyclerView.Adapter<RafflePrizeAdapter.ViewHolder> {
    private List<Prize> raffle;
    private final Context context;
    private final RaffleView view;
    private static final int VIEW_TYPE_DEFAULT = 0;
    private Boolean reloadImage;
    private int choosePrize,choosePrize2;



    public RafflePrizeAdapter(Context context, RaffleView view) {
        this.context = context;
        this.view = view;
        raffle = new ArrayList<>();
      //  prizeStatus = new ArrayList<>();
        choosePrize = -1;
        choosePrize2 = -1;
        reloadImage = true;

    }


    @Override
    public int getItemViewType(int position) {
        return VIEW_TYPE_DEFAULT;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       final ItemEventRaffleBinding itemRaffleBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_event_raffle,
                parent,
                false);





        return new ViewHolder(itemRaffleBinding);
    }

    @Override
    public void onBindViewHolder(final RafflePrizeAdapter.ViewHolder holder, final int position) {




        holder.itemRaffleBinding.prizeMore.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                PopupMenu popupMenu = new PopupMenu(context, holder.itemRaffleBinding.prizeMore);
                popupMenu.inflate(R.menu.prize_more);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_delete_prize:
                                view.prizeDelete(raffle.get(position));
                                break;
                            case R.id.action_edit_prize:
                                view.prizeEdit(raffle.get(position));
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });

        holder.itemRaffleBinding.prizeCardAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });





        holder.itemRaffleBinding.prizeCard.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                reloadImage = false;
               if(choosePrize == position)
                {
                    choosePrize =  -1;
                    holder.itemRaffleBinding.prizeCard.setCardBackgroundColor(Color.TRANSPARENT);
                }else
                {
                    choosePrize = position;
                    holder.itemRaffleBinding.prizeCard.setCardBackgroundColor(Color.parseColor("#2600c39c"));
                    notifyDataSetChanged();
                }



            }
        });

        holder.itemRaffleBinding.prizeDetails2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                reloadImage = false;
                handleClick(position,holder);

            }
        });


        checkClickStatus(position,holder);
        Log.d(">>>>>",reloadImage+"");
        if(position==(raffle.size()-1))
        {
            holder.itemRaffleBinding.prizeCardAdd.setVisibility(View.VISIBLE);
            holder.itemRaffleBinding.prizeCard.setVisibility(View.GONE);
        }else {


            holder.itemRaffleBinding.setPrize(raffle.get(position));
            holder.itemRaffleBinding.setView(view);

            if(reloadImage) {
                Log.d(">>>>>",position+"");
                Glide.with(holder.itemView.getContext())
                        .load(Endpoints.EVENT_URL_IMAGE + raffle.get(position).getPriceImage())
                        .centerCrop()
                        .error(R.drawable.ic_raffle_prize)
                        .into(holder.itemRaffleBinding.prizeImageMain);
            }
        }





    }

    public void checkClickStatus(int position,RafflePrizeAdapter.ViewHolder holder)
    {

        if(choosePrize != position)
        {
            holder.itemRaffleBinding.prizeCard.setCardBackgroundColor(Color.TRANSPARENT);
        }


        if(choosePrize2 != position)
        {

            LinearLayout.LayoutParams childParam1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0);
            childParam1.weight = 0.7f;
            LinearLayout.LayoutParams childParam2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0);
            childParam2.weight = 0.3f;
            holder.itemRaffleBinding.prizeImage.setLayoutParams(childParam1);
            holder.itemRaffleBinding.prizeDetails.setLayoutParams(childParam2);
            holder.itemRaffleBinding.prizeDescription.setVisibility(View.GONE);
            holder.itemRaffleBinding.prizeQuantity.setVisibility(View.GONE);
        }
    }


    public void handleClick(int position,RafflePrizeAdapter.ViewHolder holder)
    {


        if(choosePrize2 == position) {
            LinearLayout.LayoutParams childParam1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0);
            childParam1.weight = 0.7f;
            LinearLayout.LayoutParams childParam2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0);
            childParam2.weight = 0.3f;
            holder.itemRaffleBinding.prizeImage.setLayoutParams(childParam1);
            holder.itemRaffleBinding.prizeDetails.setLayoutParams(childParam2);
            holder.itemRaffleBinding.prizeDescription.setVisibility(View.GONE);
            holder.itemRaffleBinding.prizeQuantity.setVisibility(View.GONE);
            choosePrize2 = -1;


        }else
        {
            LinearLayout.LayoutParams childParam1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0);
            childParam1.weight = 0.8f;
            LinearLayout.LayoutParams childParam2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0);
            childParam2.weight = 0.2f;
            holder.itemRaffleBinding.prizeImage.setLayoutParams(childParam2);
            holder.itemRaffleBinding.prizeDetails.setLayoutParams(childParam1);
            holder.itemRaffleBinding.prizeDescription.setVisibility(View.VISIBLE);
            holder.itemRaffleBinding.prizeQuantity.setVisibility(View.VISIBLE);

            choosePrize2 = position;
            notifyDataSetChanged();
        }

    }





    public void clear() {
        raffle.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return raffle.size();
    }


    public int getChoosen()
    {

        return choosePrize;
    }

    public Prize getPrize()
    {

        return raffle.get(choosePrize);
    }

    public int prizeQuantity()
    {
        if(choosePrize!=-1)
        return Integer.parseInt(raffle.get(choosePrize).getPrizeQuantity());
        else
        return 0;
    }

    public void setRaffleResult(List<Prize> event) {
        this.raffle = event;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemEventRaffleBinding itemRaffleBinding;

        public ViewHolder(ItemEventRaffleBinding itemRaffleBinding) {
            super(itemRaffleBinding.getRoot());
            this.itemRaffleBinding = itemRaffleBinding;
        }



    }
}
