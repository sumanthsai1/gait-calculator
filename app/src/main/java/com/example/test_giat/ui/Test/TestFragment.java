package com.example.test_giat.ui.Test;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.test_giat.R;

public class TestFragment extends Fragment {

    private EditText age,cadence,step_time,stride_time;
    private Button start;
    private TextView final3;




    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.test_fragment, container, false);

        age=view.findViewById(R.id.edit_age);
        cadence=view.findViewById(R.id.edit_candence);
        step_time=view.findViewById(R.id.edit_steptime);
        stride_time=view.findViewById(R.id.edit_stridetime);
        start=view.findViewById(R.id.start);
        final3=view.findViewById(R.id.final_note3);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final int age1=Integer.parseInt(age.getText().toString());
                final int candence1=Integer.parseInt(cadence.getText().toString());
                final double step=Double.parseDouble(step_time.getText().toString());
                final double stride=Double.parseDouble(stride_time.getText().toString());
                String s;
                int NC;
                double NST,NSTR;
                if ((age1>=70) && (age1<=74))
                {
                    NC=102;
                    NST=0.59;
                    NSTR=1.18;
                }
                else
                {
                    NC=106;
                    NST=0.56;
                    NSTR=1.13;
                }
                int C=(candence1-NC);
                double ST=(step-NST);
                double STR=(stride-NSTR);
                if (C==0 && ST==0 && STR==0)
                {
                    final3.setText("Your gait is normal");
                }
                else if (C<=10)
                {
                    if ((ST<=0.5) && (STR<=1.5))
                    {
                        s="Your gait is at the early stage of abnormality. To improve your gait, kindly decrease your stride time and step time. NOTE: Kindly refer necessary training videos to self-rehabilitate yourself.";
                        final3.setText(s);
                    }
                    else
                    {
                        s="Your gait is at the early stage of abnormality. To improve your gait, kindly decrease your stride time and step time. NOTE: Kindly refer necessary training videos to self-rehabilitate yourself.";
                        final3.setText(s);
                    }
                }
                else if (C<=20)
                {
                    if ((ST<=1) && (STR<=2.5))
                    {
                        s="Your gait is in intermediate stage of abnormality. To improve your gait kindly refer the necessary training videos in video section.";
                        final3.setText(s);
                    }
                    else
                    {
                        s="Your gait is in intermediate stage of abnormality. To improve your gait kindly refer the necessary training videos in video section.";
                        final3.setText(s);
                    }
                }
                else
                {
                    s="Your gait is in final stage of abnormality. Kindly visit doctor to take necessary rehabilitation.";
                    final3.setText(s);
                }
            }
        });
        return  view;


    }



}