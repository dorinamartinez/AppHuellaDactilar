package com.example.apphuelladactilar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import androidx.biometric.BiometricPrompt;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity {

    private TextView tvAuth;
    private Button btnAuth;

    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private androidx.biometric.BiometricPrompt.PromptInfo promptInfo;

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvAuth = findViewById(R.id.tvAuth);
        btnAuth = findViewById(R.id.btnAuth);

        executor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt(MainActivity.this,executor, new BiometricPrompt.AuthenticationCallback(){
            @Override
            public void onAuthenticationError(int errorCode, CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                tvAuth.setText("Error de autenticación");
                Toast.makeText(MainActivity.this, "Error de autenticacion" + errString, Toast.LENGTH_SHORT).show();
            }

            /*@Override
            public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
                super.onAuthenticationHelp(helpCode, helpString);
                tvAuth.setText("Ingresa huella");
                Toast.makeText(MainActivity.this, "Ingrese huella", Toast.LENGTH_SHORT).show();
            }*/

            @Override
            public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                tvAuth.setText("Autenticasion exitosa");
                Toast.makeText(MainActivity.this, "Autenticacion exotosa", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                tvAuth.setText("Autenticacion fallida");
                Toast.makeText(MainActivity.this, "Autenticacion fallida", Toast.LENGTH_SHORT).show();
            }

        });

        promptInfo = new androidx.biometric.BiometricPrompt.PromptInfo.Builder()
                .setTitle("Autenticacion con huella dactilar")
                .setSubtitle("Iniciar sesión usab¿ndo huella")
                .setDescription("Coloca tu huella sobre el sensor del dispositivo")
                .setNegativeButtonText("Usar contraseña")
                .build();

        btnAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                biometricPrompt.authenticate(promptInfo);
            }
        });
    }
}