package com.example.opencvandfirebase;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class Dashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dashboard);

        //MENUUUUUUUUUUUUU
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Remove the toolbar title
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button start_reading_Btn = findViewById(R.id.startBtn);
        start_reading_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the menu drawer
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        ImageView imageView10 = findViewById(R.id.imageView10);
        imageView10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Action to perform on click
                System.out.println("ImageView clicked!");
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.bagong_anyo) {
            navigateToMeter("Bagong Anyo");
            return true;
        } else if (id == R.id.bayate) {
            navigateToMeter("Bayate");
            return true;
        } else if (id == R.id.bongkol) {
            navigateToMeter("Bongkol");
            return true;
        } else if (id == R.id.bubukal) {
            navigateToMeter("Bubukal");
            return true;
        } else if (id == R.id.cabuyew) {
            navigateToMeter("Cabuyew");
            return true;
        } else if (id == R.id.calumpang) {
            navigateToMeter("Calumpang");
            return true;
        } else if (id == R.id.san_isidro1) {
            navigateToMeter("San Isidro");
            return true;
        } else if (id == R.id.culoy) {
            navigateToMeter("Culoy");
            return true;
        } else if (id == R.id.dagatan) {
            navigateToMeter("Dagatan");
            return true;
        } else if (id == R.id.daniw) {
            navigateToMeter("Daniw");
            return true;
        } else if (id == R.id.dita) {
            navigateToMeter("Dita");
            return true;
        } else if (id == R.id.ibabang_palina) {
            navigateToMeter("Ibabang Palina");
            return true;
        } else if (id == R.id.ibabang_san_roque) {
            navigateToMeter("Ibabang San Roque");
            return true;
        } else if (id == R.id.ibabang_sungi) {
            navigateToMeter("Ibabang Sungi");
            return true;
        } else if (id == R.id.ibabang_taykin) {
            navigateToMeter("Ibabang Taykin");
            return true;
        } else if (id == R.id.ilayang_palina) {
            navigateToMeter("Ilayang Palina");
            return true;
        } else if (id == R.id.ilayang_san_roque) {
            navigateToMeter("Ilayang San Roque");
            return true;
        } else if (id == R.id.ilayang_sungi) {
            navigateToMeter("Ilayang Sungi");
            return true;
        } else if (id == R.id.ilayang_taykin) {
            navigateToMeter("Ilayang Taykin");
            return true;
        } else if (id == R.id.kanlurang_bukal) {
            navigateToMeter("Kanlurang Bukal");
            return true;
        } else if (id == R.id.laguan) {
            navigateToMeter("Laguan");
            return true;
        } else if (id == R.id.luquin) {
            navigateToMeter("Luquin");
            return true;
        } else if (id == R.id.malabo_kalantukan) {
            navigateToMeter("Malabo-Kalantukan");
            return true;
        } else if (id == R.id.masikap) {
            navigateToMeter("Masikap");
            return true;
        } else if (id == R.id.maslun) {
            navigateToMeter("Maslun");
            return true;
        } else if (id == R.id.mojon) {
            navigateToMeter("Mojon");
            return true;
        } else if (id == R.id.novaliches) {
            navigateToMeter("Novaliches");
            return true;
        } else if (id == R.id.oples) {
            navigateToMeter("Oples");
            return true;
        } else if (id == R.id.pag_asa) {
            navigateToMeter("Pag-asa");
            return true;
        } else if (id == R.id.palayan) {
            navigateToMeter("Palayan");
            return true;
        } else if (id == R.id.rizal) {
            navigateToMeter("Rizal");
            return true;
        } else if (id == R.id.san_isidro2) {
            navigateToMeter("San Isidro");
            return true;
        } else if (id == R.id.silangang_bukal) {
            navigateToMeter("Silangang Bukal");
            return true;
        } else if (id == R.id.tuy_baanan) {
            navigateToMeter("Tuy-Baanan");
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }

    }

    private void navigateToMeter(String toast_brgy_name) {
        Toast.makeText(this, toast_brgy_name, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Dashboard.this, Meters.class);
        intent.putExtra("brgy_text", toast_brgy_name);
        startActivity(intent);
    }
}
