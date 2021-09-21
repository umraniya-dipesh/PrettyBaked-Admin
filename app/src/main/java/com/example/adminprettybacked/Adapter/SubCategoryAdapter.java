package com.example.adminprettybacked.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.adminprettybacked.ApiHelper.WebUrl;
import com.example.adminprettybacked.Listener.SubCategoryItemClickListener;
import com.example.adminprettybacked.R;
import com.example.adminprettybacked.model.SubCategory;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.ViewHolder> {

    Context context;
    ArrayList<SubCategory> listSubCategory;
    SubCategoryItemClickListener subCategoryItemClickListener;

    public SubCategoryItemClickListener getSubCategoryItemClickListener() { return subCategoryItemClickListener; }

    public void setSubCategoryItemClickListener(SubCategoryItemClickListener subCategoryItemClickListener) {
        this.subCategoryItemClickListener =subCategoryItemClickListener ;
    }
    public SubCategoryAdapter(Context context, ArrayList<SubCategory> listSubCategory) {
        this.context = context;
        this.listSubCategory = listSubCategory;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        View view= LayoutInflater.from(context).inflate(R.layout.subcategory_raw_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull SubCategoryAdapter.ViewHolder holder, int position) {
        SubCategory subCategory=listSubCategory.get(position);

        String Sub_category_id=subCategory.getSub_category_id();
        String Sub_category_name=subCategory.getSub_category_name();
        String Sub_category_image=subCategory.getSub_category_image();
        String Price=subCategory.getPrice();
        String Category_id=subCategory.getCategory_id();
        String Category_name = subCategory.getCategory_name();

        holder.tvSub_category_name.setText(Sub_category_name);
        holder.tvSub_category_image.setText(Sub_category_image);
        holder.tvPrice.setText(Price);
        holder.tvCategory_id.setText(Category_id);
        holder.tvCategory_name.setText(Category_name);


        Glide.with(context).load(WebUrl.KEY_IMAGE_URL_SUB+subCategory.getSub_category_image()).into(holder.iv_Sub_category_image);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SubCategoryItemClickListener slistener=getSubCategoryItemClickListener();
                //listener.setOnItemClicked(listUser,position);
                slistener.setOnItemClicked(listSubCategory,position,Sub_category_id);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listSubCategory.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvSub_category_name;
        TextView tvSub_category_image;
        TextView tvPrice;
        TextView tvCategory_id;
        TextView tvCategory_name;
        ImageView iv_Sub_category_image;
        public ViewHolder(@NonNull  View itemView) {
            super(itemView);
            tvSub_category_name=itemView.findViewById(R.id.tvSub_category_name);
            tvSub_category_image=itemView.findViewById(R.id.tvSub_category_image);
            tvPrice=itemView.findViewById(R.id.tvPrice);
            tvCategory_id=itemView.findViewById(R.id.tvCategory_id);
            tvCategory_name=itemView.findViewById(R.id.tvCategory_name);
            iv_Sub_category_image = itemView.findViewById(R.id.iv_Sub_category_image);

        }
    }


}
