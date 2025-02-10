package com.example.organize;

import android.net.Uri; // Importação adicionada
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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
    private RecyclerView recyclerViewChat;
    private ChatAdapter chatAdapter;
    private ArrayList<String> messages;
    private GeminiManager geminiManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_orga);

        // Referências aos componentes
        chatInput = findViewById(R.id.chat_input);
        Button sendMessageButton = findViewById(R.id.send_message_button);
        recyclerViewChat = findViewById(R.id.chat_recycler_view);
        VideoView videoView = findViewById(R.id.chat_video_view);

        // Inicializando a lista de mensagens e o GeminiManager
        messages = new ArrayList<>();
        geminiManager = new GeminiManager();

        // Configurando o RecyclerView
        chatAdapter = new ChatAdapter(messages);
        recyclerViewChat.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewChat.setAdapter(chatAdapter);

        // Configurando o VideoView
        Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.orga);
        videoView.setVideoURI(videoUri);
        videoView.setOnPreparedListener(mp -> mp.setLooping(true)); // Looping do vídeo
        videoView.start();

        // Clique no botão "Enviar"
        sendMessageButton.setOnClickListener(v -> {
            String message = chatInput.getText().toString().trim();
            if (!message.isEmpty()) {
                // Adiciona a mensagem do usuário na lista e atualiza o RecyclerView
                messages.add("Você: " + message);
                chatAdapter.notifyItemInserted(messages.size() - 1);
                recyclerViewChat.scrollToPosition(messages.size() - 1);
                chatInput.setText("");

                // Envia a mensagem para o Gemini e obtém a resposta
                new Thread(() -> {
                    String orgaResponse = geminiManager.sendMessage(message);
                    new Handler(Looper.getMainLooper()).post(() -> {
                        messages.add("Orga: " + orgaResponse);
                        chatAdapter.notifyItemInserted(messages.size() - 1);
                        recyclerViewChat.scrollToPosition(messages.size() - 1);
                    });
                }).start();
            } else {
                Toast.makeText(ChatOrgaActivity.this, "Por favor, digite uma mensagem.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
