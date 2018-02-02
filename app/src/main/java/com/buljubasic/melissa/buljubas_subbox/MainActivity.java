package com.buljubasic.melissa.buljubas_subbox;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/*
 * Main activity class
 * Shows the list of subscriptions and saves the list after the app closes
 */
public class MainActivity extends AppCompatActivity {
    private static final String FILENAME = "subs.sav";
    private ListView subsList;
    private Button addSubscription;
    private int subIndex = 0;

    private CustomListViewAdapter customListView;

    private ArrayList<Subscription> subscriptionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        subsList = (ListView) findViewById(R.id.subList);
        addSubscription = (Button) findViewById(R.id.button);

        loadList();

        customListView = new CustomListViewAdapter(this, subscriptionList);
        subsList.setAdapter(customListView);

        //Add a new subscription
        addSubscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddSubscriptionActivity.class);
                intent.putExtra("command", "new");
                startActivityForResult(intent, 1);
            }
        });

        //when the list is clicked call the AddSubscriptionActivity to edit
        subsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                subIndex = position;
                Intent intent = new Intent(MainActivity.this, AddSubscriptionActivity.class);
                intent.putExtra("name", subscriptionList.get(position).getName());
                intent.putExtra("date", subscriptionList.get(position).getDate());
                intent.putExtra("charge", subscriptionList.get(position).getCharge());
                intent.putExtra("comment", subscriptionList.get(position).getComment());
                intent.putExtra("command", "edit");
                startActivityForResult(intent, 1);
            }
        });
    }

    //get the result of the AddSubscriptionActivity
    //The activity returns a different result code depending on what it wants the main activity to do
    //resultCode 0 adds a new subscription
    //resultCode 1 edits a current subscription
    //resultCode 2 removes the selected subscription
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 0) {
            try {
                Subscription newSub = new Subscription(data.getStringExtra("name"), data.getStringExtra("date"),
                        data.getStringExtra("charge"), data.getStringExtra("comment"));
                subscriptionList.add(newSub);
            } catch (CommentTooLongException e) {
                Toast.makeText(this, "Comment cannot be longer then 30 characters", Toast.LENGTH_LONG).show();
            } catch (NameTooLongException e) {
                Toast.makeText(this, "Name cannot be longer then 20 characters", Toast.LENGTH_LONG).show();
            }
        }
        //All items are changed when the subscription is edited
        //I think there is a more efficient way to do this but I don't know how to implement it
        else if (resultCode == 1) {
            try {
                subscriptionList.get(subIndex).setName(data.getStringExtra("name"));
                subscriptionList.get(subIndex).setDate(data.getStringExtra("date"));
                subscriptionList.get(subIndex).setCharge(data.getStringExtra("charge"));
                subscriptionList.get(subIndex).setComment(data.getStringExtra("comment"));
            } catch (CommentTooLongException e) {
                Toast.makeText(this, "Comment cannot be longer then 30 characters", Toast.LENGTH_LONG).show();
            } catch (NameTooLongException e) {
                Toast.makeText(this, "Name cannot be longer then 20 characters", Toast.LENGTH_LONG).show();
            }
        }
        else if (resultCode == 2) {
            subscriptionList.remove(subIndex);
        }
        subsList.setAdapter(customListView);
    }

    //Save the subscription list
    private void saveList() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
            for (int i = 0; i < subscriptionList.size(); i++) {
                Subscription newSub = subscriptionList.get(i);
                fos.write((newSub.getName() + "|" + newSub.getDate() + "|"
                        + newSub.getCharge() + "|" + newSub.getComment() + "\n").getBytes());
            }
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //load the subscription list when the app is reopened
    private void loadList() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            String line = in.readLine();
            subscriptionList = new ArrayList<>();
            while (line != null) {
                String[] parts = line.split("\\|");
                try {
                    if (parts.length == 3) {
                        subscriptionList.add(new Subscription(parts[0], parts[1], parts[2]));
                    } else {
                        subscriptionList.add(new Subscription(parts[0], parts[1], parts[2], parts[3]));
                    }
                } catch (CommentTooLongException e) {
                    Log.i("---->", "Comment too long");
                } catch (NameTooLongException e) {
                    Log.i("---->", "Name too long");
                }
                line = in.readLine();
            }
            fis.close();
        } catch (FileNotFoundException e) {
            subscriptionList = new ArrayList<>();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        saveList();
    }
}