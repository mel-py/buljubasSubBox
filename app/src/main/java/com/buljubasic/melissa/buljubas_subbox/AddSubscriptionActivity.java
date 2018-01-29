package com.buljubasic.melissa.buljubas_subbox;

import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;

public class AddSubscriptionActivity extends AppCompatActivity {
    private EditText subName;
    private EditText subDate;
    private EditText subCharge;
    private EditText subComment;
    private Button submitBtn;

    private Subscription newSub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subscription);

        subName = (EditText) findViewById(R.id.addName);
        subDate = (EditText) findViewById(R.id.addDate);
        subCharge = (EditText) findViewById(R.id.addCharge);
        subComment = (EditText) findViewById(R.id.addComment);

        submitBtn = (Button) findViewById(R.id.btnSubmit);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = subName.getText().toString();
                String date = subDate.getText().toString();
                String charge = subCharge.getText().toString();
                String comment = subComment.getText().toString();

                Toast.makeText(AddSubscriptionActivity.this, name, Toast.LENGTH_LONG).show();

                Intent intent = new Intent();
                intent.putExtra("name", name);
                intent.putExtra("date", date);
                intent.putExtra("charge", charge);
                intent.putExtra("comment", comment);
                setResult(1, intent);
                finish();
            }
        });
    }
}
