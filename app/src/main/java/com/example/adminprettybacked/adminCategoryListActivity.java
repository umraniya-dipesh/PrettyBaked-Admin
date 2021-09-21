package com.example.adminprettybacked;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.adminprettybacked.Adapter.CategoryAdapter;
import com.example.adminprettybacked.ApiHelper.JsonField;
import com.example.adminprettybacked.ApiHelper.WebUrl;
import com.example.adminprettybacked.EditActivity.EditCategoryActivity;
import com.example.adminprettybacked.Listener.CategoryItemClickListener;
import com.example.adminprettybacked.model.Category;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class adminCategoryListActivity extends AppCompatActivity implements CategoryItemClickListener {

    private RecyclerView rvCategoryList;
    protected ArrayList<Category> listCategory;
    public CategoryAdapter CategoryAdapter;
    FloatingActionButton add_Category_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category_list);

        getCategory();
        add_Category_button = findViewById(R.id.add_Category_button);
        rvCategoryList = findViewById(R.id.rvCategoryList);
        add_Category_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(adminCategoryListActivity.this,InsertCategoryActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getCategory() {
        listCategory = new ArrayList<>();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, WebUrl.KEY_CATEGORY_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                parseJson(response.toString());
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        RequestQueue requestQueue= Volley.newRequestQueue(adminCategoryListActivity.this);
        requestQueue.add(stringRequest);
    }
    private void parseJson(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            int flag = jsonObject.optInt(JsonField.FLAG);
            if (flag == 1) {
                Log.i("msg", "in parseJson , flag == 1");
                JSONArray jsonArray = jsonObject.getJSONArray(JsonField.CATEGORY_ARRAY);
                if (jsonArray.length() > 0) {
                    Log.i("msg", "in parseJson ,length()>0 ");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.optJSONObject(i);


                        String CATEGORY_ID = object.optString(JsonField.CATEGORY_ID);
                        String CATEGORY_NAME = object.getString(JsonField.CATEGORY_NAME);
                        String CATEGORY_IMAGE = object.optString(JsonField.CATEGORY_IMAGE);

                        Category category = new Category();

                        category.setCategory_image(CATEGORY_IMAGE);
                        category.setCategory_id(CATEGORY_ID);
                        category.setCategory_name(CATEGORY_NAME);
                        listCategory.add(category);
                    }
                    CategoryAdapter = new CategoryAdapter(adminCategoryListActivity.this, listCategory);
                    CategoryAdapter.setCategoryItemClickListener(adminCategoryListActivity.this);
                    rvCategoryList.setAdapter(CategoryAdapter);

                }
            }
        }catch (JSONException e){

            e.getStackTrace();
        }
    }
    public void setOnItemClicked(ArrayList<Category> listCategory, int position, String id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        CharSequence[] dailogitem  = {"edit data"};
        builder.setTitle(listCategory.get(position).getCategory_name());
        builder.setItems(dailogitem,(dialog,i)->{
            switch(i){
                case 0: {
                    Intent intent = new Intent(adminCategoryListActivity.this, EditCategoryActivity.class);
                    Category category = listCategory.get(position);

                    String category_id = category.getCategory_id();
                    String category_name = category.getCategory_name();
                    String CategoryImage = category.getCategory_image();

                    intent.putExtra("position", position);
                    intent.putExtra(JsonField.CATEGORY_ID, category_id);
                    intent.putExtra(JsonField.CATEGORY_NAME, category_name);
                    intent.putExtra(JsonField.CATEGORY_IMAGE, CategoryImage);

                    Log.e("msg","position subCategory:"+position+category_name);

                    startActivity(intent);
                    break;
                }

            }
        });
        builder.create().show();
    }

}