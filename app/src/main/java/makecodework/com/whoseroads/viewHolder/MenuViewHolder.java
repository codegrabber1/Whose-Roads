package makecodework.com.whoseroads.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import makecodework.com.whoseroads.Interface.ItemClickListener;
import makecodework.com.whoseroads.R;

public class MenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView defectName;
    public TextView defectAddress;
//    public TextView defectDate;
    public ImageView imageV;

    private ItemClickListener itemClickListener;

    public MenuViewHolder(View itemView) {
        super(itemView);

        defectName = itemView.findViewById(R.id.defect_title);
        defectAddress = itemView.findViewById(R.id.defect_address);
        //defectDate = itemView.findViewById(R.id.defect_date);
        imageV = itemView.findViewById(R.id.road_img);

        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {
            itemClickListener.onClick(view,getAdapterPosition(),false);

    }
}
