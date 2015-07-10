package hwz.com.sharedome.mi;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.xiaomi.mipush.sdk.MiPushClient;

import java.util.ArrayList;
import java.util.List;

import hwz.com.sharedome.R;

/**
 * 1、本demo可以直接运行，设置topic和alias。服务器端使用appsecret即可以向demo发送广播和单点的消息。
 * 2、为了修改本demo为使用你自己的appid，你需要修改几个地方：
 * DemoApplication.java中的APP_ID,APP_KEY，AndroidManifest.xml中的packagename，
 * 和权限permission.MIPUSH_RECEIVE的前缀为你的packagename。
 *
 * @author nico
 */
public class MainActivity extends Activity
{
    //消息集合
    public static List<String> logList = new ArrayList<String>();

    public static MainActivity sMainActivity = null;
    //显示log的消息提示
    public TextView logView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sMainActivity = this;
        logView = (TextView) findViewById(R.id.log);

        // 设置别名
        findViewById(R.id.set_alias).setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                final EditText editText = new EditText(MainActivity.this);
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle(R.string.set_alias)
                        .setView(editText)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener()
                        {

                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                String alias = editText.getText().toString();
                                /**开发者可以为指定用户设置别名，然后给这个别名推送消息，效果等同于给RegId推送消息。
                                 *一个RegId可以被设置多个别名，如果设置的别名已经存在，会覆盖掉之前的别名。
                                 */
                                MiPushClient.setAlias(MainActivity.this, alias, null);

                            }

                        })
                        .setNegativeButton("取消", null)
                        .show();
            }
        });
        // 撤销别名
        findViewById(R.id.unset_alias).setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                final EditText editText = new EditText(MainActivity.this);
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle(R.string.unset_alias)
                        .setView(editText)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener()
                        {

                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                String alias = editText.getText().toString();
                                MiPushClient.unsetAlias(MainActivity.this, alias, null);
                            }

                        })
                        .setNegativeButton("取消", null)
                        .show();
            }
        });
        // 设置帐号
        findViewById(R.id.set_account).setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                final EditText editText = new EditText(MainActivity.this);
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle(R.string.set_account)
                        .setView(editText)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener()
                        {

                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                String account = editText.getText().toString();
                                MiPushClient.setUserAccount(MainActivity.this, account, null);
                            }

                        })
                        .setNegativeButton("取消", null)
                        .show();
            }
        });
        // 撤销帐号
        findViewById(R.id.unset_account).setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                final EditText editText = new EditText(MainActivity.this);
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle(R.string.unset_account)
                        .setView(editText)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener()
                        {

                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                String account = editText.getText().toString();
                                MiPushClient.unsetUserAccount(MainActivity.this, account, null);
                            }

                        })
                        .setNegativeButton("取消", null)
                        .show();
            }
        });
        // 设置标签
        findViewById(R.id.subscribe_topic).setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                final EditText editText = new EditText(MainActivity.this);
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle(R.string.subscribe_topic)
                        .setView(editText)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener()
                        {

                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                String topic = editText.getText().toString();
                                MiPushClient.subscribe(MainActivity.this, topic, null);
                            }

                        })
                        .setNegativeButton("取消", null)
                        .show();
            }
        });
        // 撤销标签
        findViewById(R.id.unsubscribe_topic).setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                final EditText editText = new EditText(MainActivity.this);
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle(R.string.unsubscribe_topic)
                        .setView(editText)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener()
                        {

                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                String topic = editText.getText().toString();
                                MiPushClient.unsubscribe(MainActivity.this, topic, null);
                            }

                        })
                        .setNegativeButton("取消", null)
                        .show();
            }
        });
        // 设置接收消息时间
        findViewById(R.id.set_accept_time).setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {

                new TimeIntervalDialog(MainActivity.this, new TimeIntervalDialog.TimeIntervalInterface()
                {

                    @Override
                    public void apply(int startHour, int startMin, int endHour,
                                      int endMin)
                    {
                        //设置接收MiPush服务推送的时段，不在该时段的推送消息会被缓存起来，到了合适的时段再向app推送原先被缓存的消息。
                        MiPushClient.setAcceptTime(MainActivity.this, startHour, startMin, endHour, endMin, null);
                    }

                    @Override
                    public void cancel()
                    {
                        //ignore
                    }

                })
                        .show();
            }
        });
        // 暂停推送
        findViewById(R.id.pause_push).setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                MiPushClient.pausePush(MainActivity.this, null);
            }
        });
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        refreshLogInfo();
    }

    //更新日志信息
    public void refreshLogInfo()
    {
        String AllLog = "";
        for (String log : logList)
        {
            AllLog = AllLog + log + "\n\n";
        }
        logView.setText(AllLog);
    }
}
