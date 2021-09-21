package com.example.adminprettybacked.Listener;

import com.example.adminprettybacked.model.Users;
import com.example.adminprettybacked.model.orders;

import java.util.ArrayList;

public interface OrderItemClickListener {
    public void setOnItemClicked(ArrayList<orders> listOrder, int position, String id);
}
