package com.example.kazimanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Add_new_details extends AppCompatActivity{
    String id;
    Button btn_edit,btn_cancel,btn_add,getBtn_cancel_update,btn_delete;
    boolean state=false;
    String Docid;
    long marriagedate;
    MarriageModel model;
    EditText inputs[]=new EditText[20];
    int[] ids = new int[]{
            R.id.new_date
            ,R.id.new_serial
            ,R.id.new_bookno
            ,R.id.new_groom
            ,R.id.new_bride
            ,R.id.new_mobile
            ,R.id.new_address
            ,R.id.new_reference
            ,R.id.new_notes
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        initialize();
    }

    void initialize(){
        btn_edit=findViewById(R.id.new_btn_edit);
        btn_cancel=findViewById(R.id.new_btn_cancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_add=findViewById(R.id.new_btn_add);
        btn_delete=findViewById(R.id.new_btn_delete);
        getBtn_cancel_update=findViewById(R.id.new_btn_cancel_update);
        findviewws();
        model= (MarriageModel) getIntent().getSerializableExtra("serializableextra");
        if(model!=null){
            btn_add.setVisibility(View.GONE);
            btn_delete.setVisibility(View.VISIBLE);
            btn_cancel.setVisibility(View.GONE);
            state=true;
            inputs[0].setText(gettime2(model.getDate()));
            inputs[1].setText(model.getSeriaNo());
            inputs[2].setText(model.getBookNo());
            inputs[3].setText(model.getGroomName());
            inputs[4].setText(model.getBrideName());
            inputs[5].setText(model.getMobileNo());
            inputs[6].setText(model.getAddress());
            inputs[7].setText(model.getReference());
            inputs[8].setText(model.getNotes());
            Docid=model.getIdNo();
        }else{
            btn_edit.setVisibility(View.GONE);
            getBtn_cancel_update.setVisibility(View.GONE);
        }
        setenable();
        getBtn_cancel_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setenable();
                getBtn_cancel_update.setVisibility(View.GONE);
            }
        });
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(
                        Add_new_details.this).create();

                // Setting Dialog Title
                alertDialog.setTitle("Warning");
                // Setting Dialog Message
                alertDialog.setMessage("Are you sure want to delete this entry ??");
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes,Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, int which) {
                        dialog.dismiss();
                        FirebaseFirestore.getInstance().collection(UserUtils.getuseremail()).document(Docid)
                                .delete();
                        Toast.makeText(Add_new_details.this,"Succesfull",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No,go back", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialog.show();

            }
        });
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!state)
                {
                    setenable();
                    getBtn_cancel_update.setVisibility(View.VISIBLE);
                }
                else{
                    for(int i=0;i<8;i++) {
                        if (inputs[i].getText() == null || inputs[i].getText().toString().isEmpty()) {
                            Toast.makeText(Add_new_details.this, "Fillup all field", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    model=new MarriageModel(inputs[1].getText().toString().trim(),inputs[2].getText().toString().trim(),inputs[3].getText().toString().trim(),inputs[4].getText().toString().trim(),
                            inputs[6].getText().toString().trim(),inputs[7].getText().toString().trim(),inputs[5].getText().toString().trim(),inputs[8].getText().toString().trim(),0000);
                    model.setDate(marriagedate);
                    AlertDialog.Builder builder = new AlertDialog.Builder(Add_new_details.this);
                    View view=new ProgressBar(Add_new_details.this);
                    builder.setView(view);
                    final Dialog dialog = builder.create();
                    dialog.setCancelable(false);
                    dialog.show();
                    FirebaseFirestore.getInstance().collection(UserUtils.getuseremail()).document(Docid).set(model, SetOptions.merge())
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(Add_new_details.this,"Succesfull",Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                    setenable();
                                    btn_edit.setText("Edit");
                                    getBtn_cancel_update.setVisibility(View.GONE);
                                }
                            });


                }
            }
        });
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i=0;i<8;i++) {
                    if (inputs[i].getText() == null || inputs[i].getText().toString().isEmpty()) {
                        Toast.makeText(Add_new_details.this, "Fillup all field", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                model=new MarriageModel(inputs[1].getText().toString().trim(),inputs[2].getText().toString().trim(),inputs[3].getText().toString().trim(),inputs[4].getText().toString().trim(),
                        inputs[6].getText().toString().trim(),inputs[7].getText().toString().trim(),inputs[5].getText().toString().trim(),inputs[8].getText().toString().trim(),0000);
                model.setDate(marriagedate);
                AlertDialog.Builder builder = new AlertDialog.Builder(Add_new_details.this);
                //View view = getLayoutInflater().inflate(R.layout.progress);
                View view=new ProgressBar(Add_new_details.this);
                builder.setView(view);
                final Dialog dialog = builder.create();
                dialog.show();
                FirebaseFirestore.getInstance().collection(UserUtils.getuseremail()).add(model).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(Add_new_details.this,"Succesfull",Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        AlertDialog alertDialog = new AlertDialog.Builder(
                                Add_new_details.this).create();

                        // Setting Dialog Title
                        alertDialog.setTitle("Succesful");

                        // Setting Dialog Message
                        alertDialog.setMessage("Do you want to add more ?");
                        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes,continue", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                cleafield();
                            }
                        });
                        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No,go back", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                finish();

                            }
                        });




                        // Showing Alert Message
                        alertDialog.show();

                    }
                });

            }
        });
        inputs[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!state)return;
                final Calendar newCalendar = Calendar.getInstance();
                DatePickerDialog dialog=new DatePickerDialog(Add_new_details.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Calendar abc = Calendar.getInstance();
                        abc.set(year, month, dayOfMonth);
                        long startTime = abc.getTimeInMillis();
                        inputs[0].setText(gettime2(startTime));
                        marriagedate=startTime;
                        inputs[1].requestFocus();
                    }
                },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
                dialog.show();
            }
        });

    }

    private void cleafield() {
        for(int i=0;i<9;i++)inputs[i].setText("");
    }
    void findviewws(){
        int i=0;
        for(int id : ids) {
            inputs[i++]=findViewById(id);
        }
    }
    private void setenable() {

        for(int i=1;i<9;i++) {
            EditText t = inputs[i];
            if(state)t.setBackground(null);
            else t.setBackgroundResource(R.drawable.edittextbg);
            if(state)
                t.setInputType(InputType.TYPE_NULL);
            else if(i==8 || i==6)
            {
                t.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
                t.setSingleLine(false);
            }
            else  t.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
        }
        if(state)hideKeyboard(this,false);
        else {
            findViewById(R.id.new_date).requestFocus();hideKeyboard(this,false);
        }
        if(!state && Docid!=null){
            btn_edit.setText("Update");
            getBtn_cancel_update.setVisibility(View.VISIBLE);
        }else if( Docid!=null){
            btn_edit.setText("Edit");
            getBtn_cancel_update.setVisibility(View.GONE);
        }
        state=!state;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public static void hideKeyboard(Activity activity,boolean show) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }

        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        //  else
        //   imm.showSoftInput(view,0);
        //imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }
    public static String gettime2(long time){
        try {
            return new SimpleDateFormat("dd/MM/yyyy").format(new Date(time));
        }catch (Exception e){
            return "invalid";
        }
    }
}
