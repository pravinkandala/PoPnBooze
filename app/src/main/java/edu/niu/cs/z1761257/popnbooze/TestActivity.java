package edu.niu.cs.z1761257.popnbooze;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.w3c.dom.Text;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);




//       Display();
    }

    void Display(final Double MONEY, final Double ITEMPRICE, final Double ITEMQTY){

        TableLayout table = (TableLayout)findViewById(R.id.tableForButtons);
        for(int i=0; i<2;i++){
            TableRow tableRow = new TableRow(this);
//            tableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
//                    TableLayout.LayoutParams.MATCH_PARENT,10.0f));

            table.addView(tableRow);



            for (int j=0;j<3;j++) {

                Button btn = new Button(this);
                tableRow.addView(btn,1);
                btn.setText("Coke");
                btn.setBackgroundResource(R.drawable.coke);

                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(MONEY>ITEMPRICE && ITEMQTY>0){

                        }
                    }
                });





//                btn.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
//                        TableRow.LayoutParams.MATCH_PARENT,1.0f));

            }
        }

    }
}
