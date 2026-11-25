package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // הטלפון של מזכירות בית הספר. להחליף במספר אמיתי
    public static final String OFFICE_PHONE = "0501234567";

    private TextView tvCount;
    private TextView tvLatest;
    private Button btnNavigate;
    private Button btnShare;

    // TODO 3: Intent מפורש + תוצאה. מסך הדיווח מחזיר את פרטי המציאה
    private final ActivityResultLauncher<Intent> reportLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                // RESULT_OK ו-getData לא null -> ליצור FoundItem משלושת ה-extras,
                //   ItemRepository.add, refresh() ו-Toast "הדיווח נוסף"
                // אחרת (ביטול או כפתור חזור) -> Toast "הדיווח בוטל"

            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvCount = findViewById(R.id.tvCount);
        tvLatest = findViewById(R.id.tvLatest);
        btnNavigate = findViewById(R.id.btnNavigate);
        btnShare = findViewById(R.id.btnShare);
        Button btnReport = findViewById(R.id.btnReport);
        Button btnCallOffice = findViewById(R.id.btnCallOffice);

        // TODO 4: btnReport פותח את ReportActivity דרך reportLauncher.launch(new Intent(this, ReportActivity.class))

        // TODO 5: שלושה Intents מרומזים, דרך IntentHelper
        //   btnCallOffice -> IntentHelper.dial עם OFFICE_PHONE
        //   btnNavigate -> IntentHelper.navigate עם המקום של ItemRepository.getLatest() (לבדוק שלא null)
        //   btnShare -> IntentHelper.share עם toShareText של האחרונה (לבדוק שלא null)

        refresh();
    }

    // TODO 6: מציגים את המצב הנוכחי. מקרה קצה: עוד אין דיווחים
    private void refresh() {
        FoundItem latest = ItemRepository.getLatest();
        // tvCount = "דווחו X מציאות" (ItemRepository.size)
        // latest == null -> tvLatest = "עוד אין דיווחים...", ולכבות את btnNavigate ו-btnShare (setEnabled(false))
        // אחרת -> tvLatest = שם, מקום ושעה של האחרונה, ולהדליק את שני הכפתורים
    }
}
