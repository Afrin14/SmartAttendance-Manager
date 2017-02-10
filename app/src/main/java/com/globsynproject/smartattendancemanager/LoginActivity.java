package com.globsynproject.smartattendancemanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {
    Button signup;
    String account;
    FileController fileController;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        signup=(Button) findViewById(R.id.signup);
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        account=bundle.getString(Constant.LOGIN_ACCOUNT);
        signup.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Message.toastMessage(getApplicationContext(),"Enter the details!","");
                return false;
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fileController.create_loginFile(account);
                Message.toastMessage(getApplicationContext(),"Logged in successfully!","");
                goToActivity();
            }
        });
    }
    private void goToActivity(){
        if(account.equals("teacher")){
            intent=new Intent(LoginActivity.this,TeacherActivity.class);
        }
        else if(account.equals("student")){
            intent=new Intent(LoginActivity.this,StudentActivity.class);
        }
        startActivity(intent);
        finish();
    }
}
