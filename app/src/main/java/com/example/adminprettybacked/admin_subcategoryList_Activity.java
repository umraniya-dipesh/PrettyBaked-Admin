package com.example.adminprettybacked;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.adminprettybacked.Adapter.SubCategoryAdapter;
import com.example.adminprettybacked.ApiHelper.JsonField;
import com.example.adminprettybacked.ApiHelper.WebUrl;
import com.example.adminprettybacked.EditActivity.EditSubCategoryActivity;
import com.example.adminprettybacked.Listener.SubCategoryItemClickListener;
import com.example.adminprettybacked.model.SubCategory;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class admin_subcategoryList_Activity extends AppCompatActivity implements SubCategoryItemClickListener {

    private RecyclerView rvSubCategoryList;
    protected ArrayList<SubCategory> listSubCategory;
    public SubCategoryAdapter subCategoryAdapter;
    FloatingActionButton add_SubCategory_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_subcategory_list);

        getSubCategory();
        add_SubCategory_button = findViewById(R.id.add_SubCategory_button);
        rvSubCategoryList = findViewById(R.id.rvSubCategoryList);
        add_SubCategory_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(admin_subcategoryList_Activity.this,InsertSubCategoryActivity.class);
                startActivity(intent);
            }
        });

    }

    private void getSubCategory() {
        listSubCategory = new ArrayList<>();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, WebUrl.KEY_SUB_CATEGORY_VIEW_URL, new Response.Listener<String>() {
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
        RequestQueue requestQueue= Volley.newRequestQueue(admin_subcategoryList_Activity.this);
        requestQueue.add(stringRequest);

    }

    private void parseJson(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            int flag = jsonObject.optInt(JsonField.FLAG);
            if (flag == 1) {
                Log.i("msg", "in parseJson , flag == 1");
                JSONArray jsonArray = jsonObject.getJSONArray(JsonField.SUBCATEGORY_ARRAY);
                if (jsonArray.length() > 0) {
                    Log.i("msg", "in parseJson ,length()>0 ");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.optJSONObject(i);
                        String SUBCATEGORY_ID = object.optString(JsonField.SUBCATEGORY_ID);
                        String SUBCATEGORY_NAME = object.getString(JsonField.SUBCATEGORY_NAME);
                        String SUBCATEGORY_IMAGE = object.optString(JsonField.SUBCATEGORY_IMAGE);
                        String PRICE = object.getString(JsonField.PRICE);
                        String CATEGORY_ID = object.optString(JsonField.CATEGORY_ID);
                        String CATEGORY_NAME = object.getString(JsonField.CATEGORY_NAME);

                        SubCategory subCategory = new SubCategory();
                        subCategory.setSub_category_id(SUBCATEGORY_ID);
                        subCategory.setSub_category_name(SUBCATEGORY_NAME);
                        subCategory.setSub_category_image(SUBCATEGORY_IMAGE);
                        subCategory.setPrice(PRICE);
                        subCategory.setCategory_id(CATEGORY_ID);
                        subCategory.setCategory_name(CATEGORY_NAME);
                        listSubCategory.add(subCategory);
                    }
                    subCategoryAdapter = new SubCategoryAdapter(admin_subcategoryList_Activity.this, listSubCategory);
                    subCategoryAdapter.setSubCategoryItemClickListener(admin_subcategoryList_Activity.this);
                    rvSubCategoryList.setHasFixedSize(true);
                    rvSubCategoryList.setAdapter(subCategoryAdapter);

                }
            }
        }catch (JSONException e){

            e.getStackTrace();
        }
    }
    public void setOnItemClicked(ArrayList<SubCategory> listSubCategory, int position, String id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        CharSequence[] dailogitem  = {"edit data" ,"delete data"};
        builder.setTitle(listSubCategory.get(position).getSub_category_name());
        builder.setItems(dailogitem,(dialog,i)->{
            switch(i){
                case 0: {
                    Intent intent = new Intent(admin_subcategoryList_Activity.this, EditSubCategoryActivity.class);
                    SubCategory subCategory = listSubCategory.get(position);

                    String sub_category_id = subCategory.getSub_category_id();
                    String sub_category_name = subCategory.getSub_category_name();
                    String subCategoryImage = subCategory.getSub_category_image();
                    String subCategoryPrice = subCategory.getPrice();
                    String category_id = subCategory.getCategory_id();
                    String category_name = subCategory.getCategory_name();

                    intent.putExtra("position", position);
                    intent.putExtra(JsonField.SUBCATEGORY_ID, sub_category_id);
                    intent.putExtra(JsonField.SUBCATEGORY_NAME, sub_category_name);
                    intent.putExtra(JsonField.SUBCATEGORY_IMAGE, subCategoryImage);
                    intent.putExtra(JsonField.PRICE, subCategoryPrice);
                    intent.putExtra(JsonField.CATEGORY_ID, category_id);
                    intent.putExtra(JsonField.CATEGORY_NAME, category_name);

                    Log.e("msg","position subCategory:"+position+sub_category_name);

                    startActivity(intent);
                    break;
                }
                case 1:{
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(this);

                    builder1.setTitle("Confirm");
                    builder1.setMessage("Are you sure?");

                    builder1.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            // Do nothing but close the dialog
                            SubCategory subCategory = listSubCategory.get(position);
                            String subcategoryid = subCategory.getSub_category_id();
                            deleteProduct(subcategoryid);
                            dialog.dismiss();
                        }
                    });

                    builder1.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    AlertDialog alert = builder1.create();
                    alert.show();
                    break;

//                            Users users = listUser.get(position);
//                            String user_id = users.getUser_id();
//                            deleteUser(user_id);
                }
            }
        });
        builder.create().show();
    }

    private void deleteProduct(String subcategoryid) {

        StringRequest stringRequest=new StringRequest(Request.Method.POST, WebUrl.KEY_SUB_CATEGORY_DELETE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.equalsIgnoreCase("data deleted")){
                    Toast.makeText(admin_subcategoryList_Activity.this,"data deleted successfully.",Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(admin_subcategoryList_Activity.this,"data deletion Failed.",Toast.LENGTH_LONG).show();
                }
                //parseJson1(response.toString());
                Log.e("msg",response.toString());
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(admin_subcategoryList_Activity.this,"---error: "+error , Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        }){
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params=new HashMap<>();
                Log.e("Inside method","inside params"+subcategoryid);
                params.put(JsonField.SUBCATEGORY_ID,subcategoryid);
                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(admin_subcategoryList_Activity.this);
        requestQueue.add(stringRequest);
    }

}