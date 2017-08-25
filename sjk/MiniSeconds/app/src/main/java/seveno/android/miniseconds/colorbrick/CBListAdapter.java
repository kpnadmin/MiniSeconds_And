package seveno.android.miniseconds.colorbrick;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import seveno.android.miniseconds.R;

public class CBListAdapter extends ArrayAdapter <Integer>{




    //TextView 필수
    public CBListAdapter(@NonNull Context context, @LayoutRes int resource, @IdRes int textViewResourceId, @NonNull List<Integer> objects) {
        super(context, resource, textViewResourceId, objects);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //리스트의 한 항목에 해당하는 레이아웃을 생성

        View itemLayout = super.getView(position, convertView, parent);
        ViewHolder viewHolder = (ViewHolder) itemLayout.getTag();

        if (viewHolder == null) {
            viewHolder = new ViewHolder();

            viewHolder.colorImg = (ImageView) itemLayout.findViewById(R.id.colorImg);

            //TextView 필수
            viewHolder.txt = (TextView) itemLayout.findViewById(R.id.txt);

            itemLayout.setTag(viewHolder);
        }

        viewHolder.colorImg.setImageResource( getItem(position) );

        return itemLayout;
    }

    public class ViewHolder{
        ImageView colorImg;
        TextView txt;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }
}
