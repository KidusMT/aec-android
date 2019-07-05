package com.aait.aec.ui.settings;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;

import com.aait.aec.R;
import com.aait.aec.ui.base.BaseActivity;
import com.aait.aec.utils.AppConstants;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.aait.aec.utils.AppConstants.PREF_KEY_CURRENT_LANGUAGE;

public class LanguageActivity extends BaseActivity implements View.OnClickListener {

    RadioButton mRadioButton_amharic;
    RadioButton mRadioButton_english;

    SharedPreferences sharedPreferences;

    @BindView(R.id.languages_toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_list);


        mRadioButton_amharic = findViewById(R.id.check_amharic);
        mRadioButton_english = findViewById(R.id.check_english);

        mRadioButton_amharic.setOnClickListener(this);
        mRadioButton_english.setOnClickListener(this);

        findViewById(R.id.check_english_container).setOnClickListener(this);
        findViewById(R.id.check_amharic_container).setOnClickListener(this);

        sharedPreferences = getApplicationContext().getSharedPreferences(AppConstants.PREF_NAME, Context.MODE_PRIVATE);
        String currentLanguage = sharedPreferences.getString("PREF_KEY_CURRENT_LANGUAGE", "en");

        ButterKnife.bind(this);

        setUp();

        assert currentLanguage != null;
        if (currentLanguage.equals("en")) {
            mRadioButton_english.setChecked(true);
            mRadioButton_amharic.setChecked(false);
        } else if (currentLanguage.equals("am")) {
            mRadioButton_english.setChecked(false);
            mRadioButton_amharic.setChecked(true);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.check_english:
            case R.id.check_english_container:
                Locale locale = new Locale("en");
                Locale.setDefault(locale);
                Configuration config = new Configuration();
                config.locale = locale;
                getBaseContext().getResources().updateConfiguration(config,
                        getBaseContext().getResources().getDisplayMetrics());

                sharedPreferences.edit().putString("PREF_KEY_CURRENT_LANGUAGE", "en").apply();
                mRadioButton_amharic.setChecked(false);
                try {
                    Thread.sleep(650);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(this, LanguageActivity.class);
                startActivity(intent);
                finish();

                onConfigurationChanged(config);
                break;
            case R.id.check_amharic:
            case R.id.check_amharic_container:

                Locale localea = new Locale("am");
                Locale.setDefault(localea);
                Configuration configa = new Configuration();
                configa.locale = localea;
                getBaseContext().getResources().updateConfiguration(configa,
                        getBaseContext().getResources().getDisplayMetrics());

                sharedPreferences.edit().putString(PREF_KEY_CURRENT_LANGUAGE, "am").apply();
                mRadioButton_english.setChecked(false);
                try {
                    Thread.sleep(650);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Intent intent1 = new Intent(this, LanguageActivity.class);
                startActivity(intent1);
                finish();

                onConfigurationChanged(configa);
                break;
        }
    }


    @Override
    protected void setUp() {
        setSupportActionBar(mToolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}