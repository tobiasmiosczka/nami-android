package com.github.tobiasmiosczka.nami;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.github.tobiasmiosczka.nami.program.PhoneContact;

import java.util.List;

public class PhoneListRecyclerViewAdapter extends RecyclerView.Adapter<PhoneListRecyclerViewAdapter.ViewHolder>{

    private List<PhoneContact> memberList;
    private Activity activity;

    public PhoneListRecyclerViewAdapter(Activity activity, List<PhoneContact> memberList) {
        this.activity = activity;
        this.memberList = memberList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_phone_list_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        PhoneContact namiMitglied = memberList.get(i);
        viewHolder.name.setText(namiMitglied.getPhoneNumber() + " (" + namiMitglied.getName() + ")");
        viewHolder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call(memberList.get(i).getPhoneNumber());
            }
        });
    }

    @Override
    public int getItemCount() {
        return memberList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Button button;
        TextView name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            button = itemView.findViewById(R.id.phone_list_item_button);
            name = itemView.findViewById(R.id.phone_list_item_name);
        }
    }

    private void call(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
        activity.startActivity(intent);
    }
}
