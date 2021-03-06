package com.globsynproject.smartattendancemanager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

/**This is the first activity to be displayed.*/
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
            if (!Settings.System.canWrite(this)) {
                Intent i = new Intent().setAction(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                this.startActivity(i);
            }
        }
        Constant.dataBaseController=new DataBaseController(getApplicationContext());
        findViewById(R.id.teacher).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToTeacher();
            }
        });
        findViewById(R.id.teacher).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Message.toastMessage(getApplicationContext(),"Teacher's Login!","");
                return false;
            }
        });
        findViewById(R.id.student).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToStudent();
            }
        });
        findViewById(R.id.student).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Message.toastMessage(getApplicationContext(),"Student's Login!","");
                return false;
            }
        });
    }

    /**
     * NOTE: This is the method to take the user to the teacher activity.
     */
    private void goToTeacher(){
        Intent intent; Bundle bundle;
        FileController fileController=new FileController(getApplicationContext());
        Constant.NUMBER_STUDENTS=fileController.updateStudent_Number();
        String login=fileController.check_loginFile();
        if(login.equals("teacher logged in")){
            showDialog(Constant.REGISTER_CODE);
            /*if(!ifRegister) {
                DataBaseController dataBaseController = new DataBaseController(getApplicationContext());
                bundle = dataBaseController.getPasswordAndSSID();
                intent = new Intent(MainActivity.this, TeacherActivity.class);
                intent.putExtras(bundle);
                Message.toastMessage(getApplicationContext(), "Teacher logged in!", "");
            }
            else{
                intent=new Intent(MainActivity.this,RegisterActivity.class);
            }*/
        }
        else {
            bundle=new Bundle();
            bundle.putString(Constant.LOGIN_ACCOUNT,"teacher");
            intent=new Intent(MainActivity.this,LoginActivity.class);
            intent.putExtras(bundle);
            Message.toastMessage(getApplicationContext(),"Teacher not Logged in!","");
            startActivity(intent);
        }
    }

    /**
     * NOTE: This is the method to take the user to the Student activity.
     */
    private void goToStudent(){
        Intent intent;Bundle bundle;
        FileController fileController=new FileController(getApplicationContext());
        String login=fileController.check_loginFile();
        if(login.equals("student logged in")){
            String register=fileController.check_RegisterFle();
            Message.logMessages("MAIN: ","Logged in");
            if(register.equals("not registered")) {
                intent = new Intent(MainActivity.this, StudentRegister.class);
                Message.logMessages("MAIN: ","Not Registered!");
                Message.toastMessage(getApplicationContext(),"Student not registered!","");
            }
            else{
                Message.logMessages("MAIN:",register);
                String name=register.substring(0,register.indexOf(' '));//retreivng the data and the password.
                String pass=register.substring(register.indexOf(' ')+1,register.length()-1);
                bundle=new Bundle();
                bundle.putString(Constant.REGISTER_NAME,name);
                bundle.putString(Constant.REGISTER_PASSWORD,pass);
                intent=new Intent(MainActivity.this,StudentActivity.class);
                intent.putExtras(bundle);
                Message.toastMessage(getApplicationContext(),"Student Logged in and already registered","");
            }
        }
        else{
            Message.toastMessage(getApplicationContext(),"Student not logged in!","");
            bundle=new Bundle();
            bundle.putString(Constant.LOGIN_ACCOUNT,"student");
            intent=new Intent(MainActivity.this,LoginActivity.class);
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
        builder.setMessage(R.string.ifRegister);
        builder.setPositiveButton(R.string.PostiveRegister, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent=new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
        builder.setNegativeButton(R.string.NegativeRegister, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //DataBaseController dataBaseController = new DataBaseController(getApplicationContext());
                Bundle bundle = Constant.dataBaseController.getPasswordAndSSID();
                Intent intent = new Intent(MainActivity.this, TeacherActivity.class);
                intent.putExtras(bundle);
                Message.toastMessage(getApplicationContext(), "Teacher logged in!", "");
                startActivity(intent);
                //finish();
            }
        });
        return builder.create();
    }
}
