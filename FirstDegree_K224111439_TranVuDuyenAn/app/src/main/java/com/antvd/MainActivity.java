package com.antvd;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    EditText edtA, edtB;
    TextView tvKetQua, tvEquation;
    Button btnGiai, btnNext, btnExit;
    Button btnLangVi, btnLangEn, btnLangFr, btnLangEs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        loadLocale(); // Load ngôn ngữ trước
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtA = findViewById(R.id.edtA);
        edtB = findViewById(R.id.edtB);
        tvKetQua = findViewById(R.id.tvKetQua);
        tvEquation = findViewById(R.id.tvEquation);
        btnGiai = findViewById(R.id.btnSolution);
        btnNext = findViewById(R.id.btnNext);
        btnExit = findViewById(R.id.btnExit);

        btnLangVi = findViewById(R.id.btnLangVi);
        btnLangEn = findViewById(R.id.btnLangEn);
        btnLangFr = findViewById(R.id.btnLangFr);
        btnLangEs = findViewById(R.id.btnLangEs);

        // Set màu nút
        btnGiai.setBackgroundColor(ContextCompat.getColor(this, android.R.color.holo_green_light));
        btnNext.setBackgroundColor(ContextCompat.getColor(this, android.R.color.holo_orange_light));
        btnExit.setBackgroundColor(ContextCompat.getColor(this, android.R.color.holo_red_light));

        // Xử lý sự kiện
        btnGiai.setOnClickListener(v -> doSolution());
        btnNext.setOnClickListener(v -> doNext());
        btnExit.setOnClickListener(v -> doExit());

        btnLangVi.setOnClickListener(v -> changeLanguage("vi"));
        btnLangEn.setOnClickListener(v -> changeLanguage("en"));
        btnLangFr.setOnClickListener(v -> changeLanguage("fr"));
        btnLangEs.setOnClickListener(v -> changeLanguage("es"));

        // Cập nhật phương trình khi người dùng nhập
        TextWatcher watcher = new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateEquation();
            }
            @Override public void afterTextChanged(Editable s) { }
        };

        edtA.addTextChangedListener(watcher);
        edtB.addTextChangedListener(watcher);

        updateEquation(); // Hiển thị phương trình lúc đầu
    }

    public void doSolution() {
        try {
            double a = Double.parseDouble(edtA.getText().toString());
            double b = Double.parseDouble(edtB.getText().toString());

            if (a == 0) {
                tvKetQua.setText(b == 0 ? getString(R.string.infinite) : getString(R.string.no_solution));
            } else {
                double x = -b / a;
                tvKetQua.setText(getString(R.string.result) + " x = " + x);
            }
        } catch (NumberFormatException e) {
            tvKetQua.setText(getString(R.string.invalid_input));
        }
    }

    public void doNext() {
        edtA.setText("");
        edtB.setText("");
        tvKetQua.setText(getString(R.string.result));
        updateEquation();
    }

    public void doExit() {
        finishAffinity();
    }

    private void updateEquation() {
        String strA = edtA.getText().toString();
        String strB = edtB.getText().toString();

        String formatted = "";

        if (!strA.isEmpty() || !strB.isEmpty()) {
            double a = strA.isEmpty() ? 0 : Double.parseDouble(strA);
            double b = strB.isEmpty() ? 0 : Double.parseDouble(strB);

            String sign = b >= 0 ? " + " : " - ";
            double absB = Math.abs(b);

            formatted = a + "x" + sign + absB + " = 0";
        } else {
            formatted = getString(R.string.default_equation); // Ex: "ax + b = 0"
        }

        tvEquation.setText(formatted);
    }

    private void changeLanguage(String langCode) {
        SharedPreferences prefs = getSharedPreferences("lang", MODE_PRIVATE);
        String currentLang = prefs.getString("lang_code", "vi");

        if (!currentLang.equals(langCode)) {
            setLocale(langCode);
            recreate();
        }
    }

    private void setLocale(String langCode) {
        Locale locale = new Locale(langCode);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);

        getResources().updateConfiguration(config, getResources().getDisplayMetrics());

        SharedPreferences.Editor editor = getSharedPreferences("lang", MODE_PRIVATE).edit();
        editor.putString("lang_code", langCode);
        editor.apply();
    }

    private void loadLocale() {
        SharedPreferences prefs = getSharedPreferences("lang", MODE_PRIVATE);
        String langCode = prefs.getString("lang_code", "vi");
        setLocale(langCode);
    }
}
