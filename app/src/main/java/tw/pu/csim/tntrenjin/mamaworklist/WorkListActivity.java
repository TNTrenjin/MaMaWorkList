package tw.pu.csim.tntrenjin.mamaworklist;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class WorkListActivity extends AppCompatActivity implements View.OnClickListener {

    AlertDialog.Builder dialogBuilder;
    AlertDialog alertDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_list);

    }

    private void showAlertDialog(int itemIcon, String itemName, int getStar, String spendTime) {
        dialogBuilder = new AlertDialog.Builder(this);
        View layoutView = getLayoutInflater().inflate(R.layout.check_work, null);

        Button dialogButton = layoutView.findViewById(R.id.OK);
        ImageView itemIconImageView = layoutView.findViewById(R.id.itemIcon);
        TextView itemNameTextView = layoutView.findViewById(R.id.itemName);
        TextView getStarTextView = layoutView.findViewById(R.id.getStar);
        TextView spendTimeTextView = layoutView.findViewById(R.id.spendTime);

        dialogBuilder.setView(layoutView);
        alertDialog = dialogBuilder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();

        // 初始化
        itemIconImageView.setImageResource(itemIcon);
        itemNameTextView.setText(itemName);
        getStarTextView.setText(String.valueOf(getStar));
        spendTimeTextView.setText(spendTime);

        // 事件觸發
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.work_1:
                showAlertDialog(R.drawable.bread, "吐司", 100, "1.5 小時");
                break;
            case R.id.work_2:
                showAlertDialog(R.drawable.bread_1, "麵包", 120, "1 小時");
                break;
            case R.id.work_3:
                showAlertDialog(R.drawable.cookie, "餅乾", 110, "40 分鐘");
                break;
        }
    }
}
