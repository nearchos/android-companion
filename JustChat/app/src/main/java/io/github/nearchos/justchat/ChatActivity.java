package io.github.nearchos.justchat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Vector;

public class ChatActivity extends AppCompatActivity {

    private EditText editTextYourMessage;

    private DatabaseReference justChatDatabaseReference;

    private final Vector<Message> messages = new Vector<>();
    private final ChatAdapter chatAdapter = new ChatAdapter(messages);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        final RecyclerView recyclerViewChat = findViewById(R.id.recyclerViewChat);
        recyclerViewChat.setAdapter(chatAdapter);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true); // focus on the last item
        recyclerViewChat.setLayoutManager(linearLayoutManager);


        this.editTextYourMessage = findViewById(R.id.editTextYourMessage);
        findViewById(R.id.buttonSend).setOnClickListener(v -> send());

        // initialize firebase RT DB
        final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        justChatDatabaseReference = firebaseDatabase.getReference().child("just-chat");
        final Query recentChatsQuery = justChatDatabaseReference.limitToLast(10);
        recentChatsQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                final Message message = snapshot.getValue(Message.class);
                messages.add(message);
                chatAdapter.notifyDataSetChanged();
                recyclerViewChat.scrollToPosition(messages.size() - 1);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                /* placeholder - not handled */
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                /* placeholder - not handled */
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                /* placeholder - not handled */
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                /* placeholder - not handled */
            }
        });
    }

    private String alias;

    @Override
    protected void onResume() {
        super.onResume();
        this.alias = getIntent().getStringExtra("alias");
        this.chatAdapter.setAlias(alias);
    }

    private void send() {
        final String yourMessage = editTextYourMessage.getText().toString();
        if(!yourMessage.trim().isEmpty()) {
            final long timestamp = System.currentTimeMillis();
            final Message message = new Message(timestamp, alias, yourMessage);
            justChatDatabaseReference.child(Long.toString(timestamp)).setValue(message);
        }
        editTextYourMessage.setText("");
    }
}