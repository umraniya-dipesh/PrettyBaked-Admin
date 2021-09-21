package com.example.adminprettybacked.EditActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.android.volley.VolleyError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.example.adminprettybacked.ApiHelper.JsonField;
import com.example.adminprettybacked.ApiHelper.WebUrl;
import com.example.adminprettybacked.R;
import com.example.adminprettybacked.ViewUserListActivity;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EditUserActivity extends AppCompatActivity {
    private  String id;
    private  String name;
    private  String email;
    private  String gender;
    private  String mobile;
    private  String address;

    EditText username,useraddress,usermobile;
    String new_mobile , new_address;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        //final Context context = ();
        //final LayoutInflater inflater = LayoutInflater.from(context);
        //final View view = inflater.inflate(R.layout.activity_edit_user, null, false);

        Button btn_submit_user = findViewById(R.id.btn_submit_user);
        final EditText username = findViewById(R.id.et_username);
        final EditText useremail = findViewById(R.id.et_useremail);
        final EditText usermobile = findViewById(R.id.et_usermobile);
        final EditText usergender = findViewById(R.id.et_usergender);
        final EditText useraddress = findViewById(R.id.et_useraddress);


        Intent intent=getIntent();
        id=intent.getStringExtra(JsonField.USERID);
        name=intent.getStringExtra(JsonField.USERNAME);
        email =intent.getStringExtra(JsonField.USEREMAIl);
        gender =intent.getStringExtra(JsonField.USERGENDER);
        mobile = intent.getStringExtra(JsonField.USERMOBILE);
        address =intent.getStringExtra(JsonField.USERAddress);

        Log.d("id",id);

        username.setText(name);
        //username.setEnabled(false);

        useremail.setText(email);
        //useremail.setEnabled(false);

        usermobile.setText(mobile);
        //usermobile.setEnabled(true);

        usergender.setText(gender);
        //usergender.setEnabled(false);

        useraddress.setText(address);
        //useraddress.setEnabled(true);




        btn_submit_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //if (CheckUserAddress() && CheckUserMobile())
                if (usermobile.getText().toString().trim().length() >= 6 && useraddress.getText().toString().trim().length() >= 3){
                    //pd = new ProgressDialog(EditUserActivity.this);
                    //pd.setMessage("checking and updating");
                    //pd.show();
                    new_mobile = usermobile.getText().toString().trim();
                    new_address = useraddress.getText().toString().trim();
                    sendUserEditRequest(new_mobile,new_address);
                    /*
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, WebUrl.KEY_USER_EDIT_URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(EditUserActivity.this, "OnResponse ", Toast.LENGTH_LONG).show();
                            parseEditUserResponse(response);

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(EditUserActivity.this, "onErrorResponse "+error, Toast.LENGTH_LONG).show();

                        }
                    }){
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params=new HashMap<>();
                            Log.e("Inside method","inside params");
                            params.put(JsonField.USERID,id);
                            params.put(JsonField.USERAddress,usermobile.getText().toString().trim());
                            params.put(JsonField.USERMOBILE,useraddress.getText().toString().trim());
                            return params;
                        }
                    };
                    RequestQueue requestQueue= Volley.newRequestQueue(EditUserActivity.this);
                    requestQueue.add(stringRequest);

                    */

                }
                else{
                    Toast.makeText(EditUserActivity.this, "data wrong inputed ", Toast.LENGTH_LONG).show();

                }
            }
        });
    }



    private void sendUserEditRequest(String new_mobile,String new_address) {
        Log.e("msg","in sendUserEditRequest");


        //pd.dismiss();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, WebUrl.KEY_USER_EDIT_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(EditUserActivity.this, "OnResponse ", Toast.LENGTH_LONG).show();
                parseEditUserResponse(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(EditUserActivity.this, "onErrorResponse "+error, Toast.LENGTH_LONG).show();

            }
        }){
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params=new HashMap<>();
                Log.e("Inside method","inside params");
                params.put(JsonField.USERID,id);
                params.put(JsonField.USERAddress,new_address);
                params.put(JsonField.USERMOBILE,new_mobile);
                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(EditUserActivity.this);
        requestQueue.add(stringRequest);

    }
    private void parseEditUserResponse(String response) {
        Log.e("msg","in parseEditUserResponse");
        Log.d("Response",response);
        try {

            JSONObject jsonObject=new JSONObject(response);
            int flag=jsonObject.optInt(JsonField.FLAG);
            String message=jsonObject.optString(JsonField.MESSAGES);
            if(flag==1)
            {
                Log.e("msg","Successfully updated.");
                Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(EditUserActivity.this, ViewUserListActivity.class);
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
            e.printStackTrace();
        }
    }

    private boolean CheckUserMobile(){
        boolean isUserMobileValid=false;
        if(usermobile.getText().toString().trim().length()<=0)
        {
            usermobile.setError("Enter Mobile");
        }
        else if(usermobile.getText().toString().trim().length()==10) {
            isUserMobileValid=true;
        }
        else{
            usermobile.setError("Enter Correct No.");
        }
        return isUserMobileValid;
    }
    private boolean CheckUserAddress(){
        boolean isUserAddressValid=false;
        String uaddress = useraddress.getText().toString().trim();
        if(uaddress.length() <= 0)
        {
            useraddress.setError("Enter Address");
        }
        else if(uaddress.length()>=3) {
            isUserAddressValid=true;
        }
        else{
            usermobile.setError("Enter Correct address");
        }
        return isUserAddressValid;
    }


}