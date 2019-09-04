package examples.aaronhoskins.com.firebasedemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class ProfileActivity extends AppCompatActivity {
    EditText etName;
    EditText etAge;
    EditText etGender;
    EditText etInterest;
    ArrayList<String> interestList;
    ArrayList<Profile> profiles;
    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("profile");
        profiles = new ArrayList<>();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                HashMap<String, String> recievedProfile = (HashMap)dataSnapshot.getValue();
                profiles.add(new Profile(
                        recievedProfile.get("name"),
                        recievedProfile.get("age"),
                        recievedProfile.get("gender"),
                        null));
                for(Profile p : profiles) {
                    Log.d("PROFILE",
                            String.format("Name: %s , Age: %s , Gender: %s", p.getName(), p.getAge(), p.getGender()));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        bindViews();
    }

    private void bindViews() {
        etAge = findViewById(R.id.etAge);
        etGender = findViewById(R.id.etGender);
        etName = findViewById(R.id.etName);
        etInterest = findViewById(R.id.etInterest);
    }

    public void onInterestClicked(View view) {
        if(!etInterest.getText().toString().isEmpty()) {
            final String interest = etInterest.getText().toString();
            if(interestList == null) {
                interestList = new ArrayList<>();
            }
            interestList.add(interest);
            etInterest.setText("");
        } else {
            Toast.makeText(this, "No interest entered", Toast.LENGTH_LONG).show();
        }
    }

    public void onSaveClicked(View view) {
        final String name = etName.getText().toString();
        final String age = etAge.getText().toString();
        final String gender = etGender.getText().toString();
        if(interestList == null) {
            Toast.makeText(this, "No interest entered to save", Toast.LENGTH_LONG).show();
        } else {
            Profile profile = new Profile(name, age, gender, interestList);
            myRef.setValue(profile);
        }
    }
}
