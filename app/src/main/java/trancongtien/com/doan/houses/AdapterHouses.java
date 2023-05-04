package trancongtien.com.doan.houses;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import trancongtien.com.doan.R;
import trancongtien.com.doan.houses.houseDetail.HouseDetailSystem;
import trancongtien.com.doan.model.Houses;

public class AdapterHouses extends RecyclerView.Adapter<AdapterHouses.HouseHolder> implements Filterable {

    Context context;

    List<Houses> housesList;
    List<Houses> housesListOld;

    public AdapterHouses(Context context, List<Houses> housesList) {
        this.context = context;
        this.housesList = housesList;
        this.housesListOld = housesList;
    }


    @NonNull
    @Override
    public HouseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_house, parent, false);
        return new AdapterHouses.HouseHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HouseHolder holder, int position) {
        Houses houses = housesList.get(position);

        holder.txt_houseName.setText(houses.gethName());
        holder.txt_houseAddress.setText(houses.gethAddress());
        holder.txt_houseCity.setText(houses.gethTinhThanhPho());

        holder.cv_houseItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, HouseDetailSystem.class);

                intent.putExtra("Data_House_Parcelable", houses);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return housesList.size();
    }

    public class HouseHolder extends RecyclerView.ViewHolder {
        TextView txt_houseName, txt_houseAddress, txt_houseCity;
        CardView cv_houseItem;


        public HouseHolder(@NonNull View itemView) {
            super(itemView);
            txt_houseName = itemView.findViewById(R.id.txt_houseName);
            txt_houseAddress = itemView.findViewById(R.id.txt_houseAddress);
            txt_houseCity = itemView.findViewById(R.id.txt_houseCity);

            cv_houseItem = itemView.findViewById(R.id.cv_houseItem);
        }
    }

    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String strSearch = constraint.toString();
                if (strSearch.isEmpty()) {
                    housesList = housesListOld;

                } else {
                    List<Houses> list = new ArrayList<>();
                    for (Houses h : housesListOld) {
                        if (h.gethName().toLowerCase().contains(strSearch.toLowerCase())) {
                            list.add(h);
                        }
                    }
                    housesList = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = housesList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                housesList = (List<Houses>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
