package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class ReportActivity extends AppCompatActivity {

    // המפתחות של הפתק. static final = const ב-C#
    public static final String EXTRA_TITLE = "title";
    public static final String EXTRA_LOCATION = "location";
    public static final String EXTRA_PHONE = "phone";

    private EditText etTitle;
    private EditText etLocation;
    private EditText etPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        etTitle = findViewById(R.id.etTitle);
        etLocation = findViewById(R.id.etLocation);
        etPhone = findViewById(R.id.etPhone);
        Button btnSave = findViewById(R.id.btnSave);
        Button btnCancel = findViewById(R.id.btnCancel);

        btnSave.setOnClickListener(v -> save());
        // TODO 1: בלחיצה על btnCancel: setResult(RESULT_CANCELED) ואז finish()

    }

    // TODO 2: בדיקת קלט, ואז החזרת הפרטים ב-Intent
    private void save() {
        String title = etTitle.getText().toString().trim();
        String location = etLocation.getText().toString().trim();
        String phone = etPhone.getText().toString().trim().replace("-", "");

        // TODO 2א: שם ריק -> etTitle.setError("מה מצאתם?") ו-return. אותו דבר למקום (etLocation)
        //   הטלפון לא חובה. אם הוא לא ריק ולא מתאים לתבנית "0\\d{8,9}" (phone.matches) -> setError ו-return

        // TODO 2ב: Intent data = new Intent(), שלושה putExtra עם EXTRA_TITLE / EXTRA_LOCATION / EXTRA_PHONE,
        //   setResult(RESULT_OK, data) ואז finish()

    }
}