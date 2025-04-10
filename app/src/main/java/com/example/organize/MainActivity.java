package com.example.organize;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private ProgressBar progressImc;
    private TextView txtImcValue, txtImcCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        setupUserInfo(dbHelper);
        setupCurrentDate();
        setupButtons();
        setupIMCCard();
        setupOrgaAnimation();
    }

    @Override
    protected void onResume() {
        super.onResume();
        atualizarCardIMC();
    }

    private void setupUserInfo(DatabaseHelper dbHelper) {
        TextView welcomeText = findViewById(R.id.welcome_message);
        welcomeText.setText("Olá, " + dbHelper.getUserName() + "!");
    }

    private void setupCurrentDate() {
        TextView txtInfoCurrentDate = findViewById(R.id.current_date);
        String currentDateFormatted = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                .format(new Date());
        txtInfoCurrentDate.setText(String.format("Hoje: %s", currentDateFormatted));
    }

    private void setupButtons() {
        findViewById(R.id.add_reminder_button).setOnClickListener(v ->
                startActivity(new Intent(this, AdicionarLembrete.class)));

        findViewById(R.id.add_task_button).setOnClickListener(v ->
                startActivity(new Intent(this, AddTaskActivity.class)));

        findViewById(R.id.chat_with_orga_button).setOnClickListener(v ->
                startActivity(new Intent(this, ChatOrgaActivity.class)));

        findViewById(R.id.add_habit_button).setOnClickListener(v ->
                startActivity(new Intent(this, AdicionarHabitoActivity.class)));

        findViewById(R.id.menu_button).setOnClickListener(this::showPopupMenu);
    }

    private void setupIMCCard() {
        progressImc = findViewById(R.id.progress_imc);
        txtImcValue = findViewById(R.id.txt_imc_value);
        txtImcCategory = findViewById(R.id.txt_imc_category);
    }

    private void setupOrgaAnimation() {
        ImageView orgaImage = findViewById(R.id.orga_image);
        Animation floatAnimation = AnimationUtils.loadAnimation(this, R.anim.flutuar);
        orgaImage.startAnimation(floatAnimation);
    }

    private void atualizarCardIMC() {
        OrgaAI orgaAI = new OrgaAI(this);
        double imc = orgaAI.calcularValorIMC();

        if (imc > 0) {
            txtImcValue.setText(String.format(Locale.getDefault(), "%.1f", imc));
            txtImcCategory.setText(getCategoriaIMC(imc));
            txtImcCategory.setTextColor(getCorCategoriaIMC(imc));
            progressImc.setProgress((int) Math.max(15, Math.min(imc, 40)));
        } else {
            txtImcValue.setText("--");
            txtImcCategory.setText("Complete seus dados nas Configurações");
            txtImcCategory.setTextColor(ContextCompat.getColor(this, R.color.black));
        }
    }

    private String getCategoriaIMC(double imc) {
        if (imc < 18.5) return "Abaixo do peso";
        if (imc < 24.9) return "Peso saudável";
        if (imc < 29.9) return "Sobrepeso";
        return "Obesidade";
    }

    private int getCorCategoriaIMC(double imc) {
        if (imc < 18.5)
            return ContextCompat.getColor(this, R.color.material_dynamic_secondary30);
        if (imc < 24.9)
            return ContextCompat.getColor(this, R.color.material_dynamic_primary50);
        if (imc < 29.9)
            return ContextCompat.getColor(this, R.color.material_dynamic_tertiary50);
        return ContextCompat.getColor(this, R.color.material_dynamic_error50);
    }

    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.getMenuInflater().inflate(R.menu.menu_main, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_tarefas) {
                startActivity(new Intent(this, ListaTarefasActivity.class));
                return true;

            } else if (itemId == R.id.nav_lembretes) {
                startActivity(new Intent(this, ListaLembretesActivity.class));
                return true;

            } else if (itemId == R.id.nav_habitos) {
                startActivity(new Intent(this, ListaHabitosActivity.class));  // Corrigido para ListaHabitosActivity
                return true;
            }
            return false;
        });

        popupMenu.show();  // Movido para fora do listener
    }
}
