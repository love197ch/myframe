package com.ch.myframe.ui.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ch.myframe.R;
import com.ch.myframe.base.BaseActivity;
import com.ch.myframe.base.BaseFragment;
import com.ch.myframe.ui.fragment.ConferenceFragment;
import com.ch.myframe.ui.fragment.HomeFragment;
import com.ch.myframe.ui.fragment.MessageFragment;
import com.ch.myframe.ui.fragment.MineFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.nav_home)
    public TextView navHome;
    @BindView(R.id.nav_message)
    public TextView navMessage;
    @BindView(R.id.nav_mine)
    public TextView navMine;
    @BindView(R.id.nav_conference)
    public TextView navConference;

    @BindView(R.id.img_home)
    public ImageView imgHome;
    @BindView(R.id.img_message)
    public ImageView imgMessage;
    @BindView(R.id.img_mine)
    public ImageView imgMine;
    @BindView(R.id.img_conference)
    public ImageView imgConference;

    private HomeFragment homeFragment;
    private MessageFragment messageFragment;
    private MineFragment mineFragment;
    private ConferenceFragment conferenceFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        homeFragment = new HomeFragment();
        addFragment(getSupportFragmentManager(), homeFragment.getClass(), R.id.fragment_container, null);
    }

    //重置所有文本的选中状态
    public void unselected() {
        Drawable homeDrawable = ContextCompat.getDrawable(this, R.drawable.ic_main_dzy);
        Drawable messageDrawable = ContextCompat.getDrawable(this, R.drawable.ic_main_dhys);
        Drawable mineDrawable = ContextCompat.getDrawable(this, R.drawable.ic_main_dtxl);
        Drawable conferenceDrawable = ContextCompat.getDrawable(this, R.drawable.ic_main_dsz);

        navHome.setTextColor(ContextCompat.getColor(this, R.color.main_menu_text_color));
        imgHome.setImageDrawable(homeDrawable);
        navMessage.setTextColor(ContextCompat.getColor(this, R.color.main_menu_text_color));
        imgMessage.setImageDrawable(messageDrawable);
        navMine.setTextColor(ContextCompat.getColor(this, R.color.main_menu_text_color));
        imgMine.setImageDrawable(mineDrawable);
        navConference.setTextColor(ContextCompat.getColor(this, R.color.main_menu_text_color));
        imgConference.setImageDrawable(conferenceDrawable);
    }

    @OnClick({R.id.l_home, R.id.l_message, R.id.l_conference, R.id.l_mine})
    public void btn_Nav_Click(View v) {
        switch (v.getId()) {
            case R.id.l_home:
                unselected();
                Drawable homeDrawable = ContextCompat.getDrawable(this, R.drawable.ic_main_dzyh);
                imgHome.setImageDrawable(homeDrawable);
                navHome.setTextColor(ContextCompat.getColor(this, R.color.main_menu_text_color_over));
                if (homeFragment == null) {
                    homeFragment = new HomeFragment();
                }
                addFragment(getSupportFragmentManager(), homeFragment.getClass(), R.id.fragment_container, null);
                break;
            case R.id.l_message:
                unselected();
                Drawable messageDrawable = ContextCompat.getDrawable(this, R.drawable.ic_main_dhysh);
                imgMessage.setImageDrawable(messageDrawable);
                navMessage.setTextColor(ContextCompat.getColor(this, R.color.main_menu_text_color_over));
                if (messageFragment == null) {
                    messageFragment = new MessageFragment();
                }
                addFragment(getSupportFragmentManager(), messageFragment.getClass(), R.id.fragment_container, null);
                break;
            case R.id.l_conference:
                unselected();
                Drawable conferenceDrawable = ContextCompat.getDrawable(this, R.drawable.ic_main_dszh);
                imgConference.setImageDrawable(conferenceDrawable);
                navConference.setTextColor(ContextCompat.getColor(this, R.color.main_menu_text_color_over));
                if (conferenceFragment == null) {
                    conferenceFragment = new ConferenceFragment();
                }
                addFragment(getSupportFragmentManager(), conferenceFragment.getClass(), R.id.fragment_container, null);
                break;
            case R.id.l_mine:
                unselected();
                Drawable mineDrawable = ContextCompat.getDrawable(this, R.drawable.ic_main_dtxlh);
                imgMine.setImageDrawable(mineDrawable);
                navMine.setTextColor(ContextCompat.getColor(this, R.color.main_menu_text_color_over));
                if (mineFragment == null) {
                    mineFragment = new MineFragment();
                }
                addFragment(getSupportFragmentManager(), mineFragment.getClass(), R.id.fragment_container, null);
                break;
        }
    }

    /**
     * Fragment的添加
     *
     * @param manager     Fragment管理器
     * @param aClass      相应的Fragment对象的getClass
     * @param containerId 容器的id
     * @param args        需要传值的话可将bundle填入  不需要传值就填null
     */
    protected void addFragment(FragmentManager manager, Class<? extends BaseFragment> aClass, int containerId, Bundle args) {
        String tag = aClass.getName();
        Fragment fragment = manager.findFragmentByTag(tag);
        FragmentTransaction transaction = manager.beginTransaction(); // 开启一个事务
        if (fragment == null) {// 没有添加
            try {
                fragment = aClass.newInstance(); // 通过反射 new 出一个 fragment 的实例
                transaction.add(containerId, fragment, tag);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            if (fragment.isAdded()) {
                if (fragment.isHidden()) {
                    transaction.show(fragment);
                }
            } else {
                transaction.add(containerId, fragment, tag);
            }
        }
        if (fragment != null) {
            fragment.setArguments(args);
            hideBeforeFragment(manager, transaction, fragment);
            transaction.commit();
        }
    }


    /**
     * 除当前 fragment 以外的所有 fragment 进行隐藏
     *
     * @param manager
     * @param transaction
     * @param currentFragment
     */
    private void hideBeforeFragment(FragmentManager manager, FragmentTransaction transaction, Fragment currentFragment) {
        List<Fragment> fragments = manager.getFragments();
        for (Fragment fragment : fragments) {
            if (fragment != currentFragment && !fragment.isHidden()) {
                transaction.hide(fragment);
            }
        }
    }

    //点击返回键，返回到桌面
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            Intent home = new Intent(Intent.ACTION_MAIN);
            home.addCategory(Intent.CATEGORY_HOME);
            startActivity(home);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
