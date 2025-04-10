package com.example.organize;

import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.VideoView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class ChatOrgaActivity extends AppCompatActivity {

    private EditText chatInput;
    private RecyclerView chatRecyclerView;
    private ChatAdapter chatAdapter;
    private ArrayList<String> messages;
    private GeminiManager geminiManager;
    private DatabaseHelper dbHelper;
    private boolean isFirstInteraction = true;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_orga);

        // Inicializações
        dbHelper = new DatabaseHelper(this);
        geminiManager = new GeminiManager();
        messages = new ArrayList<>();
        userName = dbHelper.getUserName();

        // Configuração da UI
        setupViews();
        setupRecyclerView();
        setupVideoBackground();
    }

    private void setupViews() {
        chatInput = findViewById(R.id.chat_input);
        Button sendButton = findViewById(R.id.send_message_button);
        chatRecyclerView = findViewById(R.id.chat_recycler_view);

        sendButton.setOnClickListener(v -> handleUserMessage());
    }

    private void setupRecyclerView() {
        chatAdapter = new ChatAdapter(messages);
        chatRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        chatRecyclerView.setAdapter(chatAdapter);
    }

    private void setupVideoBackground() {
        VideoView videoView = findViewById(R.id.chat_video_view);
        videoView.setZOrderOnTop(true);
        videoView.getHolder().setFormat(PixelFormat.TRANSPARENT);
        videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.orga));
        videoView.setOnPreparedListener(mp -> mp.setLooping(true));
        videoView.start();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            videoView.setBackground(null);
        }
    }

    private void handleUserMessage() {
        String userMessage = chatInput.getText().toString().trim();
        if (userMessage.isEmpty()) {
            Toast.makeText(this, "Digite sua mensagem", Toast.LENGTH_SHORT).show();
            return;
        }

        // Adiciona mensagem do usuário
        addMessageToChat("Você: " + userMessage);
        chatInput.setText("");

        // Processa resposta do Orga
        processOrgaResponse(userMessage);
    }

    private void addMessageToChat(String message) {
        messages.add(message);
        chatAdapter.notifyItemInserted(messages.size() - 1);
        chatRecyclerView.smoothScrollToPosition(messages.size() - 1);
    }

    private void processOrgaResponse(String userMessage) {
        boolean includeName = isFirstInteraction;
        geminiManager.sendMessage(userMessage, includeName ? userName : null, new GeminiManager.ResponseCallback() {
            @Override
            public void onSuccess(String response) {
                runOnUiThread(() -> {
                    addMessageToChat("Orga: " + response);
                    if (isFirstInteraction) {
                        isFirstInteraction = false;
                    }
                });
            }

            @Override
            public void onError(String error) {
                runOnUiThread(() -> {
                    addMessageToChat("Orga: Ocorreu um erro. Tente novamente.");
                    Toast.makeText(ChatOrgaActivity.this, error, Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dbHelper != null) {
            dbHelper.close();
        }
    }
}
