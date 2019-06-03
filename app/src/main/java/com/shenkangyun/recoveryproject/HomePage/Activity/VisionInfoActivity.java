package com.shenkangyun.recoveryproject.HomePage.Activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.shenkangyun.recoveryproject.R;
import com.shenkangyun.recoveryproject.UtilsFolder.FuncUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VisionInfoActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;
    @BindView(R.id.toolBar_title)
    TextView toolBarTitle;
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.tv_scope)
    TextView tvScope;
    @BindView(R.id.tv_policy)
    TextView tvPolicy;
    @BindView(R.id.tv_contacts)
    TextView tvContacts;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.subsidy)
    CardView subsidy;

    private int id;
    private List<Double> X;
    private List<Double> Y;
    private List<String> names;
    private Double[] yAxis = new Double[]{36.200472, 36.201893, 36.190457};
    private Double[] xAxis = new Double[]{117.154624, 117.110542, 117.141156};

    private String policy = "每例补贴1000元";
    private String[] name = new String[]{"泰安市爱尔眼科医院", "泰安市姜玉坤眼镜有限公司", "泰安市中医二院"};
    private String[] tel = new String[]{"8538666", "2187300", "6262029"};
    private String[] scope = new String[]{"白内障筛查、诊断及复明手术", "低视力筛查诊断", "负责视力残疾人的残疾评定以及评定后残疾人精准康复服务。"};
    private String[] contacts = new String[]{"朱燕", "付东升", "孙兆岩"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vision_info);
        ButterKnife.bind(this);
        FuncUtil.iniSystemBar(this, R.color.head_bg);
        setSupportActionBar(toolBar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            toolBarTitle.setText("机构详情");
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        initView();
        initData();
    }

    private void initView() {
        X = new ArrayList<>();
        Y = new ArrayList<>();
        names = new ArrayList<>();
        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
    }

    private void initData() {
        X = Arrays.asList(xAxis);
        Y = Arrays.asList(yAxis);
        names = Arrays.asList(name);
        List<String> tels = Arrays.asList(tel);
        List<String> scopes = Arrays.asList(scope);
        List<String> contacts = Arrays.asList(this.contacts);
        switch (id) {
            case 0:
                tvPhone.setText(tels.get(id));
                tvScope.setText(scopes.get(id));
                tvContacts.setText(contacts.get(id));
                subsidy.setVisibility(View.GONE);
                break;
            case 1:
                tvPhone.setText(tels.get(id));
                tvScope.setText(scopes.get(id));
                tvPolicy.setText(policy);
                tvContacts.setText(contacts.get(id));
                break;
            case 2:
                tvPhone.setText(tels.get(id));
                tvScope.setText(scopes.get(id));
                tvPolicy.setText(policy);
                tvContacts.setText(contacts.get(id));
                break;
            case 3:
                tvPhone.setText(tels.get(id));
                tvScope.setText(scopes.get(id));
                tvPolicy.setText(policy);
                tvContacts.setText(contacts.get(id));
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    @OnClick({R.id.tv_phone, R.id.tv_position})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_phone:
                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // 没有获得授权，申请授权
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                            Manifest.permission.CALL_PHONE)) {
                        Toast.makeText(this, "请授权！", Toast.LENGTH_LONG).show();

                        // 帮跳转到该应用的设置界面，让用户手动授权
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    } else {
                        // 不需要解释为何需要该权限，直接请求授权
                        ActivityCompat.requestPermissions(this,
                                new String[]{Manifest.permission.CALL_PHONE}, MY_PERMISSIONS_REQUEST_CALL_PHONE);
                    }
                } else {
                    // 已经获得授权，可以打电话
                    CallPhone();
                }
                break;
            case R.id.tv_position:
                Intent intent = new Intent(this, PositionInfoActivity.class);
                intent.putExtra("X", X.get(id));
                intent.putExtra("Y", Y.get(id));
                intent.putExtra("name", names.get(id));
                startActivity(intent);
                break;
        }
    }

    private void CallPhone() {
        String callPhone = tvPhone.getText().toString();
        if (TextUtils.isEmpty(callPhone)) {
            Toast.makeText(this, "号码不能为空！", Toast.LENGTH_SHORT).show();
        } else {
            // 拨号：激活系统的拨号组件
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_DIAL);
            Uri data = Uri.parse("tel:" + callPhone);
            intent.setData(data);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    @TargetApi(23)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // TODO Auto-generated method stub
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CALL_PHONE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 授权成功，继续打电话
                    CallPhone();
                } else {
                    // 授权失败！
                    Toast.makeText(this, "授权失败！", Toast.LENGTH_LONG).show();
                }
                break;
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
