package com.ch.myframe.ui.activity.login;

import android.Manifest;
import android.content.Intent;
import androidx.annotation.NonNull;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.ch.myframe.R;
import com.ch.myframe.base.BaseMvpActivity;
import com.ch.myframe.bean.User;
import com.ch.myframe.ui.activity.MainActivity;
import com.ch.myframe.utils.MySharedPreferences;
import com.ch.myframe.utils.StaticVariable;

import butterknife.BindView;
import butterknife.OnClick;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

/**
 * @Author: Administrator
 * @Time: 2019/7/29 15:00
 * @Company：ch
 * @Description: 登录页面
 */

@RuntimePermissions
public class LoginActivity extends BaseMvpActivity<LoginPresenter> implements LoginContract.View {

    @BindView(R.id.btn_Login)
    public Button button;
    @BindView(R.id.input_LoginName)
    public EditText inputLoginName;
    @BindView(R.id.input_LoginPsw)
    public EditText inputLoginPsw;
    @BindView(R.id.checkBox)
    public CheckBox checkBox;

    private MySharedPreferences mySharedPreferences;

    @Override
    protected int getLayout(){
        return R.layout.activity_login;
    }

    @Override
    public void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    public void initViewAndData(){
        initData();
        LoginActivityPermissionsDispatcher.initPermissonWithPermissionCheck(this);
    }

    private void initData(){
        mySharedPreferences = new MySharedPreferences(this, "Shared");
        //初始化是否记住了密码
        String u = mySharedPreferences.getValue("user", "").toString();
        String p = mySharedPreferences.getValue("password", "").toString();
        StaticVariable.URL = mySharedPreferences.getValue("url", "https://www.apiopen.top").toString();
        if (!isEmpty(u) && !isEmpty(p)) {
            inputLoginName.setText(u);
            inputLoginPsw.setText(p);
        }
        if ((Boolean) mySharedPreferences.getValue("check", false)) {
            checkBox.setChecked(true);
        }
    }

    @OnClick(R.id.btn_Login)
    public void btn_Login_Click(View v) {
        mPresenter.login(inputLoginName.getText().toString(), inputLoginPsw.getText().toString());
    }

    @Override
    public void httpCallback(User user) {
        if (checkBox.isChecked()) {
            mySharedPreferences.putValue("user", inputLoginName.getText().toString());
            mySharedPreferences.putValue("password", inputLoginPsw.getText().toString());
            mySharedPreferences.putValue("check", true);
        } else {
            mySharedPreferences.clear();
        }
        Toast.makeText(getBaseContext(), user.getResult(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void httpError(String e) {
        Toast.makeText(getBaseContext(), e, Toast.LENGTH_SHORT).show();
    }

    //有权限时会直接调用改方法，没权限时，会在申请通过后调用
    @NeedsPermission({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void initPermisson() {

    }

    //重写该方法之后，当弹出授权对话框时，我们点击允许授权成功时，会自动执行注解@NeedsPermission所标注的方法里面的逻辑
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        LoginActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    /**
     * 被用户拒绝
     */
    @NeedsPermission({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void onPermissionDenied() {
        show_Toast("权限未授予，部分功能无法使用");
    }
}
