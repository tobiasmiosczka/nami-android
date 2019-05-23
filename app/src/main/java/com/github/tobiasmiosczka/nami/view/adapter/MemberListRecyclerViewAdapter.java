package com.github.tobiasmiosczka.nami.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.tobiasmiosczka.nami.R;
import com.github.tobiasmiosczka.nami.view.MemberDetailsActivity;
import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView;

import java.util.List;

import nami.connector.namitypes.NamiMitglied;
import nami.connector.namitypes.enums.NamiStufe;

public class MemberListRecyclerViewAdapter extends RecyclerView.Adapter<MemberListRecyclerViewAdapter.ViewHolder> implements FastScrollRecyclerView.SectionedAdapter {

    private final List<NamiMitglied> memberList;
    private final Context context;

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

    private int getCircle(NamiStufe stufe) {
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

    private static String getPreviewText(NamiMitglied member) {
        return member.getVorname() + " " + member.getNachname();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final NamiMitglied member  = memberList.get(i);
        viewHolder.name.setText(getPreviewText(member));
        viewHolder.image.setImageResource(getCircle(member.getStufe()));
        viewHolder.layout.setOnClickListener(v -> {
            Intent intent = new Intent(context, MemberDetailsActivity.class);
            intent.putExtra("id", member.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return memberList.size();
    }

    @NonNull
    @Override
    public String getSectionName(int position) {
        final NamiMitglied member  = memberList.get(position);
        return getPreviewText(member).substring(0, 1);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        final RelativeLayout layout;
        final ImageView image;
        final TextView name;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.member_list_item_layout);
            image = itemView.findViewById(R.id.member_list_item_image);
            name = itemView.findViewById(R.id.phone_list_item_name);
        }
    }

}
