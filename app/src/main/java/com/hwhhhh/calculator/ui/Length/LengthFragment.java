package com.hwhhhh.calculator.ui.Length;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hwhhhh.calculator.R;
import com.hwhhhh.calculator.utils.ConvertLen;

public class LengthFragment extends Fragment implements View.OnClickListener{

    private LengthViewModel lengthViewModel;

    //按钮的id
    private int[] button_ids = new int[]{
            R.id.len_btn_0, R.id.len_btn_1, R.id.len_btn_2, R.id.len_btn_3, R.id.len_btn_4, R.id.len_btn_5, R.id.len_btn_6,
            R.id.len_btn_7, R.id.len_btn_8, R.id.len_btn_9, R.id.len_btn_point, R.id.len_btn_ce};
    //按钮数组
    private Button[] buttons = new Button[12];
    //转换的数值
    private TextView textView_1, textView_2;
    //表达式
    private String expression = "0";
    //控制小数点
    private boolean ctrl_point = true;
    //需要转换的单位
    private Spinner spinner_before;
    private String str_sp_before = "米";
    //转换的目标单位
    private Spinner spinner_after;
    private String str_sp_after = "厘米";
    private ConvertLen translateLength;
    private static final String TAG = "LengthFragment";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_length, container, false);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //设置按钮的点击事件
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = getActivity().findViewById(button_ids[i]);
            buttons[i].setOnClickListener(this);
        }

        textView_1 = getActivity().findViewById(R.id.len_textView_1);
        textView_2 = getActivity().findViewById(R.id.len_textView_2);

        translateLength = new ConvertLen();

        ImageButton button_del = getActivity().findViewById(R.id.len_btn_del);
        button_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (expression.length() > 1) {
                    expression =  expression.substring(0, expression.length()-1);
                    if (expression.subSequence(expression.length()-1, expression.length()).toString().equals(".")) {
                        ctrl_point = true;
                    }
                } else {
                    expression = "0";
                    ctrl_point = true;
                }
                textView_1.setText(expression);
            }
        });

        spinner_before = getActivity().findViewById(R.id.spinner_first);
        spinner_before.setSelection(4);
        spinner_before.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                str_sp_before = spinner_before.getSelectedItem().toString();
                if (str_sp_after != null) {
                    String s = translateLength.getResult(expression, str_sp_before, str_sp_after);
                    textView_2.setText(s);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinner_after = getActivity().findViewById(R.id.spinner_second);
        spinner_after.setSelection(3);
        spinner_after.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                str_sp_after = spinner_after.getSelectedItem().toString();
                String s = translateLength.getResult(expression, str_sp_before, str_sp_after);
                textView_2.setText(s);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String s = translateLength.getResult(expression, str_sp_before, str_sp_after);
                textView_2.setText(s);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                int length = editable.length();
                if (length < 6) {
                    textView_1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 60);
                } else if (editable.length() < 8) {
                    textView_1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 45);
                } else if (editable.length() < 11){
                    textView_1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 32);
                } else {
                    textView_1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 23);
                }
            }
        };

        TextWatcher textWatcher1 = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int length = editable.length();
                if (length < 6) {
                    textView_2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 60);
                } else if (editable.length() < 8) {
                    textView_2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 45);
                } else if (editable.length() < 11){
                    textView_2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 32);
                } else {
                    textView_2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 23);
                }
            }
        };
        textView_1.addTextChangedListener(textWatcher);
        textView_2.addTextChangedListener(textWatcher1);
        Log.d(TAG, "onActivityCreated str_sp_before: " + str_sp_before);
        Log.d(TAG, "onActivityCreated str_sp_after: " + str_sp_after);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        Button button = view.findViewById(id);
        String button_text = button.getText().toString();

        if (id == R.id.len_btn_ce) {
            expression = "0";
            ctrl_point = true;
        } else if (id == R.id.len_btn_point) {
            if (ctrl_point) {
                expression += ".";
                ctrl_point = false;
            }
        } else {
            if (expression.equals("0")) {
                expression = "";
            }
            expression = expression + button_text;
        }
        textView_1.setText(expression);
    }

}