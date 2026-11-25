package com.example.myapplication;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

// מציאה אחת. מחלקה רגילה, כמו ב-C#. בכל שלב של הפרויקט היא תקבל עוד שדות
public class FoundItem {

    private String title;
    private String location;
    private String finderPhone;
    private long createdAt;

    public FoundItem(String title, String location, String finderPhone) {
        this.title = title;
        this.location = location;
        this.finderPhone = finderPhone;
        this.createdAt = System.currentTimeMillis();
    }

    public String getTitle() {
        return title;
    }

    public String getLocation() {
        return location;
    }

    public String getFinderPhone() {
        return finderPhone;
    }

    public boolean hasPhone() {
        return finderPhone != null && !finderPhone.isEmpty();
    }

    public String getTimeText() {
        return new SimpleDateFormat("dd/MM HH:mm", Locale.getDefault()).format(new Date(createdAt));
    }

    // הטקסט שנשלח בשיתוף (Intent מרומז ACTION_SEND)
    public String toShareText() {
        String text = "נמצא: " + title + "\nאיפה: " + location + "\nמתי: " + getTimeText();
        if (hasPhone()) {
            text += "\nטלפון של המוצא: " + finderPhone;
        }
        return text;
    }
}
