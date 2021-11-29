package com.noanails.tiendaappadmin;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

//Esta clase relaciona la parte grafica con los datos (Fecha y Hora) que vamos ha tratar.
public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private List<ListElemnt>mData;//Esta lista tiene todos los datos de ListElement.
    private LayoutInflater mInflater;//Describe de que archivo proviene.
    public Context context;//Define de que clase estamos llamando este adaptador.

    //Constructor.
    public ListAdapter(List<ListElemnt> itemList, Context context){
        this.mInflater = LayoutInflater.from(context);
        this.context=context;
        this.mData = itemList;
    }
    public ListAdapter(){
    }

    //A continuación damos la referencia de como se va a ver cada "tarjeta"
    @NonNull
    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_element, null);
        return new ViewHolder(view);
    }

    //
    @Override
    public void onBindViewHolder(@NonNull ListAdapter.ViewHolder holder, int position) {
        holder.bindData(mData.get(position));
    }

    //Método que devuelve el tamaño de elementos que hay en la lista.
    @Override
    public int getItemCount() {
        return mData.size();
    }

    //Este metodo sirve para redefinir los elementos de la lista.
    public void setItems(List<ListElemnt> items){
        mData=items;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView usuario, nombre, ap1, ap2, nTelf, email;

        Button btMod, btElim;
        DatabaseReference dbr;

        ViewHolder(View itemView){
            super(itemView);
            dbr = FirebaseDatabase.getInstance().getReference();
            usuario = itemView.findViewById(R.id.textViewUsuarioCard);
            nombre = itemView.findViewById(R.id.textViewNombre);
            ap1 = itemView.findViewById(R.id.textViewApe1);
            ap2 = itemView.findViewById(R.id.textViewApe2);
            nTelf = itemView.findViewById(R.id.textViewEmail);
            email = itemView.findViewById(R.id.textViewTelf);

            btMod = itemView.findViewById(R.id.buttonModifi);
            btElim = itemView.findViewById(R.id.buttonDelet);
        }

        void bindData(final ListElemnt item){
            usuario.setText(item.getNombre());
            nombre.setText(item.getNombre());
            ap1.setText(item.getAp1());
            ap2.setText(item.getAp2());
            nTelf.setText(item.getnTelf());
            email.setText(item.getEmail());

            btMod.setContentDescription(item.getIdUsuario());
            btMod.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(),Usuarios.class);
                    intent.putExtra("boton",btMod.getContentDescription());
                    v.getContext().startActivity(intent);
                }
            });
        }
    }
}
