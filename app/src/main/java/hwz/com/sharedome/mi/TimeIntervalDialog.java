package hwz.com.sharedome.mi;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;

import hwz.com.sharedome.R;

public class TimeIntervalDialog extends Dialog implements OnTimeChangedListener
{
    //设置时间开启或取消的接口
    private TimeIntervalInterface timeIntervalInterface;
    //上下文
    private Context mContext;
    //开始时间  /  结束时间
    private TimePicker startTimePicker, endTimePicker;
    //确定按钮  /  取消按钮
    private Button applyBtn, cancelBtn;
    //开始的 时:分    /    结束的 时:分
    private int startHour, startMinute, endHour, endMinute;

    public TimeIntervalDialog(Context context, TimeIntervalInterface timeIntervalInterface, int startHour, int startMinute, int endHour, int endMinute)
    {
        super(context);
        mContext = context;
        this.timeIntervalInterface = timeIntervalInterface;
        this.startHour = startHour;
        this.startMinute = startMinute;
        this.endHour = endHour;
        this.endMinute = endMinute;
    }

    public TimeIntervalDialog(Context context, TimeIntervalInterface timeIntervalInterface)
    {
        this(context, timeIntervalInterface, 0, 0, 23, 59);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_time_dialog);
        setCancelable(true);
        setTitle(mContext.getString(R.string.set_accept_time));
        //设置开始时间
        startTimePicker = (TimePicker) findViewById(R.id.startTimePicker);
        startTimePicker.setIs24HourView(true);
        startTimePicker.setCurrentHour(startHour);
        startTimePicker.setCurrentMinute(startMinute);
        startTimePicker.setOnTimeChangedListener(this);
        //设置结束时间
        endTimePicker = (TimePicker) findViewById(R.id.endTimePicker);
        endTimePicker.setIs24HourView(true);
        endTimePicker.setCurrentHour(endHour);
        endTimePicker.setCurrentMinute(endMinute);
        endTimePicker.setOnTimeChangedListener(this);
        //确定
        applyBtn = (Button) findViewById(R.id.apply);
        applyBtn.setOnClickListener(clickListener);
        //取消
        cancelBtn = (Button) findViewById(R.id.cancel);
        cancelBtn.setOnClickListener(clickListener);
    }

    /**
     * 实现确定/取消按钮的监听
     */
    private Button.OnClickListener clickListener = new Button.OnClickListener()
    {

        @Override
        public void onClick(View v)
        {
            switch (v.getId())
            {
                //设置时间
                case R.id.apply:
                    dismiss();
                    timeIntervalInterface.apply(startHour, startMinute, endHour, endMinute);
                    break;
                //取消
                case R.id.cancel:
                    dismiss();
                    timeIntervalInterface.cancel();
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 设置时间改变的方法
     * @param view  开始或结束时间
     * @param hourOfDay
     * @param minute
     */
    @Override
    public void onTimeChanged(TimePicker view, int hourOfDay, int minute)
    {
        if (view == startTimePicker)
        {
            startHour = hourOfDay;
            startMinute = minute;
        }
        else if (view == endTimePicker)
        {
            endHour = hourOfDay;
            endMinute = minute;
        }
    }

    /**
     * 设置或取消接受通知的接口
     */
    static interface TimeIntervalInterface
    {
        public void apply(int startHour, int startMin, int endHour, int endMin);

        public void cancel();
    }
}
