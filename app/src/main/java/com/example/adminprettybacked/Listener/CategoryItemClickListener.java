package com.example.adminprettybacked.Listener;
import com.example.adminprettybacked.model.Category;
import com.example.adminprettybacked.model.SubCategory;

import java.util.ArrayList;

public interface CategoryItemClickListener {
    public void setOnItemClicked(ArrayList<Category> listCategory, int position, String id);
}
