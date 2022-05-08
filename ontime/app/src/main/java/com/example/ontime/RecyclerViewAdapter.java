package com.example.ontime;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter  extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    // Deklarasi Variable
    private ArrayList<data_kegiatan> listKegiatan;
    private Context context;


    public interface dataListener{
        void onDeleteData(data_kegiatan data, int position);
    }
    dataListener listener;
    public RecyclerViewAdapter(ArrayList<data_kegiatan> listKegiatan, Context context){
        this.listKegiatan = listKegiatan;
        this.context = context;
        listener = (MainActivity)context;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View V = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_design, parent, false);
        return new ViewHolder(V);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        final String title = listKegiatan.get(position).getTitledoes();
        final String desc = listKegiatan.get(position).getDescdoes();
        final String date = listKegiatan.get(position).getDatedoes();
        final String info = listKegiatan.get(position).getInfodoes();
        final String place = listKegiatan.get(position).getPlacedoes();
        final String barang = listKegiatan.get(position).getBarangdoes();


        holder.title.setText(title);
        holder.desc.setText(desc);
        holder.date.setText(date);
        holder.info.setText(info);
        holder.place.setText(place);
        holder.barang.setText("Bawa "+barang);
        // Metode alert dialog
        holder.ic_dots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] action = {"Update","Delete"};
                AlertDialog.Builder alert = new AlertDialog.Builder(v.getContext());
                alert.setItems(action, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i){
                            case 0:

                                Bundle bundle = new Bundle();
                                bundle.putString("dataTitledoes", listKegiatan.get(position).getTitledoes());
                                bundle.putString("dataDescdoes", listKegiatan.get(position).getDescdoes());
                                bundle.putString("dataDatedoes", listKegiatan.get(position).getDatedoes());
                                bundle.putString("dataInfodoes", listKegiatan.get(position).getInfodoes());
                                bundle.putString("dataPlacedoes", listKegiatan.get(position).getPlacedoes());
                                bundle.putString("dataBarangdoes", listKegiatan.get(position).getBarangdoes());
                                bundle.putString("getPrimaryKey", listKegiatan.get(position).getKey());
                                Intent intent = new Intent(v.getContext(), ActivityUpdate.class);
                                intent.putExtras(bundle);
                                context.startActivity(intent);
                                break;
                            case 1:
                                listener.onDeleteData(listKegiatan.get(position),position);
                                break;
                        }
                    }
                });
                alert.create();
                alert.show();



            }
        });


//        holder.ic_dots.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                final String[] action = {"Update","Delete"};
//                AlertDialog.Builder alert = new AlertDialog.Builder(v.getContext());
//                alert.setItems(action, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        switch (i){
//                            case 0:
//
//                                Bundle bundle = new Bundle();
//                                bundle.putString("dataTitledoes", listKegiatan.get(position).getTitledoes());
//                                bundle.putString("dataDescdoes", listKegiatan.get(position).getDescdoes());
//                                bundle.putString("dataDatedoes", listKegiatan.get(position).getDatedoes());
//                                bundle.putString("dataInfodoes", listKegiatan.get(position).getInfodoes());
//                                bundle.putString("dataPlacedoes", listKegiatan.get(position).getPlacedoes());
//                                bundle.putString("dataBarangdoes", listKegiatan.get(position).getBarangdoes());
//                                bundle.putString("getPrimaryKey", listKegiatan.get(position).getKey());
//                                Intent intent = new Intent(v.getContext(), ActivityUpdate.class);
//                                intent.putExtras(bundle);
//                                context.startActivity(intent);
//                                break;
//                            case 1:
//                                listener.onDeleteData(listKegiatan.get(position),position);
//                                break;
//                        }
//                    }
//                });
//                alert.create();
//                alert.show();
//
//                return true;
//            }
//        });
    }

        @Override
    public int getItemCount() {
        return listKegiatan.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // inisialisasi widget
        private TextView title, desc, date,info, place, barang;
        private LinearLayout ListItem;
        private ImageView ic_dots;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Inisialisasi View-view
            title = itemView.findViewById(R.id.titledoes);
            desc = itemView.findViewById(R.id.descdoes);
            date = itemView.findViewById(R.id.datedoes);
            info = itemView.findViewById(R.id.infodoes);
            place = itemView.findViewById(R.id.placedoes);
            barang = itemView.findViewById(R.id.barangdoes);
            ListItem = itemView.findViewById(R.id.list_item);
            ic_dots = itemView.findViewById(R.id.ic_dots);

        }
    }
}
