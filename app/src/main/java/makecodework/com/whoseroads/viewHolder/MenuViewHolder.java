package makecodework.com.whoseroads.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import makecodework.com.whoseroads.Interface.ItemClickListener;
import makecodework.com.whoseroads.R;

public class MenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView objName,objPerform,objPrice,objRelease;

    private ItemClickListener itemClickListener;

    public MenuViewHolder(View itemView) {
        super(itemView);

        objName = itemView.findViewById(R.id.obj_name);
        objPerform = itemView.findViewById(R.id.object_performer);
        objPrice = itemView.findViewById(R.id.object_price);
        objRelease = itemView.findViewById(R.id.data_release);

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
