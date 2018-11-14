package com.example.sameedshah.foodorder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListAdapter;

import com.example.sameedshah.foodorder.Interface.ItemClickListener;
import com.example.sameedshah.foodorder.ViewHolder.FoodViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import com.example.sameedshah.foodorderserver.Model.Food;

public class FoodList extends AppCompatActivity {

    RecyclerView recycler_food;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference foodList;

    FirebaseRecyclerAdapter<Food,FoodViewHolder> adapter;

    //Search functionality
    FirebaseRecyclerAdapter<Food,FoodViewHolder> searchAdapter;
    List<String> suggestionList = new ArrayList<>();
    MaterialSearchBar materialSearchBar;

    String categoryId ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);


        
        database = FirebaseDatabase.getInstance();
        foodList = database.getReference("Foods");

        recycler_food = findViewById(R.id.recycler_food);
        recycler_food.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recycler_food.setLayoutManager(layoutManager);

        if (getIntent() != null)
            categoryId = getIntent().getStringExtra("categoryId");
        if (!categoryId.isEmpty() && categoryId != null) {

            loadFoodList(categoryId);
        }

        //Search

        materialSearchBar = findViewById(R.id.searchBar);
        materialSearchBar.setHint("Search your food");
        loadSuggestions();
        materialSearchBar.setLastSuggestions(suggestionList);
        materialSearchBar.setCardViewElevation(10);
        materialSearchBar.addTextChangeListener(new TextWatcher()  {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                //when user types his suggestion list will be change
                List<String> suggest = new ArrayList<>();
                for(String search: suggestionList){
                    if(search.toLowerCase().contains(materialSearchBar.getText().toLowerCase()))
                        suggest.add(search);
                }
                materialSearchBar.setLastSuggestions(suggest);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        materialSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                //when search bar is closed

                if(!enabled)
                    recycler_food.setAdapter(adapter);

            }

            @Override
            public void onSearchConfirmed(CharSequence text) {

                //when search finish
                //show search result
                 startSearch(text);
            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });

    }

    private void startSearch(CharSequence text) {

        searchAdapter = new FirebaseRecyclerAdapter<Food, FoodViewHolder>(

                Food.class,
                R.layout.foot_item,
                FoodViewHolder.class,
                foodList.orderByChild("Name").equalTo(text.toString())

        ) {
            @Override
            protected void populateViewHolder(FoodViewHolder viewHolder, Food model, int position) {

                viewHolder.txtFoodName.setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage()).into(viewHolder.foodImage);

                final Food local = model;

                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {

                        Intent i = new Intent(FoodList.this,FoodDetail.class);
                        i.putExtra("foodId",searchAdapter.getRef(position).getKey());
                        startActivity(i);

                    }
                });

            }
        };
        recycler_food.setAdapter(searchAdapter);

    }

    private void loadSuggestions() {

        foodList.orderByChild("MenuId").equalTo(categoryId).addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for(DataSnapshot postSnapShot:dataSnapshot.getChildren())
                        {
                            Food item = postSnapShot.getValue(Food.class);
                            suggestionList.add(item.getName());
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                }
        );
    }

    private void loadFoodList(String categoryId) {

        adapter = new FirebaseRecyclerAdapter<Food, FoodViewHolder>(Food.class,
                R.layout.foot_item
                ,FoodViewHolder.class
                ,foodList.orderByChild("MenuId").equalTo(categoryId)) {
            @Override
            protected void populateViewHolder(FoodViewHolder viewHolder, Food model, int position) {

                viewHolder.txtFoodName.setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage()).into(viewHolder.foodImage);

                final Food local = model;

                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {

                        Intent i = new Intent(FoodList.this,FoodDetail.class);
                        i.putExtra("foodId",adapter.getRef(position).getKey());
                        startActivity(i);

                    }
                });
            }
        };

        recycler_food.setAdapter(adapter);

    }
}
