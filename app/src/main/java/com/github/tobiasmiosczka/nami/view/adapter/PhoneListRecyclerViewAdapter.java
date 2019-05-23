package com.github.tobiasmiosczka.nami.view.adapter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.tobiasmiosczka.nami.R;
import com.github.tobiasmiosczka.nami.program.PhoneContact;

import java.util.List;

public class PhoneListRecyclerViewAdapter extends RecyclerView.Adapter<PhoneListRecyclerViewAdapter.ViewHolder>{

    private final List<PhoneContact> memberList;
    private final Activity activity;

    public PhoneListRecyclerViewAdapter(Activity activity, List<PhoneContact> phoneContacts) {
        this.activity = activity;
        this.memberList = phoneContacts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_phone_list_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        PhoneContact phoneContact = memberList.get(i);
        viewHolder.name.setText(phoneContact.getPhoneNumber() + ((phoneContact.getName() == null) ? "" : " (" + phoneContact.getName() + ")"));
        viewHolder.button.setOnClickListener(v -> call(memberList.get(viewHolder.getAdapterPosition()).getPhoneNumber()));
    }

    @Override
    public int getItemCount() {
        return memberList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final Button button;
        private final TextView name;

        ViewHolder(@NonNull View itemView) {
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
