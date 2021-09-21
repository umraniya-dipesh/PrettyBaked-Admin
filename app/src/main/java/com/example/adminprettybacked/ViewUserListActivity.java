package com.example.adminprettybacked;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.adminprettybacked.Adapter.UsersAdapter;
import com.example.adminprettybacked.ApiHelper.JsonField;
import com.example.adminprettybacked.ApiHelper.WebUrl;
import com.example.adminprettybacked.EditActivity.EditUserActivity;
import com.example.adminprettybacked.Listener.UsersItemClickListener;
import com.example.adminprettybacked.model.Users;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ViewUserListActivity extends AppCompatActivity implements UsersItemClickListener{


    private RecyclerView recyclerView;
    protected ArrayList<Users> listUser;
    public UsersAdapter usersAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user_list);

        getUsers();




        recyclerView = findViewById(R.id.rvUserList);
        //recyclerView.setHasFixedSize(true);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //listUser = new ArrayList<Users>();
        //usersAdapter = new UsersAdapter(this, listUser);
        //recyclerView.setAdapter(usersAdapter);



        //getUsers();
    }

    private void getUsers() {
        listUser = new ArrayList<>();
        StringRequest stringRequest=new StringRequest(Request.Method.POST,WebUrl.KEY_USERS_VIEW_URL, new Response.Listener<String>() {
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
        RequestQueue requestQueue= Volley.newRequestQueue(ViewUserListActivity.this);
        requestQueue.add(stringRequest);

    }

    private void addUsers(){

    }
    private Boolean varify_for_addUsers(){return true;}

    private void parseJson(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            int flag = jsonObject.optInt(JsonField.FLAG);
            if (flag == 1) {
                Log.i("msg", "in parseJson , flag == 1");
                JSONArray jsonArray = jsonObject.getJSONArray(JsonField.USER_ARRAY);
                if (jsonArray.length() > 0) {
                    Log.i("msg", "in parseJson ,length()>0 ");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.optJSONObject(i);
                        String userId = object.getString(JsonField.USERID);
                        String userName = object.optString(JsonField.USERNAME);
                        String userEmail = object.getString(JsonField.USEREMAIl);
                        String userGender = object.optString(JsonField.USERGENDER);
                        String userMobile = object.getString(JsonField.USERMOBILE);
                        String userAddress = object.optString(JsonField.USERAddress);

                        Users users = new Users();
                        users.setUser_id(userId);
                        users.setUser_name(userName);
                        users.setUser_email(userEmail);
                        users.setUser_gender(userGender);
                        users.setUser_mobile(userMobile);
                        users.setUser_address(userAddress);
                        listUser.add(users);
                    }
                    UsersAdapter usersAdapter = new UsersAdapter(ViewUserListActivity.this, listUser);
                    usersAdapter.setUsersItemClickListener(ViewUserListActivity.this);
                    recyclerView.setAdapter(usersAdapter);


                }
            }
        }catch (JSONException e){

            e.getStackTrace();
        }

    }
    private void parseJson1(String response) {
        try {
            JSONObject jsonObject=new JSONObject(response);
            int flag=jsonObject.optInt(JsonField.FLAG);
            if(flag==1)
            {
                Toast.makeText(this,"flag 1 , data deleted",Toast.LENGTH_LONG).show();
            }
        }catch (JSONException e){
            Toast.makeText(this,"flag 0:" + e ,Toast.LENGTH_LONG).show();
            e.getStackTrace();
        }

    }

    @Override
    public void setOnItemClicked(ArrayList<Users> listUser, int position, String id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        CharSequence[] dailogitem  = {"edit data" ,"delete data"};
        builder.setTitle(listUser.get(position).getUser_name());
        builder.setItems(dailogitem,(dialog,i)->{
                    switch(i){
                        case 0: {
                            Intent intent = new Intent(ViewUserListActivity.this, EditUserActivity.class);
                            Users users = listUser.get(position);

                            String user_id = users.getUser_id();
                            String user_name = users.getUser_name();
                            String user_email = users.getUser_email();
                            String user_gender = users.getUser_gender();
                            String user_mobile = users.getUser_mobile();
                            String user_address = users.getUser_address();

                            intent.putExtra("position", position);
                            intent.putExtra(JsonField.USERID, user_id);
                            intent.putExtra(JsonField.USERNAME, user_name);
                            intent.putExtra(JsonField.USEREMAIl, user_email);
                            intent.putExtra(JsonField.USERGENDER, user_gender);
                            intent.putExtra(JsonField.USERMOBILE, user_mobile);
                            intent.putExtra(JsonField.USERAddress, user_address);

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
                                    Users users = listUser.get(position);
                                    String user_id = users.getUser_id();
                                    deleteUser(user_id);
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

    private void deleteUser(String user_id) {

        StringRequest stringRequest=new StringRequest(Request.Method.POST, WebUrl.KEY_USER_DELETE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.equalsIgnoreCase("data deleted")){
                    Toast.makeText(ViewUserListActivity.this,"data deleted successfully.",Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(ViewUserListActivity.this,"data deletion Failed.",Toast.LENGTH_LONG).show();
                }
                //parseJson1(response.toString());
                Log.e("msg",response.toString());
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ViewUserListActivity.this,"---error: "+error , Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        }){
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params=new HashMap<>();
                Log.e("Inside method","inside params"+user_id);
                params.put(JsonField.USERID,user_id);
                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(ViewUserListActivity.this);
        requestQueue.add(stringRequest);
    }

    /*
    public void setOnItemClicked(ArrayList<Users> listUser, int position) {
        Intent intent=new Intent(ViewUserListActivity.this,EditUserActivity.class);
        Users users=listUser.get(position);

        String user_id=users.getUser_id();
        String user_name=users.getUser_name();
        String user_email=users.getUser_email();
        String user_gender=users.getUser_gender();
        String user_mobile=users.getUser_mobile();
        String user_address=users.getUser_address();

        intent.putExtra(JsonField.USERID,user_id);
        intent.putExtra(JsonField.USERNAME,user_name);
        intent.putExtra(JsonField.USEREMAIl,user_email);
        intent.putExtra(JsonField.USERGENDER,user_gender);
        intent.putExtra(JsonField.USERMOBILE,user_mobile);
        intent.putExtra(JsonField.USERAddress,user_address);

        startActivity(intent);
    }*/


}