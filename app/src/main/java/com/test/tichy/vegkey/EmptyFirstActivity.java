package com.test.tichy.vegkey;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class EmptyFirstActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty_first);

        Intent intent = getIntent();
        overridePendingTransition( 0, 0);
        startActivity(new Intent(EmptyFirstActivity.this, MainActivity.class));
        finish();
        overridePendingTransition( 0, 0);
    }

}

