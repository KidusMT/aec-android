package com.aait.aec.ui.settings;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.aait.aec.R;
import com.aait.aec.ui.base.BaseActivity;
import com.aait.aec.ui.main.MainActivity;
import com.aait.aec.utils.AppConstants;
import com.aait.aec.utils.LocaleManager;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.aait.aec.utils.AppConstants.PREF_KEY_CURRENT_LANGUAGE;
import static com.aait.aec.utils.LocaleManager.LANGUAGE_AMHARIC;
import static com.aait.aec.utils.LocaleManager.LANGUAGE_ENGLISH;

public class LanguageActivity extends BaseActivity implements View.OnClickListener {

    RadioButton mRadioButton_amharic;
    RadioButton mRadioButton_english;

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

        ButterKnife.bind(this);

        setUp();

        if (LocaleManager.getLanguage(this).equals(LANGUAGE_ENGLISH)) {
            mRadioButton_english.setChecked(true);
            mRadioButton_amharic.setChecked(false);
        } else {
            mRadioButton_english.setChecked(false);
            mRadioButton_amharic.setChecked(true);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.check_english://==> ENGLISH
            case R.id.check_english_container:
//                Locale locale = new Locale(LANGUAGE_ENGLISH);
//                Locale.setDefault(locale);
//                Configuration config = new Configuration();
//                config.locale = locale;
//                getBaseContext().getResources().updateConfiguration(config,
//                        getBaseContext().getResources().getDisplayMetrics());
//
//                sharedPreferences.edit().putString(PREF_KEY_CURRENT_LANGUAGE, LANGUAGE_ENGLISH).apply();
                mRadioButton_amharic.setChecked(false);

                setNewLocale(LANGUAGE_ENGLISH);

//                onConfigurationChanged(config);
                break;
            case R.id.check_amharic://==> AMHARIC
            case R.id.check_amharic_container:
//                Locale localea = new Locale(LANGUAGE_AMHARIC);
//                Locale.setDefault(localea);
//                Configuration configa = new Configuration();
//                configa.locale = localea;
//                getBaseContext().getResources().updateConfiguration(configa,
//                        getBaseContext().getResources().getDisplayMetrics());

//                sharedPreferences.edit().putString(PREF_KEY_CURRENT_LANGUAGE, LANGUAGE_AMHARIC).apply();
                mRadioButton_english.setChecked(false);

                setNewLocale(LANGUAGE_AMHARIC);

//                onConfigurationChanged(configa);
                break;
            default:
                setNewLocale(LANGUAGE_ENGLISH);// default language - english
                break;
        }
    }

    private void setNewLocale(String language) {
        LocaleManager.setNewLocale(this, language);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.application_going_to_restart)
                .setPositiveButton(getString(R.string.btn_restart), (dialog, id) -> {
                    restartApplication(true);
                })
                .setNegativeButton(getString(R.string.btn_later), (dialog, id) -> {
                    dialog.dismiss();
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void restartApplication(boolean restartProcess) {
        startActivity(MainActivity.getStartIntent(LanguageActivity.this)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));

        if (restartProcess) {
            System.exit(0);
        } else {
            Toast.makeText(this, "Activity restarted", Toast.LENGTH_SHORT).show();
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
