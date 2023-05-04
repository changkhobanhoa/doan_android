package trancongtien.com.doan.houses;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.*;

import java.util.ArrayList;
import java.util.List;

import trancongtien.com.doan.MainActivity;
import trancongtien.com.doan.R;
import trancongtien.com.doan.houses.addHouse.AddHouse;
import trancongtien.com.doan.model.Houses;

public class HousesSystem extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

    ImageView imgBack, imgAddHouses;
   SearchView searchView_houses;

    List<Houses> housesList = new ArrayList<>();
    RecyclerView rcv_houses;
    AdapterHouses adapterHouses;

    ShimmerFrameLayout shimmer_view_container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_houses_system);

        initUI();
        init();
        setUpRCV();
searchView_houses=findViewById(R.id.searchView_houses);
searchView_houses.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
    @Override
    public boolean onQueryTextSubmit(String query) {
        adapterHouses.getFilter().filter(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adapterHouses.getFilter().filter(newText);
        return false;
    }
});
    }

    private void setUpRCV() {
        adapterHouses = new AdapterHouses(HousesSystem.this, housesList);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL);

        rcv_houses.setLayoutManager(staggeredGridLayoutManager);
        rcv_houses.setAdapter(adapterHouses);

        displayHouses();
    }

    private void init() {
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HousesSystem.this, MainActivity.class));

            }
        });

        imgAddHouses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HousesSystem.this, AddHouse.class));

            }
        });
    }


    private void displayHouses() {
        housesList.clear();

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Houses houses = dataSnapshot.getValue(Houses.class); // Snap Key here is City
                    housesList.add(houses);
                }
                adapterHouses.notifyDataSetChanged();

                // When get data successfully, hide the shimmer and show all function field
                searchView_houses.setVisibility(View.VISIBLE);
                rcv_houses.setVisibility(View.VISIBLE);
                imgAddHouses.setVisibility(View.VISIBLE);
                shimmer_view_container.setVisibility(View.GONE);
                shimmer_view_container.stopShimmerAnimation();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        };
        Query query = myRef.child("houses").child(firebaseUser.getUid());
        query.addListenerForSingleValueEvent(valueEventListener);

    }


    private void initUI() {
        imgBack = findViewById(R.id.imgBack);
        imgAddHouses = findViewById(R.id.imgAddHouses);

        searchView_houses = findViewById(R.id.searchView_houses);

        rcv_houses = findViewById(R.id.rcv_houses);

        shimmer_view_container = findViewById(R.id.shimmer_view_container);

    }


    @Override
    protected void onStart() {
        // Hide all function field and show shimmer effect
        searchView_houses.setVisibility(View.GONE);
        rcv_houses.setVisibility(View.GONE);
        imgAddHouses.setVisibility(View.GONE);
        shimmer_view_container.setVisibility(View.VISIBLE);
        shimmer_view_container.startShimmerAnimation();

        super.onStart();

    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(HousesSystem.this, MainActivity.class));
        HousesSystem.this.finish();
        super.onBackPressed();
    }
}