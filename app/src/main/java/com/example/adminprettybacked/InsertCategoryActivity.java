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

public class InsertCategoryActivity extends AppCompatActivity {

    String CategoryImage,CategoryName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_category);

        EditText et_category_name = findViewById(R.id.et_category_name);
        EditText et_CategoryImage = findViewById(R.id.et_CategoryImage);
        Button btn_add_category = findViewById(R.id.btn_add_category);

        btn_add_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CategoryName = et_category_name.getText().toString();
                CategoryImage = et_CategoryImage.getText().toString().trim();

                if (CategoryName.trim().length() >=3) {
                    if (CategoryImage.length() >= 1) {
                        Toast.makeText(InsertCategoryActivity.this, "data inserted properly.", Toast.LENGTH_SHORT).show();
                        insertCategory(et_category_name.getText().toString(),et_CategoryImage.getText().toString().trim());
                    } else {
                        Toast.makeText(InsertCategoryActivity.this, "Image invalid.", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(InsertCategoryActivity.this, "sub_category_name invalid.", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void insertCategory(String categoryName, String categoryImage) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, WebUrl.KEY_CATEGORY_ADD_URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                parseInsertCategoryResponse(response);
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
                params.put(JsonField.CATEGORY_NAME, categoryName);
                params.put(JsonField.CATEGORY_IMAGE, categoryImage);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(InsertCategoryActivity.this);
        requestQueue.add(stringRequest);
    }

    private void parseInsertCategoryResponse(String response) {
        Log.d("Response","response"+response);
        try {
            JSONObject jsonObject=new JSONObject(response);
            int flag=jsonObject.optInt(JsonField.FLAG);
            String message=jsonObject.optString(JsonField.MESSAGES);
            if(flag==1)
            {
                Toast.makeText(this, "Successfully inserted", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(InsertCategoryActivity.this, adminCategoryListActivity.class);
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