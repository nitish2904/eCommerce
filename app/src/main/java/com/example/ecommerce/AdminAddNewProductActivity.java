package com.example.ecommerce;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class AdminAddNewProductActivity extends AppCompatActivity {
    private String categoryName;
    private Button AddNewProductButton;
    private ImageView InputProductImage;
    private EditText InputProductName,InputProductPrice,InputProductDescription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_new_product);
        categoryName = getIntent().getExtras().get("category").toString();
        AddNewProductButton = (Button)findViewById(R.id.add_new_product);
        InputProductDescription = (EditText)findViewById(R.id.product_description);
        InputProductImage = (ImageView)findViewById(R.id.select_product_image);
        InputProductPrice = (EditText)findViewById(R.id.product_price);
        InputProductName = (EditText)findViewById(R.id.product_name);

    }
}
