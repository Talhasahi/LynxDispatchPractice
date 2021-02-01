package com.example.lynxdispatch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import static java.lang.Boolean.TRUE;

public class ChatActivity extends AppCompatActivity {

    private String chatpersonname;
    private ImageView sendButton, attachFile;
    private EditText messageBox;
    private Message msglist;
    private singlten_messages adp;
    private ListView listView;
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        inialization();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = messageBox.getText().toString();
                if (message.length() > 0) {
                    msglist = new Message(message, TRUE);
                    adp = new singlten_messages(ChatActivity.this);
                    adp.add(msglist);
                    listView.setAdapter(adp);
                    messageBox.getText().clear();
                } else {
                    Snackbar snackbar = Snackbar
                            .make(v, "Write Text Please!", Snackbar.LENGTH_SHORT).setAction("Try Again!", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                }
                            });
                    snackbar.show();
                }
            }
        });

        attachFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ChatActivity.this, "File Attach", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void inialization() {
        sendButton = findViewById(R.id.messenger_message_send_button);
        attachFile = findViewById(R.id.messenger_attach_file_button);
        messageBox = findViewById(R.id.messageBox_dispatcher);
        listView = findViewById(R.id.messenger_listview);
        backButton = findViewById(R.id.backButton_chat_activity);
        Intent intent = getIntent();
        chatpersonname = intent.getStringExtra("ChatPersonName");
        Toast.makeText(this, "Start Chating With " + chatpersonname, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }
}