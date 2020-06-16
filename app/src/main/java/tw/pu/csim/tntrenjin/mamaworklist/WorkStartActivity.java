package tw.pu.csim.tntrenjin.mamaworklist;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class WorkStartActivity extends AppCompatActivity implements View.OnClickListener {

    Intent it;
    ImageView img;
    TextView stepTextView;
    TextView titleTextView;
    TextView contentTextView;
    Button nextBtn;
    ArrayList<WorkDetail> workDetail;
    AlertDialog.Builder dialogBuilder;
    AlertDialog alertDialog;
    TextView starTextView;

    int nowStep;
    int userStar;
    int canGetStar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_start);

        it = getIntent();
        workDetail = it.getParcelableArrayListExtra("list");
        canGetStar = it.getIntExtra("canGetStar", 0);

        stepTextView = findViewById(R.id.step);
        img = findViewById(R.id.img);
        titleTextView = findViewById(R.id.title);
        contentTextView = findViewById(R.id.content);
        nextBtn = findViewById(R.id.next);

        userStar = getSharedPreferences("userData", MODE_PRIVATE).getInt("stars", 0);
        starTextView = findViewById(R.id.starCount);
        starTextView.setText(String.valueOf(userStar));

        nowStep = 0;

        update();
    }

    public void update() {
        stepTextView.setText(String.format("步驟 %s", nowStep + 1));
        titleTextView.setText(workDetail.get(nowStep).title);
        contentTextView.setText(workDetail.get(nowStep).content);
        img.setImageResource(workDetail.get(nowStep).icon);

        if (nowStep == workDetail.size() - 1)
            nextBtn.setText("完成");
        else
            nextBtn.setText("下一步");

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void showAlertDialog() {

        dialogBuilder = new AlertDialog.Builder(this);
        View layoutView = getLayoutInflater().inflate(R.layout.finish_work, null);

        Button dialogButton = layoutView.findViewById(R.id.understand);

        dialogBuilder.setView(layoutView);
        alertDialog = dialogBuilder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();


        // 事件觸發
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                dialogOnDismiss();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.next) {
            if ((nowStep + 1) < workDetail.size()) {
                nowStep++;
                update();
            } else {
                showAlertDialog();
                SharedPreferences pref = getSharedPreferences("userData", MODE_PRIVATE);
                pref.edit().putInt("stars", userStar + canGetStar).apply();
            }
        }
    }

    public void dialogOnDismiss() {
        SharedPreferences pref = getSharedPreferences("userData", MODE_PRIVATE);
        pref.edit().putInt("stars", userStar + canGetStar).apply();
        finish();
    }

}
