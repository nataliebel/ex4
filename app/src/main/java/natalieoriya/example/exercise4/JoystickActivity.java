package natalieoriya.example.exercise4;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class JoystickActivity extends AppCompatActivity implements JoystickView.JoystickListener {

    private JoystickView ja;
    private float x;
    private float y;
    private Client client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        JoystickView joystick = new JoystickView(this);
        setContentView(R.layout.activity_joystick);
        client=Client.getInstance();
        x=0;
        y=0;
    }

    @Override
    public void onJoystickMoved(float xPercent, float yPercent, int id) {
        Log.d("joystick", "X percent: " + xPercent + " Y percent: " + yPercent);
        //pushed right
        if(xPercent!=x){
            client.send("set /controls/flight/aileron " + Float.toString(xPercent) + "\r\n");
            x=xPercent;
        }
         if(yPercent!=y){
            client.send("set /controls/flight/elevator " + Double.toString(yPercent) + "\r\n");
            y=yPercent;
        }
    }
}
