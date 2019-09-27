package com.hwhhhh.calculator.ui.volume;

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
import androidx.lifecycle.ViewModelProviders;

import com.hwhhhh.calculator.R;
import com.hwhhhh.calculator.utils.ConvertVol;

public class VolumeFragment extends Fragment implements View.OnClickListener{

    private VolumeViewModel volumeViewModel;
    private static final String TAG = "VolumeFragment";
    //转换的数值
    private TextView textView_1, textView_2;
    //表达式
    private String expression = "0";
    //控制小数点
    private boolean ctrl_point = true;
    //按钮的id
    private int[] button_ids = new int[]{
            R.id.vol_btn_0, R.id.vol_btn_1, R.id.vol_btn_2, R.id.vol_btn_3, R.id.vol_btn_4, R.id.vol_btn_5, R.id.vol_btn_6,
            R.id.vol_btn_7, R.id.vol_btn_8, R.id.vol_btn_9, R.id.vol_btn_point, R.id.vol_btn_ce};
    //按钮数组
    private Button[] buttons = new Button[12];
    private ConvertVol convertVol;
    private Spinner spinner_1, spinner_2;
    private String spinner_1_selected, spinner_2_selected;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        volumeViewModel =
                ViewModelProviders.of(this).get(VolumeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_volume, container, false);
//        final TextView textView = root.findViewById(R.id.text_share);
//        shareViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
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

        ImageButton button_del = getActivity().findViewById(R.id.vol_btn_del);
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

        textView_1 = getActivity().findViewById(R.id.vol_textView_1);
        textView_2 = getActivity().findViewById(R.id.vol_textView_2);
        convertVol = new ConvertVol();

        spinner_1 = getActivity().findViewById(R.id.vol_spinner_first);
        spinner_2 = getActivity().findViewById(R.id.vol_spinner_second);
        spinner_1.setSelection(2);
        spinner_2.setSelection(0);
        spinner_1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinner_1_selected = spinner_1.getSelectedItem().toString();
                Log.d(TAG, "onItemSelected: " +spinner_1_selected);
                if (spinner_2_selected != null) {
                    String s = convertVol.getResult(expression, spinner_1_selected, spinner_2_selected);
                    textView_2.setText(s);
                    Log.d(TAG, "onItemSelected: spinner2selected is null" );
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinner_2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinner_2_selected = spinner_2.getSelectedItem().toString();
                String s = convertVol.getResult(expression, spinner_1_selected, spinner_2_selected);
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
                String s = convertVol.getResult(expression, spinner_1_selected, spinner_2_selected);
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
    }

    @Override
    public void onClick(View view){
        int id = view.getId();
        Button button = view.findViewById(id);
        String button_text = button.getText().toString();

        if (id == R.id.vol_btn_ce) {
            expression = "0";
            ctrl_point = true;
        } else if (id == R.id.vol_btn_point) {
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