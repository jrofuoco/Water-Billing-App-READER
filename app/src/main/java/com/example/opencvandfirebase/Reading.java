package com.example.opencvandfirebase;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.IOException;

import okhttp3.Response;

public class Reading extends AppCompatActivity {

    private EditText editText1, editText2, editText3, editText4,
            editText5, editText6, editText7, editText8;

    private EditText lastMonth1, lastMonth2, lastMonth3, lastMonth4,
            lastMonth5, lastMonth6, lastMonth7, lastMonth8;

    private String recordText, lastMonth;  // Storing the concatenated recordText and lastMonth

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_reading);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String meterno = intent.getStringExtra("meterno");
        String accountno = intent.getStringExtra("accountno");
        String id = intent.getStringExtra("id");

        TextView client_meter_number = findViewById(R.id.client_meter_number);
        client_meter_number.setText("Meter No: " + meterno);

        TextView account_no = findViewById(R.id.account_no);
        account_no.setText(accountno);

        TextView client_name = findViewById(R.id.name_client);
        client_name.setText(name);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editText1 = findViewById(R.id.editText1);
        editText2 = findViewById(R.id.editText2);
        editText3 = findViewById(R.id.editText3);
        editText4 = findViewById(R.id.editText4);
        editText5 = findViewById(R.id.editText5);
        editText6 = findViewById(R.id.editText6);
        editText7 = findViewById(R.id.editText7);
        editText8 = findViewById(R.id.editText8);

        lastMonth1 = findViewById(R.id.lastmonth_1);
        lastMonth2 = findViewById(R.id.lastmonth_2);
        lastMonth3 = findViewById(R.id.lastmonth_3);
        lastMonth4 = findViewById(R.id.lastmonth_4);
        lastMonth5 = findViewById(R.id.lastmonth_5);
        lastMonth6 = findViewById(R.id.lastmonth_6);
        lastMonth7 = findViewById(R.id.lastmonth_7);
        lastMonth8 = findViewById(R.id.lastmonth_8);

        setupTextWatchers();

        ImageButton back_btn = findViewById(R.id.backBtn2);
        back_btn.setOnClickListener(v -> finish());

        Button snap_btn = findViewById(R.id.snap_btn);
        Button record_btn = findViewById(R.id.record_btn);

        fetchAndDisplayExistingRecord(id);  // Fetching last month's record

        record_btn.setOnClickListener(v -> {
            // Concatenate recordText from input fields
            recordText = String.valueOf(editText1.getText()) +
                    String.valueOf(editText2.getText()) +
                    String.valueOf(editText3.getText()) +
                    String.valueOf(editText4.getText()) +
                    String.valueOf(editText5.getText()) +
                    String.valueOf(editText6.getText()) +
                    String.valueOf(editText7.getText()) +
                    String.valueOf(editText8.getText());

            Toast.makeText(Reading.this, recordText, Toast.LENGTH_SHORT).show();

            ImageView imageView18 = findViewById(R.id.imageView18);
            LinearLayout linearLayout2 = findViewById(R.id.linearLayout2);
            TextView verify_txt = findViewById(R.id.verify_txt);
            Button confirm_btn = findViewById(R.id.ok_btn);

            // Pop up confirmation
            snap_btn.setClickable(false);
            record_btn.setClickable(false);
            imageView18.setVisibility(View.VISIBLE);
            verify_txt.setVisibility(View.VISIBLE);
            confirm_btn.setVisibility(View.VISIBLE);
            linearLayout2.bringToFront();

            // CONFIRM LISTENER
            confirm_btn.setOnClickListener(v1 -> {
                ImageView imageView22 = findViewById(R.id.imageView22);
                Button ok_btn = findViewById(R.id.ok_reading_btn);

                imageView22.setVisibility(View.VISIBLE);
                ok_btn.setVisibility(View.VISIBLE);
                linearLayout2.setVisibility(View.GONE);

                ok_btn.setOnClickListener(v2 -> {
                    // Insert recordText into the database
                    updateRecordText(recordText, id);
                    System.out.println(recordText);
                    finish();
                });

                // Calculate charges based on recordText and lastMonth
                ResidentialChargesHelper.fetchLatestResidentialCharges(recordText, lastMonth, id, total -> {
                    System.out.println("Total Residential Charges: " + total);
                    // Optionally display the total charges
                });
            });
        });
    }

    private void setupTextWatchers() {
        editText1.addTextChangedListener(new GenericTextWatcher(editText1, editText2));
        editText2.addTextChangedListener(new GenericTextWatcher(editText2, editText3));
        editText3.addTextChangedListener(new GenericTextWatcher(editText3, editText4));
        editText4.addTextChangedListener(new GenericTextWatcher(editText4, editText5));
        editText5.addTextChangedListener(new GenericTextWatcher(editText5, editText6));
        editText6.addTextChangedListener(new GenericTextWatcher(editText6, editText7));
        editText7.addTextChangedListener(new GenericTextWatcher(editText7, editText8));
        // No need for a TextWatcher on editText8
    }

    private class GenericTextWatcher implements TextWatcher {
        private EditText currentEditText;
        private EditText nextEditText;

        public GenericTextWatcher(EditText currentEditText, EditText nextEditText) {
            this.currentEditText = currentEditText;
            this.nextEditText = nextEditText;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}

        @Override
        public void afterTextChanged(Editable s) {
            if (s.length() == 1) {
                nextEditText.requestFocus();
            }
        }
    }

    private void updateRecordText(String recordText, String connectionId) {
        new Thread(() -> {
            String checkEndpoint = "/rest/v1/monthly_bill?select=id&connection_id=eq." + connectionId;
            try {
                Response checkResponse = DatabaseConnector.performGetRequest(checkEndpoint);
                if (!checkResponse.isSuccessful()) {
                    runOnUiThread(() -> Toast.makeText(Reading.this, "Error checking record", Toast.LENGTH_SHORT).show());
                    return;
                }

                String responseData = checkResponse.body().string();
                JSONArray jsonArray = new JSONArray(responseData);

                if (jsonArray.length() > 0) {
                    String existingId = jsonArray.getJSONObject(0).getString("id");
                    String updateJson = "{\"bill\": \"" + recordText + "\"}";
                    String updateEndpoint = "/rest/v1/monthly_bill?id=eq." + existingId;
                    Response updateResponse = DatabaseConnector.performPatchRequest(updateEndpoint, updateJson);

                    if (updateResponse.isSuccessful()) {
                        runOnUiThread(() -> Toast.makeText(Reading.this, "Record updated successfully", Toast.LENGTH_SHORT).show());
                    } else {
                        runOnUiThread(() -> Toast.makeText(Reading.this, "Error updating record", Toast.LENGTH_SHORT).show());
                    }
                } else {
                    runOnUiThread(() -> Toast.makeText(Reading.this, "Record not found for the provided connection ID", Toast.LENGTH_SHORT).show());
                }
            } catch (IOException | JSONException e) {
                runOnUiThread(() -> Toast.makeText(Reading.this, "Failed to update record", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }

    private void fetchAndDisplayExistingRecord(String connectionId) {
        new Thread(() -> {
            String fetchEndpoint = "/rest/v1/monthly_bill?select=bill&connection_id=eq." + connectionId;
            try {
                Response fetchResponse = DatabaseConnector.performGetRequest(fetchEndpoint);
                if (!fetchResponse.isSuccessful()) {
                    System.out.println("Error fetching record.");
                    return;
                }

                String responseData = fetchResponse.body().string();
                JSONArray jsonArray = new JSONArray(responseData);

                if (jsonArray.length() > 0) {
                    String existingBill = jsonArray.getJSONObject(0).getString("bill");
                    System.out.println("Existing Bill: " + existingBill);

                    // Split the existingBill into parts and assign to lastMonth fields
                    runOnUiThread(() -> {
                        if (existingBill.length() >= 8) {
                            lastMonth1.setText(String.valueOf(existingBill.charAt(0)));
                            lastMonth2.setText(String.valueOf(existingBill.charAt(1)));
                            lastMonth3.setText(String.valueOf(existingBill.charAt(2)));
                            lastMonth4.setText(String.valueOf(existingBill.charAt(3)));
                            lastMonth5.setText(String.valueOf(existingBill.charAt(4)));
                            lastMonth6.setText(String.valueOf(existingBill.charAt(5)));
                            lastMonth7.setText(String.valueOf(existingBill.charAt(6)));
                            lastMonth8.setText(String.valueOf(existingBill.charAt(7)));

                            // Concatenate the values for further use
                            lastMonth = existingBill;
                        } else {
                            System.out.println("Existing bill is too short.");
                        }
                    });
                } else {
                    System.out.println("No existing record found.");
                }
            } catch (IOException | JSONException e) {
                System.out.println("Failed to fetch record. Error: " + e.getMessage());
            }
        }).start();
    }
}
