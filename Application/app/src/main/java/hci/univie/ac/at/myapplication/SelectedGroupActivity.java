package hci.univie.ac.at.myapplication;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

/**
 * Created by Benne on 01.05.2018.
 */

public class SelectedGroupActivity extends AppCompatActivity {
    TableLayout tableMembers;
    TableLayout bills;
    TableRow tmrow;
    TableRow tmrow1;
    TextView tv1, tv2, tv3, tv4, tv5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectedgroup);
        setTitle("Gruppenübersicht");
        Drawable d = getResources().getDrawable(R.drawable.button_border);

        tableMembers = (TableLayout)findViewById(R.id.tblmembers);
        bills = (TableLayout)findViewById(R.id.bill_history);

        //First Rows to the table Bills
        tmrow1 = new TableRow(this);
        tv3 = new TextView(this);
        tv4 = new TextView(this);
        tv5 = new TextView(this);

        tv3.setBackground(d);
        tv4.setBackground(d);
        tv5.setBackground(d);

        tv3.setHeight(110);
        tv4.setHeight(110);
        tv5.setHeight(110);

        tv3.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tv4.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tv5.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        tv3.setTextSize(18);
        tv4.setTextSize(18);
        tv5.setTextSize(18);

        tv3.setTextColor(Color.rgb(255, 0, 0));
        tv4.setTextColor(Color.rgb(255, 0, 0));
        tv5.setTextColor(Color.rgb(255, 0, 0));

        tv3.setText("Beschreibung");
        tv4.setText("Summe");
        tv5.setText("Bezahlt von");

        tmrow1.setId(0);
        tmrow1.addView(tv3);
        tmrow1.addView(tv4);
        tmrow1.addView(tv5);
        bills.addView(tmrow1);
        bills.setColumnStretchable(0, true);
        bills.setColumnStretchable(1, true);
        bills.setColumnStretchable(2, true);



        for (int i = 0; i < MainActivity.mainGruppe.getMembers().size(); ++i) {
            //Adding members
            tmrow = new TableRow(this);
            tv1 = new TextView(this);
            tv2 = new TextView(this);

            tv1.setBackground(d);
            tv2.setBackground(d);

            tv1.setHeight(110);
            tv2.setHeight(110);

            tv1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            tv2.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

            tv1.setTextSize(18);
            tv2.setTextSize(18);

            tv1.setTextColor(Color.rgb(0, 0, 0));
            tv2.setTextColor(Color.rgb(0, 0, 0));

            tv1.setText(MainActivity.mainGruppe.getMembers().get(i));
            tv2.setText("");

            tmrow.setId(i);
            tmrow.addView(tv1);
            tmrow.addView(tv2);
            tableMembers.addView(tmrow);
            tableMembers.setColumnStretchable(0, true);
            tableMembers.setColumnStretchable(1, true);
        }
            //Adding bills
        for (int i = 0; i < MainActivity.mainGruppe.getBills().size(); ++i){
            tmrow1 = new TableRow(this);
            tv3 = new TextView(this);
            tv4 = new TextView(this);
            tv5 = new TextView(this);

            tv3.setBackground(d);
            tv4.setBackground(d);
            tv5.setBackground(d);

            tv3.setHeight(110);
            tv4.setHeight(110);
            tv5.setHeight(110);

            tv3.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            tv4.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            tv5.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

            tv3.setTextSize(18);
            tv4.setTextSize(18);
            tv5.setTextSize(18);

            tv3.setTextColor(Color.rgb(0, 0, 0));
            tv4.setTextColor(Color.rgb(0, 0, 0));
            tv5.setTextColor(Color.rgb(0, 0, 0));

            tv3.setText(MainActivity.mainGruppe.getBills().get(i).getDescription());
            tv4.setText(String.valueOf(MainActivity.mainGruppe.getBills().get(i).getPrice()));
            tv5.setText(MainActivity.mainGruppe.getBills().get(i).getPayer());

            tmrow1.setId(i+1);
            tmrow1.addView(tv3);
            tmrow1.addView(tv4);
            tmrow1.addView(tv5);
            bills.addView(tmrow1);
            bills.setColumnStretchable(0, true);
            bills.setColumnStretchable(1, true);
            bills.setColumnStretchable(2, true);

        }

    }
}
