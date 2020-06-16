package tw.pu.csim.tntrenjin.mamaworklist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    int userStar = 0;
    TextView starTextView;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        starTextView = findViewById(R.id.starCount);

        handler = new Handler();
        handler.post(update);
    }


    @Override
    public void onClick(View v) {
        Intent it = new Intent();

        switch (v.getId()) {
            case R.id.startWork:
                it.setClass(this, WorkListActivity.class);
                startActivity(it);
                break;
            case R.id.workList:
            case R.id.gift:
            case R.id.group:
            case R.id.setting:
                Toast.makeText(this, "此功能尚未完成！", Toast.LENGTH_SHORT).show();
                break;

        }

    }

    public void updateStarInfo() {
        userStar = getSharedPreferences("userData", MODE_PRIVATE).getInt("stars", 0);
        starTextView.setText(String.valueOf(userStar));
    }

    Runnable update = new Runnable() {
        @Override
        public void run() {
            updateStarInfo();
            handler.postDelayed(update, 1000);
        }
    };
}
