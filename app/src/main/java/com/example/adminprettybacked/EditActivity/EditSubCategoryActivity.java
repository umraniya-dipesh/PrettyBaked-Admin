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
import com.example.adminprettybacked.admin_subcategoryList_Activity;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EditSubCategoryActivity extends AppCompatActivity {

    private  String subcategory_id,subcategory_name, image_file_name , price,category_id , category_name;
    //final EditText et_sub_category_name,et_subCategoryImage,et_subCategoryPrice,et_category_id,et_category_name;
    String new_sub_category_name,new_subCategoryImage,new_subCategoryPrice , new_category_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_sub_category);

        Button btn_submit_Product = findViewById(R.id.btn_submit_Product);
        final EditText et_sub_category_name = findViewById(R.id.et_sub_category_name);
        final EditText et_subCategoryImage = findViewById(R.id.et_subCategoryImage);
        final EditText et_subCategoryPrice= findViewById(R.id.et_subCategoryPrice);
        final EditText et_category_id = findViewById(R.id.et_category_id);
        final EditText et_category_name = findViewById(R.id.et_category_name);

        Intent intent=getIntent();
        subcategory_id=intent.getStringExtra(JsonField.SUBCATEGORY_ID);
        subcategory_name=intent.getStringExtra(JsonField.SUBCATEGORY_NAME);
        image_file_name =intent.getStringExtra(JsonField.SUBCATEGORY_IMAGE);
        price =intent.getStringExtra(JsonField.PRICE);
        category_id = intent.getStringExtra(JsonField.CATEGORY_ID);
        category_name =intent.getStringExtra(JsonField.CATEGORY_NAME);

        Log.d("id","subcategory_id:"+subcategory_id);

        et_sub_category_name.setText(subcategory_name);
        et_sub_category_name.setEnabled(true);

        et_subCategoryImage.setText(image_file_name);
        et_subCategoryImage.setEnabled(true);

        et_subCategoryPrice.setText(price);
        et_subCategoryPrice.setEnabled(true);

        et_category_id.setText(category_id);
        et_category_id.setEnabled(true);

        et_category_name.setText(category_name);

        btn_submit_Product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String  st_category_id = et_category_id.getText().toString().trim();
                //int category_id = Integer.parseInt(st_category_id);
                //if (CheckUserAddress() && CheckUserMobile())
                if (et_sub_category_name.getText().toString().trim().length() >= 6 &&
                        et_subCategoryImage.getText().toString().trim().length() >= 1 &&
                        et_subCategoryPrice.getText().toString().trim().length() >= 2 &&
                        et_category_id.getText().toString().trim().length() == 1 ){
                    //pd = new ProgressDialog(EditUserActivity.this);
                    //pd.setMessage("checking and updating");
                    //pd.show();
                    new_sub_category_name = et_sub_category_name.getText().toString().trim();
                    new_subCategoryImage = et_subCategoryImage.getText().toString().trim();
                    new_subCategoryPrice = et_subCategoryPrice.getText().toString().trim();
                    new_category_id = et_category_id.getText().toString().trim();

                    sendProductEditRequest(new_sub_category_name,new_subCategoryImage,new_subCategoryPrice,new_category_id);

                }
                else{
                    Toast.makeText(EditSubCategoryActivity.this, "data wrong inputed ", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void sendProductEditRequest(String new_sub_category_name, String new_subCategoryImage, String new_subCategoryPrice, String new_category_id) {
        Log.e("msg","in sendProductEditRequest");

        //pd.dismiss();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, WebUrl.KEY_SUB_CATEGORY_EDIT_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(EditSubCategoryActivity.this, "OnResponse", Toast.LENGTH_LONG).show();
                parseEditProductResponse(response.toString());

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(EditSubCategoryActivity.this, "onErrorResponse "+error, Toast.LENGTH_LONG).show();

            }
        }){
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params=new HashMap<>();
                Log.e("Inside method","inside params");
                params.put(JsonField.SUBCATEGORY_ID,subcategory_id);
                params.put(JsonField.SUBCATEGORY_NAME,new_sub_category_name);
                params.put(JsonField.SUBCATEGORY_IMAGE,new_subCategoryImage);
                params.put(JsonField.PRICE,new_subCategoryPrice);
                params.put(JsonField.CATEGORY_ID,new_category_id);
                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(EditSubCategoryActivity.this);
        requestQueue.add(stringRequest);

    }

    private void parseEditProductResponse(String response) {
        Log.e("msg","in parseEditProductResponse");
        //Log.d("Response",response);
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
                Intent intent=new Intent(EditSubCategoryActivity.this, admin_subcategoryList_Activity.class);
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