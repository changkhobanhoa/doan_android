package trancongtien.com.doan.joinRoom;

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

import trancongtien.com.doan.R;
import trancongtien.com.doan.model.JoinRoom;

public class AdapterJoinRoom extends RecyclerView.Adapter<AdapterJoinRoom.HolderJoinRoom> implements Filterable {
    JoinRoomSystem context;
    List<JoinRoom> joinRoomList;
    List<JoinRoom> joinRoomListOld;

    public AdapterJoinRoom(JoinRoomSystem context, List<JoinRoom> joinRoomList) {
        this.context = context;
        this.joinRoomList = joinRoomList;
        this.joinRoomListOld = joinRoomList;

    }

    @NonNull
    @Override
    public AdapterJoinRoom.HolderJoinRoom onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_joined_rooms, parent, false);
        return new AdapterJoinRoom.HolderJoinRoom(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterJoinRoom.HolderJoinRoom holder, int position) {
        JoinRoom joinRoom = joinRoomList.get(position);

        context.getInformationOfJoinedRoom(joinRoom, holder.txt_joinRoomName,
                holder.txt_joinRoomFloor, holder.txt_joinRoomFee);

        holder.cv_joinRoomItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, JoinedRoomDetail.class);

                intent.putExtra("Data_JoinedRoom_Parcelable", joinRoom);

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return joinRoomList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String str = constraint.toString();
                if (str.isEmpty()) {
                    joinRoomList = joinRoomListOld;

                } else {
                    List<JoinRoom> list = new ArrayList<>();
                    for (JoinRoom j : joinRoomListOld) {
                        if (j.getRoomId().toLowerCase().contains(str.toLowerCase())) {
                            list.add(j);

                        }
                    }
                    joinRoomList = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = joinRoomList;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                joinRoomList = (List<JoinRoom>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class HolderJoinRoom extends RecyclerView.ViewHolder {
        CardView cv_joinRoomItem;
        TextView txt_joinRoomName, txt_joinRoomFloor, txt_joinRoomFee;

        public HolderJoinRoom(@NonNull View itemView) {
            super(itemView);

            cv_joinRoomItem = itemView.findViewById(R.id.cv_joinRoomItem);
            txt_joinRoomName = itemView.findViewById(R.id.txt_joinRoomName);
            txt_joinRoomFloor = itemView.findViewById(R.id.txt_joinRoomFloor);
            txt_joinRoomFee = itemView.findViewById(R.id.txt_joinRoomFee);


        }
    }
}
