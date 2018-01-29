package com.buljubasic.melissa.buljubas_subbox;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String FILENAME = "subs.sav";
    private ListView subsList;
    private Button addSubscription;

    private CustomListViewAdapter customListView;

    private ArrayList<Subscription> subscriptionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        subsList = (ListView) findViewById(R.id.subList);
        addSubscription = (Button) findViewById(R.id.button);
        subscriptionList = new ArrayList<>();

        customListView = new CustomListViewAdapter(this, subscriptionList);
        subsList.setAdapter(customListView);

        addSubscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddSubscriptionActivity.class);
                Intent.putExtra(command, "create");
                startActivityForResult(intent, 1);
            }
        });

         subsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              Intent intent = new Intent();
              Intent.putExtra(name, subscriptionList.at(position).getName());
              Intent.putExtra(date, subscriptionList.at(position).getDate());
              Intent.putExtra(charge, subscriptionList.at(position).getCharge());
              Intent.putExtra(comment, subscriptionList.at(position).getComment());
              Intent.putExtra(command, "edit");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            try {
                Subscription newSub = new Subscription(data.getStringExtra("name"), data.getStringExtra("date"), data.getStringExtra("charge"), data.getStringExtra("comment"));
                subscriptionList.add(newSub);
                subsList.setAdapter(customListView);
                subsList.smoothScrollToPosition(0);
            } catch (CommentTooLongException e) {
                Log.i("---->", "comment too long");
            }
        }
        else if (requestCode == 2) {
          subscriptionList.remove(position);
        }
        else if (requestCode == 2) {
          try {
            name = data.getStringExtra("name");
            subscriptionList.at(position).setName(name);
            date = data.getStringExtra("date");
            subscriptionList.at(position).setName(date);
            charge = data.getStringExtra("charge");
            subscriptionList.at(position).setName(charge);
            comment = data.getStringExtra("comment");
            subscriptionList.at(position).setName(comment);
          } catch (CommentTooLongException e) {
            Log.i("---->", "comment too long");
          }
        }
    }

   private void saveList() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME,
                    Context.MODE_APPEND);
            for (int i = 0; i < subscriptionList.size(); i++) {
                Subscription sub = subscriptionList.get(i);
                fos.write(new String(sub.getName() +" | " + sub.getCharge() + " | " + sub.getDate() + " | " + sub.getComment()).getBytes());
                fos.close();
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
