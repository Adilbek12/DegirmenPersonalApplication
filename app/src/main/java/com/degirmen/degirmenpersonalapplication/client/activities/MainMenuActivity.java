package com.degirmen.degirmenpersonalapplication.client.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.degirmen.degirmenpersonalapplication.R;

import java.util.ArrayList;

public class MainMenuActivity extends AppCompatActivity {

    private Button vynosButton, personalButton, orderButton;
    private ListView tableListVeiew;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> numbers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        numbers = new ArrayList<>();

        tableListVeiew = findViewById(R.id.tableListView);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, numbers);
        tableListVeiew.setAdapter(adapter);

        vynosButton.setOnClickListener(buttonClickListener);
        personalButton.setOnClickListener(buttonClickListener);
        orderButton.setOnClickListener(buttonClickListener);


    }

    View.OnClickListener buttonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.orderButton:

                    break;
                case R.id.personalButton:

                    break;

                case R.id.vynosButton:

                    break;
            }
        }
    };
}
