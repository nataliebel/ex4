package natalieoriya.example.exercise4;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private Client client;

    EditText ipText;
    EditText portText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        client = Client.getInstance();
        initializeComp();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        client.closeSocket();
    }

    public void initializeComp() {
        ipText = (EditText) findViewById(R.id.ipText);
        portText = (EditText) findViewById(R.id.portText);
    }

    public void connect(View view) {
        if (!ipText.getText().toString().equals("")) {
            client.setIp(ipText.getText().toString());
        }
        if (!portText.getText().toString().equals("")) {
            client.setPort(Integer.parseInt(portText.getText().toString()));
        }
        Thread thread = new Thread(null, client, "connect", 0);
        thread.start();
        Intent intent = new Intent(this, JoystickActivity.class);
        startActivity(intent);
    }

}
