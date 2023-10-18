package com.example.shopbaefood.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.shopbaefood.model.dto.ContactClient;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

public class ContactClientDataSource {
    public static final String CONTACT_CLIENT = "contact_client";
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;
    private String[][] columns = {{"name",DatabaseHelper.TEXT},{"username",DatabaseHelper.TEXT},{"phone",DatabaseHelper.TEXT},{"address",DatabaseHelper.TEXT},{"email",DatabaseHelper.TEXT},{"avartar",DatabaseHelper.BLOB}};
    public ContactClientDataSource(Context context){
            dbHelper= new DatabaseHelper(context,"shopbaefood.db", 1, "contact_client",columns);
    }
    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }
    public void checkAndAddOrUpdateContactClient(ContactClient contactClient) {
        String selection = "id=?";
        String[] selectionArgs = {String.valueOf(contactClient.getId())};

        Cursor cursor = database.query(CONTACT_CLIENT, null, selection, selectionArgs, null, null, null);
        if (cursor.getCount() > 0) {
            ContentValues values= new ContentValues();
            values.put("name", contactClient.getName());
            values.put("username", contactClient.getUsername());
            values.put("phone", contactClient.getPhone());
            values.put("address", contactClient.getAddress());
            values.put("email", contactClient.getEmail());
            values.put("avartar", contactClient.getAvartar());

            database.update(CONTACT_CLIENT, values, selection, selectionArgs);
        } else {
            ContentValues values= new ContentValues();
            values.put("name", contactClient.getName());
            values.put("username", contactClient.getUsername());
            values.put("phone", contactClient.getUsername());
            values.put("address", contactClient.getUsername());
            values.put("email", contactClient.getUsername());
            values.put("avartar", contactClient.getAvartar());

            database.insert(CONTACT_CLIENT, null,values);
        }
        cursor.close();
    }
    public List<ContactClient> getAllContacts() {
        List<ContactClient> contacts = new ArrayList<>();
        Cursor cursor = database.query(
                CONTACT_CLIENT, null, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ContactClient contact = cursorToContact(cursor);
            contacts.add(contact);
            cursor.moveToNext();
        }
        cursor.close();
        return contacts;
    }
    public ContactClient getContactById(int desiredId) {
        String whereClause = "id = ?";
        String[] whereArgs = {String.valueOf(desiredId)};

        Cursor cursor = database.query(
                CONTACT_CLIENT, null, whereClause, whereArgs, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            ContactClient contact = cursorToContact(cursor);
            cursor.close();
            return contact;
        } else {
            return null;
        }
    }


    private ContactClient cursorToContact(Cursor cursor) {
        int idIndex = cursor.getColumnIndex("id");
        int nameIndex = cursor.getColumnIndex("name");
        int usernameIndex = cursor.getColumnIndex("username");
        int phoneIndex = cursor.getColumnIndex("phone");
        int addressIndex = cursor.getColumnIndex("address");
        int emailIndex = cursor.getColumnIndex("email");
        int avartarIndex = cursor.getColumnIndex("avartar");

        int id = cursor.getInt(idIndex);
        String name = cursor.getString(nameIndex);
        String username = cursor.getString(usernameIndex);
        String phone = cursor.getString(phoneIndex);
        String address = cursor.getString(addressIndex);
        String email = cursor.getString(emailIndex);
        byte[] avartar = cursor.getBlob(avartarIndex);

        return new ContactClient(id, name, username, phone, address, email, avartar);
    }
}
