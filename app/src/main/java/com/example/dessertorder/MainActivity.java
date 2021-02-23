package com.example.dessertorder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;

import java.util.ArrayList;

public class MainActivity extends Activity {

    ListView listView;
    ListAdapter listAdapter;
    ArrayList<Product> products = new ArrayList<>();
    Button btnPlaceOrder;
    ArrayList<Product> productOrders = new ArrayList<>();
    ArrayList<String> lOrderItems = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getProduct();
        listView = (ListView) findViewById(R.id.customListView);
        listAdapter = new ListAdapter(this,products);
        listView.setAdapter(listAdapter);
        btnPlaceOrder = (Button) findViewById(R.id.btnPlaceOrder);
        btnPlaceOrder.setOnClickListener(v -> placeOrder());
    }

    public void placeOrder()
    {
        productOrders.clear();
        lOrderItems.clear();
        for(int i=0;i<listAdapter.listProducts.size();i++)
        {
            if(listAdapter.listProducts.get(i).CartQuantity > 0)
            {
                Product products = new Product(
                        listAdapter.listProducts.get(i).ProductName
                        ,listAdapter.listProducts.get(i).ProductPrice
                        ,listAdapter.listProducts.get(i).ProductImage
                );
                products.CartQuantity = listAdapter.listProducts.get(i).CartQuantity;
                productOrders.add(products);
                lOrderItems.add(products.getJsonObject());
            }
        }
        JSONArray jsonArray =new JSONArray(lOrderItems);
        openSummary(jsonArray.toString());
    }
    public void openSummary(String orderItems)
    {
        Intent summaryIntent = new Intent(this,Payment.class);
        summaryIntent.putExtra("orderItems",orderItems);
        startActivity(summaryIntent);
    }



    public void getProduct() {
        products.add(new Product("Cookie",10.0d,R.drawable.cookie));
        products.add(new Product("Doughnut",11.0d,R.drawable.doughnut));
        products.add(new Product("Pastry",20.0d,R.drawable.pastry));
        products.add(new Product("Cake Piece",13.0d,R.drawable.piece_of_cake));
    }
}