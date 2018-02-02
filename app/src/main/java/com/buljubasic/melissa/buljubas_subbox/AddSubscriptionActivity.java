package com.buljubasic.melissa.buljubas_subbox;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/*
 * AddSubscriptionActivity
 * This activity is called AddSubscription because originally I only intended to use it to add a new
 * subscription but later I also implemented it to edit the activity
 */
public class AddSubscriptionActivity extends AppCompatActivity {
    private EditText subName;
    private EditText subDate;
    private EditText subCharge;
    private EditText subComment;
    private Button submitBtn;
    private Button deleteBtn;

    /*
     * The method checks if the main activity wants it to edit or make a new subscription
     * The delete button is only shown if editing
     * If the delete button is pressed the activity returns result code 2
     * Else returns the typed content in all the EditText fields
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subscription);

        subName = (EditText) findViewById(R.id.addName);
        subDate = (EditText) findViewById(R.id.addDate);
        subCharge = (EditText) findViewById(R.id.addCharge);
        subComment = (EditText) findViewById(R.id.addComment);

        submitBtn = (Button) findViewById(R.id.btnSubmit);
        deleteBtn = (Button) findViewById(R.id.btnDelete);

        final String command = getIntent().getStringExtra("command");
        //Set up for editing
        //Show the old subscription information in the Edit Text Fields
        if (command.equals("edit")) {
            deleteBtn.setVisibility(View.VISIBLE);
            deleteBtn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent();
                    setResult(2, intent);
                    finish();
                }
            });
            String oldName = getIntent().getStringExtra("name");
            String oldDate = getIntent().getStringExtra("date");
            String oldCharge = getIntent().getStringExtra("charge");
            String oldComment = getIntent().getStringExtra("comment");

            subName.setText(oldName);
            subDate.setText(oldDate);
            subCharge.setText(oldCharge);
            subComment.setText(oldComment);
            //Set up for making a new subscription
            //Leave the EditText fields blank and hide the delete button
        } else {
            deleteBtn.setVisibility(View.GONE);
        }
        //If submit button is clicked
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = subName.getText().toString();
                String date = subDate.getText().toString();
                String charge = subCharge.getText().toString();
                String comment = subComment.getText().toString();

                Intent intent = new Intent();
                intent.putExtra("name", name);
                intent.putExtra("date", date);
                intent.putExtra("charge", charge);
                intent.putExtra("comment", comment);
                if (command.equals("edit")) {
                    setResult(1, intent);
                } else {
                    setResult(0, intent);
                }
                finish();
            }
        });
    }
}