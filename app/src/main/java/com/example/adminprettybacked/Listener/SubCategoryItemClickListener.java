package com.example.adminprettybacked.Listener;

import com.example.adminprettybacked.model.SubCategory;
import com.example.adminprettybacked.model.Users;

import java.util.ArrayList;

public interface SubCategoryItemClickListener {
    public void setOnItemClicked(ArrayList<SubCategory> listSubCategory, int position, String id);
}
