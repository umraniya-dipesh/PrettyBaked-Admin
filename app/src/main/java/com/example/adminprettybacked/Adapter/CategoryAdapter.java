package com.example.adminprettybacked.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.adminprettybacked.ApiHelper.WebUrl;
import com.example.adminprettybacked.Listener.CategoryItemClickListener;
import com.example.adminprettybacked.R;
import com.example.adminprettybacked.model.Category;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder>{
    Context context;
    ArrayList<Category> listCategory;
    CategoryItemClickListener CategoryItemClickListener;

    public CategoryItemClickListener getCategoryItemClickListener() { return CategoryItemClickListener; }

    public void setCategoryItemClickListener(CategoryItemClickListener CategoryItemClickListener) {
        this.CategoryItemClickListener =CategoryItemClickListener ;
    }
    public CategoryAdapter(Context context, ArrayList<Category> listCategory) {
        this.context = context;
        this.listCategory = listCategory;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        View view= LayoutInflater.from(context).inflate(R.layout.category_raw_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CategoryAdapter.ViewHolder holder, int position) {
        Category category=listCategory.get(position);

        String category_id=category.getCategory_id();
        String category_name=category.getCategory_name();
        String category_image=category.getCategory_image();


        holder.tvCategory_id.setText(category_id);
        holder.tvCategory_name.setText(category_name);
        holder.tvCategory_image.setText(category_image);
        Glide.with(context).load(WebUrl.KEY_IMAGE_URL+category.getCategory_image()).into(holder.iv_category_image);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CategoryItemClickListener slistener=getCategoryItemClickListener();
                //listener.setOnItemClicked(listUser,position);
                slistener.setOnItemClicked(listCategory,position,category_id);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listCategory.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvCategory_id;
        TextView tvCategory_name,tvCategory_image;
        ImageView iv_category_image;
        public ViewHolder(@NonNull  View itemView) {
            super(itemView);
            tvCategory_id=itemView.findViewById(R.id.tv_category_id);
            tvCategory_name=itemView.findViewById(R.id.tv_category_name);
            tvCategory_image=itemView.findViewById(R.id.tv_category_image);
            iv_category_image = itemView.findViewById(R.id.iv_category_image);

        }
    }
}
