package com.hwhhhh.calculator.ui.home;

import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.hwhhhh.calculator.R;
import com.hwhhhh.calculator.utils.Calculate;

import java.lang.reflect.Method;

public class HomeFragment extends Fragment implements View.OnClickListener{
    private static final String TAG = "HomeFragment";
    private static final String EXPRESSION = "0";

    //Button数组，存放activity的buttons
    private Button[] btns = new Button[33];
    //获取btn的id
    private int[] ids_btn = new int[]{
            R.id.button_0, R.id.button_1, R.id.button_2, R.id.button_3, R.id.button_4, R.id.button_5, R.id.button_6,
            R.id.button_7, R.id.button_8, R.id.button_9, R.id.button_pm, R.id.button_point, R.id.button_plus, R.id.button_div, R.id.button_mul,
            R.id.button_sub, R.id.button_ce, R.id.button_equal, R.id.button_brackets_left, R.id.button_brackets_right,R.id.button_pi, R.id.button_sin, R.id.button_cos, R.id.button_tan, R.id.button_e, R.id.button_ln, R.id.button_log,
            R.id.button_xjiecheng, R.id.button_xx, R.id.button_xxx, R.id.button_daoshu, R.id.button_genhao, R.id.button_xy
    };

    //editText
    private EditText editText;
    //光标索引
    private int index;
    private Editable editable;
    //记录上一个运算符
    private String operation = "";
    private boolean ctrl_clear = false;
    private boolean ctrl_point = true;
    private String exp = "";
    //实例化自定义计算器对象
    private Calculate calculate = new Calculate();
    private int screenOrientation;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: homeFragment's create");
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        //禁止显示系统自带键盘
        disableShowSoftInput();
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        screenOrientation = getActivity().getResources().getConfiguration().orientation;
        if (screenOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            //通过ids得到对应btn设置其监听事件
            for (int i = 0; i < ids_btn.length; i++){
                btns[i] = getActivity().findViewById(ids_btn[i]);
                btns[i].setOnClickListener(this);
            }
        } else {
            //通过ids得到对应btn设置其监听事件
            for (int i = 0; i < ids_btn.length - 15; i++){
                btns[i] = getActivity().findViewById(ids_btn[i]);
                btns[i].setOnClickListener(this);
            }
        }

        ImageButton btn_back = getActivity().findViewById(R.id.button_back);
        btn_back.setOnClickListener(this);

