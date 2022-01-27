package de.hawlandshut.pluto22.model;

import com.google.firebase.database.DataSnapshot;

public class Post {

    public String uid;
    public String author;
    public String title;
    public String body;
    public long timestamp;
    public String firebaseKey;

    /**
     *
     */
    public Post() {
    }

    /**
     * KONSTRUKTOR ZUR ERSTELLUNG EINES POSTS
     *
     *
     * @param uid
     * @param author
     * @param title
     * @param body
     * @param timestamp
     * @param firebaseKey
     */
    public Post(String uid, String author, String title, String body, long timestamp, String firebaseKey){
        this.uid = uid;
        this.author = author;
        this.title = title;
        this.body = body;
        this.timestamp = timestamp;
        this.firebaseKey = firebaseKey;
    }

    /**
     *
     * AUS DER FIREBASE DATENBANK BEKOMMEN WIR EINEN SOGENANNTEN SNAPSHOT
     * EINES POSTS GELIEFERT. AUS DIESEM SNAPSHOT HOLEN WIR DIE FÜR UNS WICHTIGEN
     * DATEN HERAUS UND GEBEN DIESE IN FORM EINES NEUE POSTS ZURÜCK.
     *
     * DANACH WIRD NÄMLICH DER POST IN DER MAINACTIVITY DARGESTELLT
     *
     * @param snapshot
     * @return
     */
    public static Post fromSnapShot(DataSnapshot snapshot) {
        String uid = (String) snapshot.child("uid").getValue();
        String author = (String) snapshot.child("author").getValue();
        String title = (String) snapshot.child("title").getValue();
        String body = (String) snapshot.child("body").getValue();
        long timestamp = (long) snapshot.child("timestamp").getValue();
        String firebaseKey = (String) snapshot.getKey();

        return new Post(uid,author,title,body,0,firebaseKey);
    }

}
