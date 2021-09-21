package com.example.adminprettybacked.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.adminprettybacked.ApiHelper.WebUrl;
import com.example.adminprettybacked.Listener.UsersItemClickListener;
import com.example.adminprettybacked.model.Users;
import com.example.adminprettybacked.R;

import androidx.annotation.NonNull;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import androidx.recyclerview.widget.RecyclerView;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {
    Context context;
    ArrayList<Users> listUser;
    UsersItemClickListener usersItemClickListener;

//-------------------------------------------------------

    public UsersItemClickListener getUsersItemClickListener() {
        return usersItemClickListener;
    }

    public void setUsersItemClickListener(UsersItemClickListener usersItemClickListener) {
        this.usersItemClickListener = usersItemClickListener;
    }

    public UsersAdapter(Context context, ArrayList<Users> listUser) {
        this.context = context;
        this.listUser = listUser;
    }
//-----------------------------------------


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        View view= LayoutInflater.from(context).inflate(R.layout.user_raw_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull UsersAdapter.ViewHolder holder, int position) {
        Users users=listUser.get(position);

        String id=users.getUser_id();
        String user_name=users.getUser_name();
        String user_email=users.getUser_email();
        String user_mobile=users.getUser_mobile();
        String user_gender=users.getUser_gender();
        String user_address=users.getUser_address();

        holder.tvUserName.setText(user_name);
        holder.tvUserEmail.setText(user_email);
        holder.tvUserMobile.setText(user_mobile);
        holder.tvUserGender.setText(user_gender);
        holder.tvUserAddress.setText(user_address);

        //Glide.with(context).load(WebUrl.KEY_IMAGE_URL+users.getCategory_image()).into(holder.ivCatImage);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UsersItemClickListener listener=getUsersItemClickListener();
                //listener.setOnItemClicked(listUser,position);
                listener.setOnItemClicked(listUser,position,id);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listUser.size();
    }
    //------------------------------------------------------------------------

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvUserName;
        TextView tvUserEmail;
        TextView tvUserMobile;
        TextView tvUserGender;
        TextView tvUserAddress;

        public ViewHolder(@NonNull  View itemView) {
            super(itemView);
            tvUserName=itemView.findViewById(R.id.tvUserName);
            tvUserEmail=itemView.findViewById(R.id.tvUserEmail);
            tvUserMobile=itemView.findViewById(R.id.tvUserMobile);
            tvUserGender=itemView.findViewById(R.id.tvUserGender);
            tvUserAddress=itemView.findViewById(R.id.tvUserAddress);

        }
    }


}
