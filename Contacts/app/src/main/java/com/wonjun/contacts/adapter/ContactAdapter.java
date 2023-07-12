package com.wonjun.contacts.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.wonjun.contacts.AddActivity;
import com.wonjun.contacts.R;
import com.wonjun.contacts.UpdateActivity;
import com.wonjun.contacts.data.DatabaseHandler;
import com.wonjun.contacts.model.Contact;

import java.util.ArrayList;

public class ContactAdapter extends  RecyclerView.Adapter<ContactAdapter.ViewHolder>{

    Context context; //Activity 연결
    ArrayList<Contact> contactArrayList; //담아둘 데이터

    public ContactAdapter(Context context, ArrayList<Contact> contactArrayList) {
        this.context = context;
        this.contactArrayList = contactArrayList;
    }

    @NonNull
    @Override
    public ContactAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_row, parent, false);
        return new ContactAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Contact contact = contactArrayList.get(position);

        holder.txtName.setText(contact.getName());
        holder.txtPhone.setText(contact.getPhone());
    }

    @Override
    public int getItemCount() {
        return contactArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtName;
        TextView txtPhone;
        ImageButton btnDelete;

        LinearLayout editContact;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.txtName);
            txtPhone = itemView.findViewById(R.id.txtPhone);
            btnDelete = itemView.findViewById(R.id.btnDelete);

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showAlertDialog();
                }
            });

            editContact = itemView.findViewById(R.id.editContact);
            editContact.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, UpdateActivity.class);

                    int selected = getAdapterPosition();
                    Contact contact = contactArrayList.get(selected);
                    //intent.putExtra("contact", contact);

                    /*
                    intent.putExtra("id", contact.getId());
                    intent.putExtra("name", contact.getName());
                    intent.putExtra("phone", contact.getPhone());
                     */

                    //한꺼번에 보내는 것이 좋다.
                    intent.putExtra("contact", contact);//원래는 보낼수 없음
                                        //인터페이스를 상속받는다면 보낼 수 있다.

                    context.startActivity(intent);

                }
            });
        }

        private void showAlertDialog(){
            AlertDialog.Builder builder = new AlertDialog.Builder(context);

            builder.setTitle("주소록 삭제");
            builder.setMessage("정말 삭제?");
            builder.setCancelable(false);

            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    int selected = getAdapterPosition();

                    
                    //DB 삭제
                    Contact contact = contactArrayList.get(selected); //객체를 먼저 가져와야함.
                    
                    DatabaseHandler handler = new DatabaseHandler(context, "contact_db", null, 1);
                    handler.deleteContact(contact); //넣은 다음 id를 꺼네자.

                    contactArrayList.remove(selected);
                    notifyDataSetChanged();
                }
            });

            builder.setNegativeButton("No", null);

            builder.show();
        }
    }
}
