package com.example.adminprettybacked;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_user = findViewById(R.id.admin_user);
        Button btn_category = findViewById(R.id.admin_category);
        Button btn_subcategory = findViewById(R.id.admin_subcategory);
        Button btn_orders = findViewById(R.id.admin_orders);

        btn_user.setOnClickListener(this);
        btn_category.setOnClickListener(this);
        btn_subcategory.setOnClickListener(this);
        btn_orders.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view != null) {
            switch (view.getId()) {

                case R.id.admin_user: {
                    Intent intent = new Intent(MainActivity.this, ViewUserListActivity.class);
                    startActivity(intent);
                    break;
                }

                case R.id.admin_category: {
                    Intent intent = new Intent(MainActivity.this,adminCategoryListActivity.class);
                    startActivity(intent);
                    break;
                }
                case R.id.admin_subcategory: {
                    Intent intent = new Intent(MainActivity.this, admin_subcategoryList_Activity.class);
                    startActivity(intent);
                    break;
                }
                case R.id.admin_orders: {
                    Intent intent = new Intent(MainActivity.this, admin_ordersList_Activity.class);
                    startActivity(intent);
                    break;
                }


            }
        }

    }
}