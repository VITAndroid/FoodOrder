package com.example.dessertorder;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Payment extends Activity {

    ListView lvSummary;
    TextView tvTotal;
    Double Total=0d;
    ArrayList<Product> productOrders = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment1);
        lvSummary = findViewById(R.id.lvSummary);
        tvTotal = findViewById(R.id.tvTotal);
        getOrderItemData();
    }

    private void getOrderItemData() {
        Bundle extras = getIntent().getExtras();
        if(extras !=null )
        {
            String orderItems = extras.getString("orderItems",null);
            if(orderItems!=null && orderItems.length()>0)
            {
                try{
                    JSONArray jsonOrderItems = new JSONArray(orderItems);
                    for(int i=0;i<jsonOrderItems.length();i++)
                    {
                        JSONObject jsonObject = new JSONObject(jsonOrderItems.getString(i));
                        Product product = new Product(
                                jsonObject.getString("ProductName")
                                ,jsonObject.getDouble("ProductPrice")
                                ,jsonObject.getInt("ProductImage")
                        );
                        product.CartQuantity = jsonObject.getInt("CartQuantity");
                        /* Calculate Total */
                        Total = Total + (product.CartQuantity * product.ProductPrice);
                        productOrders.add(product);
                    }

                    if(productOrders.size() > 0)
                    {
                        ListAdapter listAdapter = new ListAdapter(this,productOrders);
                        lvSummary.setAdapter(listAdapter);
                        tvTotal.setText("Order Total: "+Total);
                    }
                    else
                    {
                        showMessage("Empty");
                    }
                }
                catch (Exception e)
                {
                    showMessage(e.toString());
                }
            }

        }
    }


    public void showMessage(String message)
    {
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }

    public void Home(View view) {
    Intent it = new Intent(Payment.this,MainActivity.class);
    it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    startActivity(it);
    }






}
