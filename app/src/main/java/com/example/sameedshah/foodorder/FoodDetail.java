package com.example.sameedshah.foodorder;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import Database.Database;
import com.example.sameedshah.foodorderserver.Model.Food;
import com.example.sameedshah.foodorderserver.Model.Order;

public class FoodDetail extends AppCompatActivity {

    TextView food_name,food_price,food_description,txtPriceCount;
    ImageView food_image;
    CollapsingToolbarLayout toolbarLayout;
    FloatingActionButton cartBtn;



    String foodId;
    int totalCount = 0;

    FirebaseDatabase database;
    DatabaseReference foods;

    Database db;

    Food currentFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        database = FirebaseDatabase.getInstance();
        foods = database.getReference("Foods");

        //number_counter = findViewById(R.id.number_counter);
        cartBtn = findViewById(R.id.btn_cart);

        food_name = findViewById(R.id.food_description);
        food_description = findViewById(R.id.food_name);
        food_price = findViewById(R.id.food_price);
        food_image = findViewById(R.id.img_food);

        txtPriceCount = findViewById(R.id.txtPriceCount);
        totalCount = Integer.parseInt(txtPriceCount.getText().toString());


        toolbarLayout = findViewById(R.id.collapsing);
        toolbarLayout.setExpandedTitleTextAppearance(R.style.ExpendedAppbar);
        toolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppbar);

        db = new Database(this);


        //cart button
        cartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               db.addToCart(new Order(
                        foodId,
                        currentFood.getName(),
                        txtPriceCount.getText().toString(),
                        currentFood.getPrice(),
                        currentFood.getDiscount()


                ));

                Toast.makeText(FoodDetail.this, "Added to cart", Toast.LENGTH_SHORT).show();
            }
        });




        if(getIntent()!= null)

            foodId = getIntent().getStringExtra("foodId");

        if(!foodId.isEmpty()){

            getFoodDetail(foodId);
        }

    }

    private void getFoodDetail(String foodId) {

        foods.child(foodId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                currentFood = dataSnapshot.getValue(Food.class);


                // for image
                Picasso.with(getBaseContext()).load(currentFood.getImage()).into(food_image);
                toolbarLayout.setTitle(currentFood.getName());

                food_price.setText(currentFood.getPrice());
                food_description.setText(currentFood.getDescription());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void onAdd(View view){

        if(totalCount >=0 && totalCount <=20){
            totalCount ++;
            txtPriceCount.setText(""+totalCount);
        }
    }

    public void onSub(View view){

        if(totalCount >0){
            totalCount --;
            txtPriceCount.setText(""+totalCount);
        }
    }


}
