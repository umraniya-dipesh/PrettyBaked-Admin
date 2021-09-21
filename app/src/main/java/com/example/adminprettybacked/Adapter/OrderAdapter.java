package com.example.adminprettybacked.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.adminprettybacked.ApiHelper.WebUrl;
import com.example.adminprettybacked.Listener.OrderItemClickListener;
import com.example.adminprettybacked.Listener.SubCategoryItemClickListener;
import com.example.adminprettybacked.R;
import com.example.adminprettybacked.model.SubCategory;
import com.example.adminprettybacked.model.orders;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    Context context;
    ArrayList<orders> listOrder;
    OrderItemClickListener orderItemClickListener;

    public OrderItemClickListener getOrderItemClickListener() { return orderItemClickListener; }

    public void setOrderItemClickListener(OrderItemClickListener orderItemClickListener) {
        this.orderItemClickListener =orderItemClickListener ;
    }
    public OrderAdapter(Context context, ArrayList<orders> listOrder) {
        this.context = context;
        this.listOrder = listOrder;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        View view= LayoutInflater.from(context).inflate(R.layout.orders_raw_item,parent,false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        orders order=listOrder.get(position);

        String order_id=order.getOrder_id();
        String Sub_category_id=order.getSub_category_id();
        String Sub_category_name=order.getSub_category_name();
        String Sub_category_image=order.getSub_category_image();
        String Price=order.getPrice();
        String User_id=order.getUser_id();
        String User_name = order.getUser_name();
        String User_mobile = order.getUser_mobile();


        holder.tv_user_name.setText(User_name);
        //holder.tv_sub_Category_id.setText(Sub_category_id);
        //holder.tv_sub_category_name.setText(Sub_category_name);
        holder.tvPrice.setText(Price);
        holder.tv_user_mobile.setText(User_mobile);
        //holder.tvUser_name.setText(User_name);


        Glide.with(context).load(WebUrl.KEY_IMAGE_URL_SUB+order.getSub_category_image()).into(holder.iv_Sub_category_image);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderItemClickListener slistener=getOrderItemClickListener();
                //listener.setOnItemClicked(listUser,position);
                slistener.setOnItemClicked(listOrder,position,Sub_category_id);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listOrder.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_user_name;
        TextView tv_user_mobile;
        TextView tvPrice;
        TextView tv_sub_Category_id;
        TextView tv_sub_category_name;
        ImageView iv_Sub_category_image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_user_name = itemView.findViewById(R.id.tv_user_name);
            tv_user_mobile = itemView.findViewById(R.id.tv_user_mobile);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tv_sub_Category_id = itemView.findViewById(R.id.tv_sub_Category_id);
            tv_sub_category_name = itemView.findViewById(R.id.tv_sub_category_name);
            iv_Sub_category_image = itemView.findViewById(R.id.iv_Sub_category_image);

        }
    }
}
