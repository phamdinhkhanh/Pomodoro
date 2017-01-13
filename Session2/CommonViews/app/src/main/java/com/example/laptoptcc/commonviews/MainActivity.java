package com.example.laptoptcc.commonviews;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ImageView imvElon;
    private Spinner spFruit;
    private EditText etSimple;
    private Button button;
    private CheckBox checkBox;
    private RadioButton radioButton1;
    private RadioButton radioButton2;
    private RadioButton radioButton3;
    private RatingBar ratingBar;
    private SeekBar seekBar;
    private TextView textViewSB;
    private SearchView svSimple;
    private ProgressBar progressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getReferences();
        setupUI();
        addListeners();
    }

    private void addListeners() {
        spFruit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG,String.format("%s %d","spFruit.onItemSelected",position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d(TAG,"onNothingSelected");
            }
        });

        etSimple.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.d(TAG,String.format("onTextChanged: CharSequence %s, start %s, count  %s, after %s",s,start,count,after));
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d(TAG,String.format("afterTextChanged:CharSequence %s, start %s, count  %s, after %s",s,start,count));
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d(TAG,String.format("afterTextChanged %s",s));
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.d(TAG,String.format("SeekBar: OnSeekBarChangeListener: %s,%s",progress,fromUser));
                textViewSB.setText(String.format("%s %s",progress,seekBar.getMax()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Log.d(TAG,"onStartTrackingTouch");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.d(TAG,"onStartTrackingTouch");
            }
        });

        svSimple.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d(TAG,String.format("SearchView: onQueryTextSubmit: %s",query));
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d(TAG,String.format("SearchView: onQueryTextChange :%s",newText));
                return true;
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                Log.d(TAG,String.format("%s:%s",checkBox.getId(),checkBox.isChecked()));
                //
                checkBox.setChecked(!checkBox.isChecked());
                //
                Log.d(TAG,radioButton1.isChecked()?"Male":radioButton2.isChecked()?"Female":"Undefended");
                //
                Log.d(TAG,String.format("Rating %s",ratingBar.getRating()));
                //
                Log.d(TAG,String.format("Seebar : %s", seekBar.getProgress()));
                //
                seekBar.setProgress(seekBar.getProgress()+5);

                progressBar.setProgress(progressBar.getProgress()+5);

                svSimple.clearFocus();

                svSimple.setQuery("",false);

                svSimple.setIconified(true);
            }
        });

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Log.d(TAG,String.format("Checkbox: %s",b));
            }
        });
    }

    private void getReferences(){
        imvElon = (ImageView) findViewById(R.id.imv_elm);
        spFruit = (Spinner) findViewById(R.id.sp_fruit);
        etSimple = (EditText) findViewById(R.id.et_simple);
        button = (Button) findViewById(R.id.button);
        checkBox = (CheckBox) findViewById(R.id.check);
        radioButton1 = (RadioButton) findViewById(R.id.male);
        radioButton2 = (RadioButton) findViewById(R.id.female);
        radioButton3 = (RadioButton) findViewById(R.id.undefended);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        seekBar = (SeekBar) findViewById(R.id.seeBar);
        textViewSB = (TextView) findViewById(R.id.textViewSB);
        svSimple = (SearchView) findViewById(R.id.searchView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    private void setupUI() {
        imvElon.setImageResource(R.drawable.elon_musk1);
        checkBox.setChecked(true);
        //1:
        String[] khanh = new String[]{
                "Khanh dep trai",
                "Khanh galang",
                "Khanh thong minh",
                "Khanh khoe manh"
        };
        //2:
        ArrayAdapter<String> khanhArrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                khanh
        );
        //3:
        spFruit.setAdapter(khanhArrayAdapter);
        radioButton1.setChecked(true);
        //spFruit.setSelection(2);
        spFruit.post(new Runnable() {
            @Override
            public void run() {
                spFruit.setSelection(2);
            }
        });
    }
}
