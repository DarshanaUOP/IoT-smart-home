package adapters;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.example.acer.hackethon2.R;

import java.util.List;

import entities.Pluggings;

/**
 * Created by acer on 09-Jun-18.
 */
public class PluggingsAdapter extends ArrayAdapter<Pluggings>{
    private Context context;
    private List<Pluggings> pluggingses;

    public PluggingsAdapter(Context context, List<Pluggings> pluggingses) {
        super(context, R.layout.plugings,pluggingses);
        this.context=context;
        this.pluggingses=pluggingses;
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.plugings,parent,false);

        TextView producctName;
        ImageView statuss_image;
        Switch status_switch;

        producctName = (TextView) view.findViewById(R.id.txt_Product_name);
        status_switch = (Switch) view.findViewById(R.id.sw_status);
        statuss_image = (ImageView) view.findViewById(R.id.img_status);

        producctName.setText(pluggingses.get(position).getProductName());

        boolean on_off=pluggingses.get(position).isOn_off();
        statuss_image.setImageResource(pluggingses.get(position).getStatus_image());
        if (on_off){
            status_switch.setChecked(true);
        }else {
            status_switch.setChecked(false);
        }
        return view;
    }
}
