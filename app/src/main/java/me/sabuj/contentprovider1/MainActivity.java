package me.sabuj.contentprovider1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText nameEdit = (EditText) findViewById(R.id.editName) ;
        EditText emailEdit = (EditText) findViewById(R.id.editEmail) ;
        EditText professionEdit = (EditText) findViewById(R.id.editProfession) ;
    }
}
