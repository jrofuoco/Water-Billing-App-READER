package com.example.opencvandfirebase;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Meters extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private LinearLayout dynamicContent;
    private static final String SUPABASE_URL = "https://qedemugeyctrrpxkcjpr.supabase.co";
    private static final String API_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InFlZGVtdWdleWN0cnJweGtjanByIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MTg0MDM5NjYsImV4cCI6MjAzMzk3OTk2Nn0.tgr8odentTg1nG_7XmAUZG6RknKXfFRoOnAcGH1Tp34";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_meters);

        // GET FROM INTENT
        Intent intent = getIntent();
        String brgy_name = intent.getStringExtra("brgy_text");
        System.out.println(brgy_name);
        TextView brgy_meter_name = findViewById(R.id.brgy_meter_name);
        brgy_meter_name.setText(brgy_name);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Remove the toolbar title
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        drawerLayout = findViewById(R.id.drawer_layout1);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            v.setPadding(insets.getInsets(WindowInsetsCompat.Type.systemBars()).left,
                    insets.getInsets(WindowInsetsCompat.Type.systemBars()).top,
                    insets.getInsets(WindowInsetsCompat.Type.systemBars()).right,
                    insets.getInsets(WindowInsetsCompat.Type.systemBars()).bottom);
            return insets;
        });

        dynamicContent = findViewById(R.id.dynamic_content);
        fetchData();
    }

    private void fetchData() {
        // Retrieve the brgy_name passed from the previous activity
        Intent intent = getIntent();
        String brgy_name = intent.getStringExtra("brgy_text");

        OkHttpClient client = new OkHttpClient();

        // Encode brgy_name to handle spaces or special characters in the URL
        String encodedBrgyName = brgy_name != null ? brgy_name.replace(" ", "%20") : "";

        // Update the URL to filter accounts by the specified brgy_name
        String url = SUPABASE_URL + "/rest/v1/connection_tb?select=id,meter_no,first_name,last_name,middle_name,account_no&brgy=eq." + encodedBrgyName;
        Request request = new Request.Builder()
                .url(url)
                .addHeader("apikey", API_KEY)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> {
                    Toast.makeText(Meters.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
                    Log.e("Meters", "Error fetching data", e);
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    runOnUiThread(() -> {
                        Toast.makeText(Meters.this, "Error fetching data", Toast.LENGTH_SHORT).show();
                        Log.e("Meters", "Unexpected code " + response);
                    });
                    return;
                }

                String responseData = response.body().string();
                runOnUiThread(() -> {
                    try {
                        JSONArray jsonArray = new JSONArray(responseData);
                        dynamicContent.removeAllViews(); // Clear the layout
                        for (int i = 0; i < jsonArray.length(); i += 2) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            String id1 = jsonObject1.getString("id");
                            String meterNo1 = jsonObject1.getString("meter_no");
                            String firstName1 = jsonObject1.getString("first_name");
                            String lastName1 = jsonObject1.getString("last_name");
                            String middleName1 = jsonObject1.getString("middle_name");
                            String accountNo1 = jsonObject1.getString("account_no");

                            String fullName1 = firstName1 + " " + middleName1 + " " + lastName1;

                            JSONObject jsonObject2 = null;
                            if (i + 1 < jsonArray.length()) {
                                jsonObject2 = jsonArray.getJSONObject(i + 1);
                                String id2 = jsonObject2.getString("id");
                                String meterNo2 = jsonObject2.getString("meter_no");
                                String firstName2 = jsonObject2.getString("first_name");
                                String lastName2 = jsonObject2.getString("last_name");
                                String middleName2 = jsonObject2.getString("middle_name");
                                String accountNo2 = jsonObject2.getString("account_no");

                                String fullName2 = firstName2 + " " + middleName2 + " " + lastName2;

                                LinearLayout rowLayout = new LinearLayout(Meters.this);
                                rowLayout.setOrientation(LinearLayout.HORIZONTAL);

                                LinearLayout columnLayout1 = new LinearLayout(Meters.this);
                                columnLayout1.setOrientation(LinearLayout.VERTICAL);
                                columnLayout1.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));

                                LinearLayout columnLayout2 = new LinearLayout(Meters.this);
                                columnLayout2.setOrientation(LinearLayout.VERTICAL);
                                columnLayout2.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));

                                ImageView meter1 = new ImageView(Meters.this);
                                meter1.setImageResource(R.drawable.user);
                                columnLayout1.addView(meter1);

                                TextView meter1Text = new TextView(Meters.this);
                                meter1Text.setText("Meter No: " + meterNo1 + "\n" + "Name: " + fullName1 + "\n" + "Account No: " + accountNo1 + "\n" + "ID: " + id1);
                                meter1Text.setTextSize(16);
                                meter1Text.setPadding(16, 16, 16, 16);
                                columnLayout1.addView(meter1Text);

                                Button button1 = new Button(Meters.this);
                                button1.setText("Button " + meterNo1);
                                button1.setPadding(16, 16, 16, 16);
                                button1.setOnClickListener(v -> {
                                    Intent intent = new Intent(Meters.this, Reading.class);
                                    intent.putExtra("name", fullName1);
                                    intent.putExtra("meterno", meterNo1);
                                    intent.putExtra("accountno", accountNo1);
                                    intent.putExtra("id", id1);
                                    Toast.makeText(Meters.this, accountNo1, Toast.LENGTH_SHORT).show();
                                    startActivity(intent);
                                });
                                columnLayout1.addView(button1);

                                ImageView meter2 = new ImageView(Meters.this);
                                meter2.setImageResource(R.drawable.user);
                                columnLayout2.addView(meter2);

                                TextView meter2Text = new TextView(Meters.this);
                                meter2Text.setText("Meter No: " + meterNo2 + "\n" + "Name: " + fullName2 + "\n" + "Account No: " + accountNo2 + "\n" + "ID: " + id2);
                                meter2Text.setTextSize(16);
                                meter2Text.setPadding(16, 16, 16, 16);
                                columnLayout2.addView(meter2Text);

                                Button button2 = new Button(Meters.this);
                                button2.setText("Button " + meterNo2);
                                button2.setPadding(16, 16, 16, 16);
                                button2.setOnClickListener(v -> {
                                    Intent intent = new Intent(Meters.this, Reading.class);
                                    intent.putExtra("name", fullName2);
                                    intent.putExtra("meterno", meterNo2);
                                    intent.putExtra("accountno", accountNo2);
                                    intent.putExtra("id", id2);
                                    Toast.makeText(Meters.this, accountNo2, Toast.LENGTH_SHORT).show();
                                    startActivity(intent);
                                });
                                columnLayout2.addView(button2);

                                rowLayout.addView(columnLayout1);
                                rowLayout.addView(columnLayout2);

                                dynamicContent.addView(rowLayout);
                            } else {
                                LinearLayout rowLayout = new LinearLayout(Meters.this);
                                rowLayout.setOrientation(LinearLayout.HORIZONTAL);

                                LinearLayout columnLayout1 = new LinearLayout(Meters.this);
                                columnLayout1.setOrientation(LinearLayout.VERTICAL);
                                columnLayout1.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));

                                ImageView meter1 = new ImageView(Meters.this);
                                meter1.setImageResource(R.drawable.user);
                                columnLayout1.addView(meter1);

                                TextView meter1Text = new TextView(Meters.this);
                                meter1Text.setText("Meter No: " + meterNo1 + ", Name: " + fullName1 + ", Account No: " + accountNo1 + ", ID: " + id1);
                                meter1Text.setTextSize(16);
                                meter1Text.setPadding(16, 16, 16, 16);
                                columnLayout1.addView(meter1Text);

                                Button button1 = new Button(Meters.this);
                                button1.setText("Button " + meterNo1);
                                button1.setPadding(16, 16, 16, 16);
                                button1.setOnClickListener(v -> {
                                    Intent intent = new Intent(Meters.this, Reading.class);
                                    intent.putExtra("name", fullName1);
                                    intent.putExtra("meterno", meterNo1);
                                    intent.putExtra("accountno", accountNo1);
                                    intent.putExtra("id", id1);
                                    Toast.makeText(Meters.this, accountNo1, Toast.LENGTH_SHORT).show();
                                    startActivity(intent);
                                });
                                columnLayout1.addView(button1);

                                rowLayout.addView(columnLayout1);
                                dynamicContent.addView(rowLayout);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(Meters.this, "Error parsing data", Toast.LENGTH_SHORT).show();
                    }
                });
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
            showToast("Bagong Anyo");
            return true;
        } else if (id == R.id.bayate) {
            showToast("Bayate");
            return true;
        } else if (id == R.id.bongkol) {
            showToast("Bongkol");
            return true;
        } else if (id == R.id.bubukal) {
            showToast("Bubukal");
            return true;
        } else if (id == R.id.cabuyew) {
            showToast("Cabuyew");
            return true;
        } else if (id == R.id.calumpang) {
            showToast("Calumpang");
            return true;
        } else if (id == R.id.san_isidro1) {
            showToast("San Isidro");
            return true;
        } else if (id == R.id.culoy) {
            showToast("Culoy");
            return true;
        } else if (id == R.id.dagatan) {
            showToast("Dagatan");
            return true;
        } else if (id == R.id.daniw) {
            showToast("Daniw");
            return true;
        } else if (id == R.id.dita) {
            showToast("Dita");
            return true;
        } else if (id == R.id.ibabang_palina) {
            showToast("Ibabang Palina");
            return true;
        } else if (id == R.id.ibabang_san_roque) {
            showToast("Ibabang San Roque");
            return true;
        } else if (id == R.id.ibabang_sungi) {
            showToast("Ibabang Sungi");
            return true;
        } else if (id == R.id.ibabang_taykin) {
            showToast("Ibabang Taykin");
            return true;
        } else if (id == R.id.ilayang_palina) {
            showToast("Ilayang Palina");
            return true;
        } else if (id == R.id.ilayang_san_roque) {
            showToast("Ilayang San Roque");
            return true;
        } else if (id == R.id.ilayang_sungi) {
            showToast("Ilayang Sungi");
            return true;
        } else if (id == R.id.ilayang_taykin) {
            showToast("Ilayang Taykin");
            return true;
        } else if (id == R.id.kanlurang_bukal) {
            showToast("Kanlurang Bukal");
            return true;
        } else if (id == R.id.laguan) {
            showToast("Laguan");
            return true;
        } else if (id == R.id.luquin) {
            showToast("Luquin");
            return true;
        } else if (id == R.id.malabo_kalantukan) {
            showToast("Malabo-Kalantukan");
            return true;
        } else if (id == R.id.masikap) {
            showToast("Masikap");
            return true;
        } else if (id == R.id.maslun) {
            showToast("Maslun");
            return true;
        } else if (id == R.id.mojon) {
            showToast("Mojon");
            return true;
        } else if (id == R.id.novaliches) {
            showToast("Novaliches");
            return true;
        } else if (id == R.id.oples) {
            showToast("Oples");
            return true;
        } else if (id == R.id.pag_asa) {
            showToast("Pag-asa");
            return true;
        } else if (id == R.id.palayan) {
            showToast("Palayan");
            return true;
        } else if (id == R.id.rizal) {
            showToast("Rizal");
            return true;
        } else if (id == R.id.san_isidro1) {
            showToast("San Isidro");
            return true;
        } else if (id == R.id.silangang_bukal) {
            showToast("Silangang Bukal");
            return true;
        } else if (id == R.id.tuy_baanan) {
            showToast("Tuy-Baanan");
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}