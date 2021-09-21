package com.example.adminprettybacked;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.adminprettybacked.Adapter.OrderAdapter;
import com.example.adminprettybacked.Adapter.SubCategoryAdapter;
import com.example.adminprettybacked.ApiHelper.JsonField;
import com.example.adminprettybacked.ApiHelper.WebUrl;
import com.example.adminprettybacked.Listener.OrderItemClickListener;
import com.example.adminprettybacked.Listener.SubCategoryItemClickListener;
import com.example.adminprettybacked.model.SubCategory;
import com.example.adminprettybacked.model.orders;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class admin_ordersList_Activity extends AppCompatActivity  implements OrderItemClickListener {

    private RecyclerView rvOrderList;
    protected ArrayList<orders> listOrder;;
    public OrderAdapter orderAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_orders_list);

        rvOrderList = findViewById(R.id.rvOrderList);

        getOrdersList();


    }

    private void getOrdersList() {
        listOrder = new ArrayList<>();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, WebUrl.KEY_ORDERS_VIEW_URL, new Response.Listener<String>() {
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
        RequestQueue requestQueue= Volley.newRequestQueue(admin_ordersList_Activity.this);
        requestQueue.add(stringRequest);

    }
    private void parseJson(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            int flag = jsonObject.optInt(JsonField.FLAG);
            String totalprice = jsonObject.optString(JsonField.TOTALPRICE);
            if (flag == 1) {
                Log.i("msg", "in parseJson , flag == 1");
                Log.i("msg", "in parseJson"+totalprice);
                JSONArray jsonArray = jsonObject.getJSONArray(JsonField.ORDER_ARRAY);
                if (jsonArray.length() > 0) {
                    Log.i("msg", "in parseJson ,length()>0 ");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.optJSONObject(i);
                        String SUBCATEGORY_ID = object.optString(JsonField.SUBCATEGORY_ID);
                        String SUBCATEGORY_NAME = object.getString(JsonField.SUBCATEGORY_NAME);
                        String SUBCATEGORY_IMAGE = object.optString(JsonField.SUBCATEGORY_IMAGE);
                        String PRICE = object.getString(JsonField.PRICE);
                        String ORDER_ID = object.optString(JsonField.ORDER_ID);
                        String USERID = object.getString(JsonField.USERID);
                        String USERNAME = object.optString(JsonField.USERNAME);
                        String USERMOBILE = object.getString(JsonField.USERMOBILE);


                        orders order = new orders();
                        order.setSub_category_id(SUBCATEGORY_ID);
                        order.setSub_category_name(SUBCATEGORY_NAME);
                        order.setSub_category_image(SUBCATEGORY_IMAGE);
                        order.setPrice(PRICE);
                        order.setOrder_id(ORDER_ID);
                        order.setUser_id(USERID);
                        order.setUser_name(USERNAME);
                        order.setUser_mobile(USERMOBILE);
                        listOrder.add(order);
                    }
                    orderAdapter = new OrderAdapter(admin_ordersList_Activity.this, listOrder);
                    orderAdapter.setOrderItemClickListener(admin_ordersList_Activity.this);
                    rvOrderList.setAdapter(orderAdapter);

                }
            }
        }catch (JSONException e){

            e.getStackTrace();
        }
    }


    @Override
    public void setOnItemClicked(ArrayList<orders> listOrder, int position, String id) {

    }
}