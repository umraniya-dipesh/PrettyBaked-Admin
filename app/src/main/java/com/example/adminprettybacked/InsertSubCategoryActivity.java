package com.example.adminprettybacked;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.adminprettybacked.ApiHelper.JsonField;
import com.example.adminprettybacked.ApiHelper.WebUrl;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class InsertSubCategoryActivity extends AppCompatActivity {

    //EditText et_sub_category_name,et_subCategoryImage,et_subCategoryPrice,et_category_id;
    String sub_category_name,subCategoryImage,subCategoryPrice,category_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_sub_category);

        Button btn_Insert_Product = findViewById(R.id.btn_Insert_Product);
        final EditText et_sub_category_name = findViewById(R.id.et_sub_category_name);
        final EditText et_subCategoryImage = findViewById(R.id.et_subCategoryImage);
        final EditText et_subCategoryPrice= findViewById(R.id.et_subCategoryPrice);
        final EditText et_category_id = findViewById(R.id.et_category_id);

        btn_Insert_Product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sub_category_name = et_sub_category_name.getText().toString();
                subCategoryImage = et_subCategoryImage.getText().toString().trim();
                subCategoryPrice = et_subCategoryPrice.getText().toString().trim();
                category_id = et_category_id.getText().toString();

                /*
                if (sub_category_name.trim().length() >=3 && subCategoryImage.length() >=1 && subCategoryPrice.length()>2 &&
                        (category_id == "1" ||category_id == "2"|| category_id == "3"|| category_id == "4" )){

                    Toast.makeText(InsertSubCategoryActivity.this, "data inserted properly.", Toast.LENGTH_SHORT).show();
                    insertSubCategory(sub_category_name,subCategoryImage,subCategoryPrice,category_id);

                }else {
                    Toast.makeText(InsertSubCategoryActivity.this, "data inserted invalid.", Toast.LENGTH_SHORT).show();
                }*/
                if (sub_category_name.trim().length() >=3) {
                    if (subCategoryImage.length() >=1){
                        if (subCategoryPrice.length()>2){
                            if (category_id.equals("1") ||category_id.equals("5")||category_id.equals("2")|| category_id.equals("3")|| category_id.equals("4") ){
                                Toast.makeText(InsertSubCategoryActivity.this, "data inserted properly.", Toast.LENGTH_SHORT).show();
                                insertSubCategory(sub_category_name,subCategoryImage,subCategoryPrice,category_id);
                            }else {
                                Toast.makeText(InsertSubCategoryActivity.this, "category_id invalid.", Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(InsertSubCategoryActivity.this, "Price invalid.", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(InsertSubCategoryActivity.this, "Image invalid.", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(InsertSubCategoryActivity.this, "sub_category_name invalid.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void insertSubCategory(String sub_category_name, String subCategoryImage, String subCategoryPrice, String category_id) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, WebUrl.KEY_SUB_CATEGORY_ADD_URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                parseInsertProductResponse(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                Log.d("Inside method", "inside params");
                params.put(JsonField.SUBCATEGORY_NAME, sub_category_name);
                params.put(JsonField.SUBCATEGORY_IMAGE, subCategoryImage);
                params.put(JsonField.PRICE, subCategoryPrice);
                params.put(JsonField.CATEGORY_ID, category_id);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(InsertSubCategoryActivity.this);
        requestQueue.add(stringRequest);
    }

    private void parseInsertProductResponse(String response) {
        Log.d("Response","response"+response);
        try {
            JSONObject jsonObject=new JSONObject(response);
            int flag=jsonObject.optInt(JsonField.FLAG);
            String message=jsonObject.optString(JsonField.MESSAGES);
            if(flag==1)
            {
                Toast.makeText(this, "Successfully inserted", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(InsertSubCategoryActivity.this, admin_subcategoryList_Activity.class);
                startActivity(intent);
                finish();
            }
            else
            {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}