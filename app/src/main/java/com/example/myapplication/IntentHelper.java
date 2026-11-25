package com.example.myapplication;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

// כל ה-Intents המרומזים של האפליקציה במחלקה אחת. כל מסך יכול להשתמש בהם
public class IntentHelper {

    // חייגן עם המספר מוכן. לא צריך הרשאה, כי המשתמש לוחץ "חייג" בעצמו
    public static void dial(Context context, String phone) {
        if (phone == null || phone.isEmpty()) {
            Toast.makeText(context, "אין מספר טלפון", Toast.LENGTH_SHORT).show();
            return;
        }
        start(context, new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone)));
    }

    // חיפוש מקום באפליקציית מפות. Uri.encode כדי שעברית ורווחים יעברו נכון
    public static void navigate(Context context, String place) {
        start(context, new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=" + Uri.encode(place))));
    }

    // שיתוף טקסט: וואטסאפ, מייל, SMS. המשתמש בוחר
    public static void share(Context context, String text) {
        Intent send = new Intent(Intent.ACTION_SEND);
        send.setType("text/plain");
        send.putExtra(Intent.EXTRA_TEXT, text);
        start(context, Intent.createChooser(send, "שתף דרך"));
    }

    // מקרה קצה: אין במכשיר אפליקציה שמתאימה (למשל אין מפות באמולטור)
    private static void start(Context context, Intent intent) {
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, "אין במכשיר אפליקציה שיודעת לבצע את זה", Toast.LENGTH_SHORT).show();
        }
    }
}
