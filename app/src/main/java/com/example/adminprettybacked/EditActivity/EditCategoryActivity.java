package com.example.adminprettybacked.EditActivity;

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
import com.example.adminprettybacked.R;
import com.example.adminprettybacked.adminCategoryListActivity;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EditCategoryActivity extends AppCompatActivity {

    String new_category_id,new_category_name,new_categoryImage;
    private  String  category_id , category_name, category_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_category);

        Button btn_submit_category = findViewById(R.id.btn_submit_category);

         EditText et_category_id = findViewById(R.id.et_category_id);
         EditText et_category_name = findViewById(R.id.et_category_name);
         EditText et_CategoryImage = findViewById(R.id.et_CategoryImage);

        Intent intent=getIntent();
        category_id = intent.getStringExtra(JsonField.CATEGORY_ID);
        category_name =intent.getStringExtra(JsonField.CATEGORY_NAME);
        category_image =intent.getStringExtra(JsonField.CATEGORY_IMAGE);


        et_category_id.setText(category_id);
        et_category_id.setEnabled(false);
        et_category_name.setText(category_name);
        et_category_name.setEnabled(true);
        et_CategoryImage.setText(category_image);
        et_CategoryImage.setEnabled(true);


        btn_submit_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String  st_category_id = et_category_id.getText().toString().trim();
                //int category_id = Integer.parseInt(st_category_id);
                //if (CheckUserAddress() && CheckUserMobile())
                if (et_category_name.getText().toString().trim().length() >= 6 &&
                        et_category_id.getText().toString().trim().length() >= 1 &&
                        et_CategoryImage.getText().toString().trim().length() >= 1
                         ){
                    //pd = new ProgressDialog(EditUserActivity.this);
                    //pd.setMessage("checking and updating");
                    //pd.show();
                    new_category_name = et_category_name.getText().toString().trim();
                    new_categoryImage = et_CategoryImage.getText().toString().trim();

                    sendCategoryEditRequest(category_id,et_category_name.getText().toString(),et_CategoryImage.getText().toString());

                }
                else{
                    Toast.makeText(EditCategoryActivity.this, "data wrong inputed ", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void sendCategoryEditRequest(String category_id,String category_name, String categoryImage) {
        Log.e("msg","in sendProductEditRequest");

        StringRequest stringRequest = new StringRequest(Request.Method.POST, WebUrl.KEY_CATEGORY_EDIT_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(EditCategoryActivity.this, "OnResponse", Toast.LENGTH_LONG).show();
                parseEditCategoryResponse(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(EditCategoryActivity.this, "onErrorResponse "+error, Toast.LENGTH_LONG).show();

            }
        }){
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params=new HashMap<>();
                Log.e("Inside method","inside params");
                params.put(JsonField.CATEGORY_ID,category_id);
                params.put(JsonField.CATEGORY_NAME,category_name);
                params.put(JsonField.CATEGORY_IMAGE,categoryImage);
                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(EditCategoryActivity.this);
        requestQueue.add(stringRequest);
    }

    private void parseEditCategoryResponse(String response) {
        try {
            JSONObject jsonObject=new JSONObject(response);
            int flag=jsonObject.optInt(JsonField.FLAG);
            String message=jsonObject.optString(JsonField.MESSAGES);
            Log.e("msg","flag:"+flag);
            Log.e("msg","msg:"+message);
            if(flag==1)
            {
                Log.e("msg","Successfully updated.");
                Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(EditCategoryActivity.this, adminCategoryListActivity.class);
                startActivity(intent);
                finish();
            }
            else
            {
                Log.e("msg",message);
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e)
        {
            Log.e("msg","e:"+e);
            e.printStackTrace();
        }
    }
}