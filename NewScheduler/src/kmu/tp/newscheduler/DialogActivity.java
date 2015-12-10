package kmu.tp.newscheduler;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
 
public class DialogActivity extends Activity implements
        OnClickListener {
 
    private Button mConfirm, mCancel;
    
    AddSchedule add_act=(AddSchedule)AddSchedule.add_activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_dialog);
 
        setContent();
    }
 
    private void setContent() {
        mConfirm = (Button) findViewById(R.id.btn_confirm);
        mCancel = (Button) findViewById(R.id.btn_cancel);
 
        mConfirm.setOnClickListener(this);
        mCancel.setOnClickListener(this);
    }
 
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.btn_confirm:
        	add_act.finish();
            this.finish();
            break;
        case R.id.btn_cancel:
            this.finish();
            break;
        default:
            break;
        }
    }
}
