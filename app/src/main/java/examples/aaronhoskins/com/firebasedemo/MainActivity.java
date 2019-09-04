package examples.aaronhoskins.com.firebasedemo;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private EditText etUserEmail;
    private EditText etUserPassword;
    private TextView tvCurrentUser;
    private FirebaseAuth fireballAuthentication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindViews();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            initPushNotificationChannel();
        }
        fireballAuthentication = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser firebaseUser = fireballAuthentication.getCurrentUser();
        updateUI(firebaseUser);

    }

    //Bind the views
    public void bindViews() {
        etUserEmail = findViewById(R.id.etUserEmail);
        etUserPassword = findViewById(R.id.etUserPassword);
        tvCurrentUser = findViewById(R.id.tvCurrentSignedInUser);
    }

    public void onSignInClicked(View view) {
        final String email = etUserEmail.getText().toString();
        final String password = etUserPassword.getText().toString();
        fireballAuthentication.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithEmail:success");
                            FirebaseUser user = fireballAuthentication.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    public void onCreateAccountClicked(View view) {
        final String email = etUserEmail.getText().toString();
        final String password = etUserPassword.getText().toString();
        //Create account on firebase and sign in
        fireballAuthentication.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "createUserWithEmail:success");
                            FirebaseUser user = fireballAuthentication.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        if(user != null) {
            Log.d("TAG", String.format("%s currently logged In", user.getEmail()));
            //tvCurrentUser.setText(String.format("%s currently logged In", user.getEmail()));
            startActivity(new Intent(this, ProfileActivity.class));
        } else {
            tvCurrentUser.setText("NO USER USER SIGNED IN");
        }
    }

    public void onSignOffClicked(View view) {
        fireballAuthentication.signOut();
        updateUI(fireballAuthentication.getCurrentUser());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initPushNotificationChannel() {
        NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        NotificationChannel channel
                = new NotificationChannel("channelId",
                "messages", NotificationManager.IMPORTANCE_HIGH);
        notificationManager.createNotificationChannel(channel);
    }
}
