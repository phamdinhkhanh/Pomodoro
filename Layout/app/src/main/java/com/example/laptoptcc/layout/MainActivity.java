package com.example.laptoptcc.layout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.toString();
    private Spinner sp1;
    private Button btn;
    private RatingBar rtb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getReferences();
        setupUI();
        getListerner();
    }

    private void getReferences() {
        sp1 = (Spinner) findViewById(R.id.sp1);
        btn = (Button) findViewById(R.id.button);
        rtb = (RatingBar) findViewById(R.id.rtb);
    }

    private void setupUI(){
        //Create VectorString
        String[] character = new String[]{
                "Thong minh",
                "Tot bung",
                "An nhieu"
        };
        //ArrayAdapter
        ArrayAdapter<String> characterArray = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_item,
                character
        );
        //attach with spiner
        sp1.setAdapter(characterArray);
        sp1.post(new Runnable() {
            @Override
            public void run() {
                sp1.setSelection(2);
            }
        });
    }

    private void getListerner() {
        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, String.format("%s %d", "sp1.onItemSelected",position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d(TAG,"noThingSelected");
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,String.format("Rating %s",rtb.getRating()));
            }
        });
    }

}
