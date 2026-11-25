package com.example.myapplication;

import java.util.ArrayList;

// כל המציאות של האפליקציה נמצאות כאן, במקום אחד.
// static = משותף לכל המסכים, ושורד סיבוב מסך (נמחק רק כשהאפליקציה נסגרת).
// ביחידה 8 המחלקה הזו תשמור ב-Firestore, ושאר הקוד כמעט לא ישתנה. זו הפרדה בין לוגיקה לתצוגה
public class ItemRepository {

    private static final ArrayList<FoundItem> items = new ArrayList<>();

    // החדש ביותר נכנס ראשון
    public static void add(FoundItem item) {
        items.add(0, item);
    }

    public static ArrayList<FoundItem> getAll() {
        return items;
    }

    public static int size() {
        return items.size();
    }

    // null אם עוד אין דיווחים
    public static FoundItem getLatest() {
        if (items.isEmpty()) {
            return null;
        }
        return items.get(0);
    }
}
