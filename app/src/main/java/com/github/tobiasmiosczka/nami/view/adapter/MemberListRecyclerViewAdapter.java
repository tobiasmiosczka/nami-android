package com.github.tobiasmiosczka.nami.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.tobiasmiosczka.nami.R;
import com.github.tobiasmiosczka.nami.view.MemberDetailsActivity;

import java.util.List;

import nami.connector.namitypes.NamiMitglied;
import nami.connector.namitypes.enums.NamiStufe;

public class MemberListRecyclerViewAdapter extends RecyclerView.Adapter<MemberListRecyclerViewAdapter.ViewHolder>{

    private List<NamiMitglied> memberList;
    private Context context;

    public MemberListRecyclerViewAdapter(Context context, List<NamiMitglied> memberList) {
        this.context = context;
        this.memberList = memberList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_member_list_item, viewGroup, false);
        return new ViewHolder(view);
    }

    public int getCircle(NamiStufe stufe) {
        if (stufe == null)
            return R.drawable.circle;
        switch (stufe) {
            case WOELFLING: return R.drawable.circle_woelflinge;
            case JUNGPFADFINDER: return R.drawable.circle_jungpfadfinder;
            case PFADFINDER: return R.drawable.circle_pfadfinder;
            case ROVER: return R.drawable.circle_rover;
            default: return R.drawable.circle;
        }
    }

                         @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        final NamiMitglied namiMitglied  = memberList.get(i);
        viewHolder.name.setText(namiMitglied.getVorname() + " " + namiMitglied.getNachname());
        viewHolder.image.setImageResource(getCircle(namiMitglied.getStufe()));
        viewHolder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MemberDetailsActivity.class);
                intent.putExtra("id", namiMitglied.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return memberList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout layout;
        ImageView image;
        TextView name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.member_list_item_layout);
            image = itemView.findViewById(R.id.member_list_item_image);
            name = itemView.findViewById(R.id.phone_list_item_name);
        }
    }

}
