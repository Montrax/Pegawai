package com.example.lenovo.daftarkaryawan;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;



public class InputActivity extends AppCompatActivity {

    //Declaration EditTexts
    EditText editTextUserName;
    EditText editTextAlamat;
    EditText editTextJK;

    //Declaration TextInputLayout
    TextInputLayout textInputLayoutUserName;
    TextInputLayout textInputLayoutAlamat;
    TextInputLayout textInputLayoutJK;

    //Declaration Button
    Button buttonInput;

    //Declaration SqliteHelper
    SqliteHelper sqliteHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
        sqliteHelper = new SqliteHelper(this);
        initTextViewLogin();
        initViews();
        buttonInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    String UserName = editTextUserName.getText().toString();
                    String Alamat = editTextAlamat.getText().toString();
                    String JK = editTextJK.getText().toString();

                    if (!sqliteHelper.isUserNameExists(UserName)) {


                        sqliteHelper.addUser(new User(null, UserName, Alamat, JK));
                        Snackbar.make(buttonInput, "Sukses tambah user", Snackbar.LENGTH_LONG).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                finish();
                            }
                        }, Snackbar.LENGTH_LONG);
                    }else {

                        //Email exists with email input provided so show error user already exist
                        Snackbar.make(buttonInput, "User dengan nama yang sama sudah ada ", Snackbar.LENGTH_LONG).show();
                    }


                }
            }
        });
    }

    //this method used to set Login TextView click event
    private void initTextViewLogin() {
        TextView textViewLogin = (TextView) findViewById(R.id.textViewLogin);
        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    //this method is used to connect XML views to its Objects
    private void initViews() {
        editTextAlamat = (EditText) findViewById(R.id.editTextAlamat);
        editTextJK = (EditText) findViewById(R.id.editTextJK);
        editTextUserName = (EditText) findViewById(R.id.editTextUserName);
        textInputLayoutAlamat = (TextInputLayout) findViewById(R.id.textInputLayoutAlamat);
        textInputLayoutJK = (TextInputLayout) findViewById(R.id.textInputLayoutJK);
        textInputLayoutUserName = (TextInputLayout) findViewById(R.id.textInputLayoutUserName);
        buttonInput = (Button) findViewById(R.id.buttonInput);

    }

    //This method is used to validate input given by user
    public boolean validate() {
        boolean valid = false;

        //Get values from EditText fields
        String UserName = editTextUserName.getText().toString();
        String ALamat = editTextAlamat.getText().toString();
        String JK = editTextJK.getText().toString();

        //Handling validation for UserName field
        if (UserName.isEmpty()) {
            valid = false;
            textInputLayoutUserName.setError("Masukan Nama User yang terdaftar");
        } else {
            if (UserName.length() > 5) {
                valid = true;
                textInputLayoutUserName.setError(null);
            } else {
                valid = false;
                textInputLayoutUserName.setError("Nama user terlalu pendek");
            }
        }


        return valid;
    }
}