        editText = getActivity().findViewById(R.id.editText);
        //解决长按复制粘贴问题
        editText.setLongClickable(false);
        editText.setTextIsSelectable(false);

    }

    @Override
    public void onClick(View view) {
        //取得视图id，取得对应按钮的text
        int id = view.getId();
        Button button;
        String text_btn = "";
        if (id != R.id.button_back) {
            button = view.findViewById(id);
            text_btn = button.getText().toString();
        }
        //获取光标位置
        index = editText.getSelectionStart();
        editable = editText.getText();
        //取得当前editText的文本
        exp = editable.toString();

        //是否清空, 因为已经计算过一次
        if (ctrl_clear) {
            editable.delete(0, exp.length());
            index = editText.getSelectionStart();
            exp = editable.toString();
            ctrl_clear = false;
        }

        if (id == R.id.button_equal) {
            if (index > 0) {
                editable.delete(0, editable.toString().length());
                if (calculate.getResult(exp) != null) {
                    editable.append(calculate.getResult(exp).toPlainString());
                } else {
                    editable.append("error");
                }
                ctrl_clear = true;
                ctrl_point = true;
                operation = "";
            }
        } else if (id == R.id.button_ce){
            if (exp.length() > 0) {
                editable.delete(0, exp.length());
                operation = "";
            }
        } else if (id == R.id.button_back){
            if (index > 0) {
                CharSequence cs = editable.subSequence(index-1, index);
                editable.delete(index-1, index);
                Log.d(TAG, "onClick: " + cs.toString());
                if (cs.toString().equals(operation)) {
                    operation = "";
                } else if (cs.toString().equals(".")) {
                    ctrl_point = true;
                }
            }
        } else if (id == R.id.button_pm) {
            if (!exp.isEmpty()) {
                char c = exp.charAt(0);
                if (c == '-' ) {
                    editable.delete(0, 1);
                } else {
                    editable.insert(0, "-");
                }
            }
        } else if (id == R.id.button_plus || id == R.id.button_sub || id == R.id.button_mul ||
                id == R.id.button_div) {
            if (!(id != R.id.button_sub && exp.length() == 0)) {
                if (operation.equals("")) {
                    editable.insert(index, text_btn);
                } else if (!operation.equals(text_btn)){
                    editable.replace(index-1, index, text_btn);
                }
                operation = text_btn;
                ctrl_point = true;
            }
        } else if (id == R.id.button_0) {   //输入开头为0的情况，只允许输入一次0
            if (!(exp.equals("0")||exp.equals("-0"))) {
                editable.insert(index, text_btn);
                operation = "";
            }
        } else if (id == R.id.button_point) {
            if (ctrl_point) {
                editable.insert(index, text_btn);
                operation = "";
                ctrl_point = false;
            }
        } else if (id == R.id.button_sin) {
            editable.insert(index, "sin(");
        }  else if (id == R.id.button_cos) {
            editable.insert(index, "cos(");
        }  else if (id == R.id.button_tan) {
            editable.insert(index, "tan(");
        }  else if (id == R.id.button_pi) {
            editable.insert(index, "π");
        }  else if (id == R.id.button_e) {
            editable.insert(index, "e");
        }  else if (id == R.id.button_ln) {
            editable.insert(index, "ln(");
        }  else if (id == R.id.button_log) {
            editable.insert(index, "log(");
        }  else if (id == R.id.button_xjiecheng) {
            editable.insert(index, "!");
        }  else if (id == R.id.button_xx) {
            editable.insert(index, "^(2)");
        }  else if (id == R.id.button_xxx) {
            editable.insert(index, "^(3)");
        }  else if (id == R.id.button_daoshu) {
            editable.insert(index, "^(-1)");
        }  else if (id == R.id.button_genhao) {
            editable.insert(index, "√");
        }  else if (id == R.id.button_xy) {
            editable.insert(index, "^(");
        } else {
            editable.insert(index, text_btn);
            operation = "";
        }
    }

    //禁止弹出软键盘
    public void disableShowSoftInput() {
        if (android.os.Build.VERSION.SDK_INT <= 10) {
            editText.setInputType(InputType.TYPE_NULL);
        } else {
            Class<EditText> cls = EditText.class;
            Method method;
            try {
                method = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
                method.setAccessible(true);
                method.invoke(editText, false);
            } catch (Exception e) {
            }
        }
    }

//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//
//        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
//            getActivity().setContentView(R.layout.fragment_home);
//            Log.d(TAG, "onConfigurationChanged: this is portrait");
//        } else {
//            getActivity().setContentView(R.layout.fragment_home);
//            Log.d(TAG, "onConfigurationChanged: this is landscape");
//        }
//    }

//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        HomeFragment homeFragment = new HomeFragment();
//        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//        FragmentTransaction transaction = fragmentManager.beginTransaction();
//        transaction.replace(R.id.nav_host_fragment, homeFragment, TAG);
//        fragmentManager.executePendingTransactions();
//        Fragment fragment = fragmentManager.findFragmentByTag(TAG);
//        if (fragment != null) {
//            transaction.remove(fragment);
//            if (homeFragment != null) {
//                HomeFragment newHomeFragment = new HomeFragment();
//                transaction.add(R.id.nav_host_fragment, newHomeFragment, TAG);
//                Log.d(TAG, "onConfigurationChanged: new fragment is not null");
//            }
//        } else {
//            Log.d(TAG, "onConfigurationChanged: fragment is null");
//        }
//        transaction.commit();
//
//        super.onConfigurationChanged(newConfig);
//    }
}