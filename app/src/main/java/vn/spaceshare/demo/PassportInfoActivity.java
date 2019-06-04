package vn.spaceshare.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import vn.spaceshare.demo.model.PassportInfo;
import vn.spaceshare.demo.util.Const;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PassportInfoActivity extends AppCompatActivity {

    private TextView tvName;
    private TextView tvDateOfBirth;
    private TextView tvDateOfExpiry;
    private TextView tvNationality;
    private TextView tvSex;
    private TextView tvPassportNumber;
    private TextView tvTitleToolBar;
    private ImageView ivBack;

    private PassportInfo passportInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passport_info);

        initView();

        if (getIntent() != null) {
            passportInfo = getIntent().getParcelableExtra(Const.PASSPORT_INFO);
            setData(passportInfo);
        }
    }

    private void initView() {
        tvName = findViewById(R.id.tvName);
        tvDateOfBirth = findViewById(R.id.tvDateOfBirth);
        tvDateOfExpiry = findViewById(R.id.tvDateOfExpiry);
        tvNationality = findViewById(R.id.tvNationality);
        tvSex = findViewById(R.id.tvSex);
        tvPassportNumber = findViewById(R.id.tvPassportNumber);
        tvTitleToolBar = findViewById(R.id.tvTitleToolBar);
        ivBack = findViewById(R.id.ivBack);

        tvTitleToolBar.setText(getString(R.string.title_passport_info));

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setData(PassportInfo passportInfo) {

        tvName.setText(passportInfo.getFullName());
        tvDateOfBirth.setText(convertDate(passportInfo.getDateOfBirth()));
        tvDateOfExpiry.setText(convertDate(passportInfo.getDateOfExpiry()));
        tvNationality.setText(passportInfo.getNationality());
        tvSex.setText(customSex(passportInfo.getSex()));
        tvPassportNumber.setText(passportInfo.getPassportNumber());

    }


    private String customSex(String sex) {
        if (sex.equals("M")) {
            return "Male";
        } else {
            return "Female";
        }
    }

    private String convertDate(String date) {
        String FORMAT_DATE_ONE = "yymmdd";
        String FORMAT_DATE_CUSTOM = "dd/mm/yy";

        try {
            SimpleDateFormat sourceFormat = new SimpleDateFormat(FORMAT_DATE_ONE);
            Date dateFormat = sourceFormat.parse(date);
            sourceFormat.applyPattern(FORMAT_DATE_CUSTOM);
            return sourceFormat.format(dateFormat);

        } catch (ParseException ex) {

        }
        return date;
    }
}
