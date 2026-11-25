package com.example.myapplication;
import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class ReportActivity extends AppCompatActivity {

    // המפתחות של הפתק. static final = const ב-C#
    public static final String EXTRA_TITLE = "title";
    public static final String EXTRA_LOCATION = "location";
    public static final String EXTRA_PHONE = "phone";

    private EditText etTitle;
    private EditText etLocation;
    private ImageView ivPhoto;
    private Bitmap photo;
    private Button btnSettings;
    private Button btnPhoto;
    private EditText etPhone;
    private TextView tvPhotoStatus;

    private final ActivityResultLauncher<Void> cameraLauncher =
            registerForActivityResult(new ActivityResultContracts.TakePicturePreview(), bitmap -> {
                if (bitmap != null) {
                    photo = bitmap;
                    ivPhoto.setImageBitmap(photo);
                }
            });
    private final ActivityResultLauncher<String> permissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), granted -> {
                if (granted) {
                    btnSettings.setVisibility(View.GONE);
                    tvPhotoStatus.setText("התמונה נוספה!");
                    cameraLauncher.launch(null);
                } else if (!shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                    // סירב וגם אין יותר חלון מערכת = חסימה
                    btnSettings.setVisibility(View.VISIBLE);
                }
            });
    @Override
    protected void onResume() {
        super.onResume();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            btnSettings.setVisibility(View.GONE);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        etTitle = findViewById(R.id.etTitle);
        etLocation = findViewById(R.id.etLocation);
        etPhone = findViewById(R.id.etPhone);
        Button btnSave = findViewById(R.id.btnSave);
        Button btnCancel = findViewById(R.id.btnCancel);
        btnSettings = findViewById(R.id.btnSettings);
        btnPhoto = findViewById(R.id.btnPhoto);


        btnSave.setOnClickListener(v -> save());

        btnCancel.setOnClickListener(v ->
        {
            setResult(Activity.RESULT_CANCELED);
            finish();
        });
        btnSettings.setOnClickListener(v -> startActivity(
                new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.fromParts("package", getPackageName(), null))));
        btnPhoto.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED) {
                cameraLauncher.launch(null);                       // אושרה
            } else if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                new AlertDialog.Builder(this)                      // סורבה פעם אחת
                        .setTitle("צריך גישה למצלמה")
                        .setMessage("כדי לצלם את הפריט צריך הרשאה למצלמה.")
                        .setPositiveButton("אישור", (d, w) ->
                                permissionLauncher.launch(Manifest.permission.CAMERA))
                        .setNegativeButton("ביטול", null)
                        .show();
            } else {
                permissionLauncher.launch(Manifest.permission.CAMERA);  // ראשונה, או חסומה
            }
        });
        ivPhoto = findViewById(R.id.ivPhoto);
        Button btnPhoto = findViewById(R.id.btnPhoto);
        btnPhoto.setOnClickListener(v -> permissionLauncher.launch(Manifest.permission.CAMERA));
        // TODO 1: בלחיצה על btnCancel: setResult(RESULT_CANCELED) ואז finish()

    }

    // TODO 2: בדיקת קלט, ואז החזרת הפרטים ב-Intent
    private void save() {
        String title = etTitle.getText().toString().trim();
        String location = etLocation.getText().toString().trim();
        String phone = etPhone.getText().toString().trim().replace("-", "");

        if (title.isEmpty()) {
            etTitle.setError("מה מצאתם?");
            return;
        }
        if (location.isEmpty()) {
            etLocation.setError("איפה מצאתם?");
            return;
        }
        if (!phone.isEmpty() && !phone.matches("0\\d{8,9}")) {
            etPhone.setError("מספר טלפון לא תקין");
            return;
        }

        // TODO 2ב
        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_LOCATION, location);
        data.putExtra(EXTRA_PHONE, phone);
        setResult(Activity.RESULT_OK, data);
        finish();

    }
}