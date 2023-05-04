package trancongtien.com.doan;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.*;
import android.net.*;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.net.NetworkInterface;

public class ConnectionReceiver extends BroadcastReceiver {
    Context mcontext;
    @Override
    public void onReceive(Context context, Intent intent) {
        if (isConnected(context)) {
            Toast.makeText(context, "Đã có kết nối mạng", Toast.LENGTH_SHORT).show();

        } else {
            showDialog
        }
    }

    public boolean isConnected(Context context) {
        try {
            ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));

            return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
        } catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        }

    }
    public  void showDialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(mcontext);
        LayoutInflater inflater=(LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)e;
        View view=inflater.inflate(R.layout.check_internet,null);
        Button btnOk= view.findViewById(R.id.btnOk);
        builder.setView(view);
        final Dialog dialog=builder.create();
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }
}
