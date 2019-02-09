package makecodework.com.whoseroads.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import makecodework.com.whoseroads.Interface.ItemClickListener;
import makecodework.com.whoseroads.R;

public class RoadsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView defName, defStreet;
    public ImageView defImage;

    private ItemClickListener itemClickListener;




    public RoadsViewHolder(View itemView) {
        super(itemView);

        defName = itemView.findViewById(R.id.defect_name);
        defStreet = itemView.findViewById(R.id.defect_street);
        defImage = itemView.findViewById(R.id.defectroad_img);



        itemView.setOnClickListener(this);


    }
    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),false);

    }
}
