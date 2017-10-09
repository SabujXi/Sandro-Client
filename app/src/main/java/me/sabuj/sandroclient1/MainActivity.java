package me.sabuj.sandroclient1;

import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public Uri theUri = Uri.parse("content://me.sabuj.sandroprovider1.SANDRO_PROVIDER1/persons");
    public EditText nameEdit;
    public EditText emailEdit;
    public EditText professionEdit;
    public String[] theProjection = {"id", "name", "email", "profession"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameEdit = (EditText) findViewById(R.id.editName);
        emailEdit = (EditText) findViewById(R.id.editEmail);
        professionEdit = (EditText) findViewById(R.id.editProfession);

        final TextView textInfo = (TextView) findViewById(R.id.textInfo);

        Button insertBtn = (Button) findViewById(R.id.insert);
        Button queryByEmailBtn = (Button) findViewById(R.id.queryByEmail);
        Button updateByEmailBtn = (Button) findViewById(R.id.updateByEmail);
        Button deleteByEmailBtn = (Button) findViewById(R.id.deleteByEmail);

        // Setup on click listeners
        insertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] ip = getInputValues();
                Uri uri = getContentResolver().insert(theUri, getContentValues(ip));
                if(ContentUris.parseId(uri) == -1){
                    textInfo.setText("Error occurred: cannot insert the data! It seems like that a person with the same email already exists!");
                    return;
                }
                textInfo.setText("Data Inserted:" +
                        "\nName: " + ip[0] +
                        "\nEmail: " + ip[1] +
                        "\nProfession: " + ip[2]
                    );
                clearInputs();
            }
        });

        queryByEmailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] ip = getInputValues();

                Cursor cursor = getContentResolver().query(theUri, theProjection, "email=?", new String[]{ip[1]}, null);
                if(cursor.getCount() == 0){
                    textInfo.setText("No record with the email address found");
                }else {
                    cursor.moveToFirst();
                    textInfo.setText("Query Result:" +
                            "\nName: " + cursor.getString(1) +
                            "\nEmail: " + cursor.getString(2) +
                            "\nProfession: " + cursor.getString(3)
                    );
                }
                cursor.close();
                clearInputs();
            }
        });

        updateByEmailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the row id first
//                textInfo.setText("clicked update?");
                String[] ip = getInputValues();

                Cursor cursor = getContentResolver().query(theUri, theProjection, "email=?", new String[]{ip[1]}, null);
                textInfo.setText("clicked update?");
                if(cursor.getCount() == 0){
                    textInfo.setText("No record with the email address found to delete!");
                }else {

                    cursor.moveToFirst();

                    getContentResolver().update(ContentUris.withAppendedId(theUri, cursor.getInt(0)), getContentValues(ip),
                            "email=?", new String[]{cursor.getString(2)});

                    textInfo.setText("Data Updated For:" +
                            "\nName: " + cursor.getString(1) + " --> " + ip[0] +
                            "\nEmail: " + cursor.getString(2) + " --> " + ip[1] +
                            "\nProfession: " + cursor.getString(3) + " --> " + ip[2]
                    );
                }

                cursor.close();
                clearInputs();
            }
        });

        deleteByEmailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] ip = getInputValues();
                long ret = getContentResolver().delete(theUri, "email=?", new String[]{ip[1]});
                if(ret <= 0) {
                    textInfo.setText("The delete operation was not successful!");
                }else{
                    textInfo.setText("Deleted successfully!");
                }
                clearInputs();
            }
        });
    }

    public String[] getInputValues(){
        return new String[]{
                nameEdit.getText().toString(),
                emailEdit.getText().toString().toLowerCase(),
                professionEdit.getText().toString()
        };
    }

    public ContentValues getContentValues(String[] ip){
        ContentValues cv = new ContentValues();
        cv.put("name", ip[0]);
        cv.put("email", ip[1]);
        cv.put("profession", ip[2]);
        return cv;
    }

    public void clearInputs(){
        nameEdit.setText("");
        emailEdit.setText("");
        professionEdit.setText("");
    }
}
