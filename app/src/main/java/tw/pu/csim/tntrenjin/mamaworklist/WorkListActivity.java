package tw.pu.csim.tntrenjin.mamaworklist;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class WorkListActivity extends AppCompatActivity implements View.OnClickListener {

    ArrayList<Work> list = new ArrayList<>();
    AlertDialog.Builder dialogBuilder;
    AlertDialog alertDialog;
    Toast toast;
    TextView starTextView;
    int userStar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_list);

        toast = Toast.makeText(this, "此項目尚未更新", Toast.LENGTH_SHORT);

        userStar = getSharedPreferences("userData", MODE_PRIVATE).getInt("stars", 0);
        starTextView = findViewById(R.id.starCount);
        starTextView.setText(String.valueOf(userStar));

        ArrayList<WorkDetail> w1 = new ArrayList<>();
        w1.add(new WorkDetail("準備食材", "高筋麵粉 300g, 速發酵母粉 3g, 糖 6g, 鹽 6g, 鮮奶 220 ml, 橄欖油 10g", 0, R.drawable.work_1_1));
        w1.add(new WorkDetail("", "將糖、鹽和溫度約30度左右的牛奶混合", 0, R.drawable.work_1_2));
        w1.add(new WorkDetail("", "均勻灑下速發乾酵母，靜置1分鐘讓其融解", 0, R.drawable.work_1_3));
        w1.add(new WorkDetail("", "倒入橄欖油，用橡皮刮刀攪拌混合", 0, R.drawable.work_1_4));
        w1.add(new WorkDetail("", "將麵粉倒入，充分攪拌混合至均勻", 0, R.drawable.work_1_5));
        w1.add(new WorkDetail("", "在盆上覆上一層保鮮膜，準備送進烤箱發酵", 0, R.drawable.work_1_7));
        w1.add(new WorkDetail("首次發酵", "烤箱溫度設定30度，將剛拌好的麵糰放入發酵1小時30分鐘", 150, R.drawable.work_1_8));
        w1.add(new WorkDetail("", "用刮刀往內拌壓將空氣擠壓出去，拌成一個緊實的圓形麵糰，請確實排除空氣攪拌至麵糰能整團以刮刀一把鏟起的狀態", 0, R.drawable.work_1_10));
        w1.add(new WorkDetail("二次發酵", "蓋上保鮮膜準備送入烤箱做第二次發酵", 0, R.drawable.work_1_11));
        w1.add(new WorkDetail("", "烤箱溫度設定30度，麵糰放入發酵1小時", 60, R.drawable.work_1_12));
        w1.add(new WorkDetail("", "將麵糰分成兩份，分別放進灑上高筋麵粉的調理盤，要開始塑型", 0, R.drawable.work_1_13));
        w1.add(new WorkDetail("", "整型好的麵糰放在鋪了烘焙墊(或烘焙紙)的烤盤上", 0, R.drawable.work_1_14));
        w1.add(new WorkDetail("", "撕下尺寸可以覆蓋麵團的保鮮膜，內層噴少許油後以油刷刷勻成一層超薄的油膜", 0, R.drawable.work_1_15));
        w1.add(new WorkDetail("最後發酵", "蓋保鮮膜的麵糰送入烤箱，轉30度C、設定30分鐘進行最後發酵", 0, R.drawable.work_1_16));
        w1.add(new WorkDetail("", "保鮮膜撕掉，烤箱轉210度預熱10分鐘", 0, R.drawable.work_1_17));
        w1.add(new WorkDetail("", "在麵團上劃下刀痕", 0, R.drawable.work_1_18));
        w1.add(new WorkDetail("", "以210度C烤25分鐘即可出爐，夾出擺放架上自然冷卻即可裝盤", 0, R.drawable.work_1_19));


        list.add(new Work(R.drawable.bread, "吐司", 100, 150, null));
        list.add(new Work(R.drawable.bread_1, "麵包", 120, 250, w1));
        list.add(new Work(R.drawable.cookie, "餅乾", 110, 80, null));
    }

    private void showAlertDialog(final Work w) {

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
        int h = w.totalSpendTime / 60;
        int m = w.totalSpendTime % 60;

        itemIconImageView.setImageResource(w.icon);
        itemNameTextView.setText(w.workName);
        getStarTextView.setText(String.valueOf(w.getStar));
        spendTimeTextView.setText(String.format("%s%s", h > 0 ? h + " 小時 " : "", (m == 0 && h > 0) ? "" : m + " 分鐘"));

        // 事件觸發
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (w.workList != null) {
                    Intent it = new Intent(WorkListActivity.this, WorkStartActivity.class);
                    it.putExtra("workName", w.workName);
                    it.putExtra("canGetStar", w.getStar);
                    it.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) w.workList);
                    startActivity(it);
                    alertDialog.dismiss();
                    finish();
                } else {
                    toast.show();
                    alertDialog.dismiss();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.work_1:
                showAlertDialog(list.get(0));
                break;
            case R.id.work_2:
                showAlertDialog(list.get(1));
                break;
            case R.id.work_3:
                showAlertDialog(list.get(2));
                break;
        }
    }
}

class Work {
    String workName;
    int icon;
    int getStar;
    int totalSpendTime;
    ArrayList<WorkDetail> workList = new ArrayList<>();

    Work(int icon, String workName, int getStar, int totalSpendTime, ArrayList<WorkDetail> workList) {
        this.icon = icon;
        this.workName = workName;
        this.getStar = getStar;
        this.totalSpendTime = totalSpendTime;
        this.workList = workList;
    }
}

class WorkDetail implements Parcelable {
    String title;
    String content;
    int spendTime;
    int icon;

    WorkDetail(String title, String content, int spendTime, int icon) {
        this.title = title;
        this.content = content;
        this.spendTime = spendTime;
        this.icon = icon;
    }

    protected WorkDetail(Parcel in) {
        title = in.readString();
        content = in.readString();
        spendTime = in.readInt();
        icon = in.readInt();
    }

    public static final Creator<WorkDetail> CREATOR = new Creator<WorkDetail>() {
        @Override
        public WorkDetail createFromParcel(Parcel in) {
            return new WorkDetail(in);
        }

        @Override
        public WorkDetail[] newArray(int size) {
            return new WorkDetail[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(content);
        dest.writeInt(spendTime);
        dest.writeInt(icon);
    }
}
