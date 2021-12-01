package clasesObjeto;

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
import com.noanails.tiendaappadmin.Citas;
import com.noanails.tiendaappadmin.Modificar_Citas;
import com.noanails.tiendaappadmin.R;

import java.util.List;

//Esta clase relaciona la parte grafica con los datos (Fecha y Hora) que vamos ha tratar.
public class ListAdapterCitas extends RecyclerView.Adapter<ListAdapterCitas.ViewHolder> {

    private List<ListElement_Citas>mData;//Esta lista tiene todos los datos de ListElement.
    private LayoutInflater mInflater;//Describe de que archivo proviene.
    public Context context;//Define de que clase estamos llamando este adaptador.

    //Constructor.
    public ListAdapterCitas(List<ListElement_Citas> itemList, Context context){
        this.mInflater = LayoutInflater.from(context);
        this.context=context;
        this.mData = itemList;
    }
    public ListAdapterCitas(){
    }

    //A continuación damos la referencia de como se va a ver cada "tarjeta"
    @NonNull
    @Override
    public ListAdapterCitas.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_element_citas, null);
        return new ViewHolder(view);
    }

    //
    @Override
    public void onBindViewHolder(@NonNull ListAdapterCitas.ViewHolder holder, int position) {
        holder.bindData(mData.get(position));
    }

    //Método que devuelve el tamaño de elementos que hay en la lista.
    @Override
    public int getItemCount() {
        return mData.size();
    }

    //Este metodo sirve para redefinir los elementos de la lista.
    public void setItems(List<ListElement_Citas> items){
        mData=items;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView mostrar, fecha, hora, servicio, nombre, email, telefono;
        Button btMod, btElim;
        DatabaseReference dbr;


        ViewHolder(View itemView){
            super(itemView);
            dbr = FirebaseDatabase.getInstance().getReference();
            mostrar = itemView.findViewById(R.id.textViewCitasCard2);
            fecha = itemView.findViewById(R.id.textViewFechaCard2);
            hora = itemView.findViewById(R.id.textViewHoraCard2);
            servicio = itemView.findViewById(R.id.textViewServicio2);
            nombre= itemView.findViewById(R.id.textViewNombre);
            email = itemView.findViewById(R.id.textViewEmail);
            telefono = itemView.findViewById(R.id.textViewTelf);
            btMod = itemView.findViewById(R.id.buttonModifi);
            btElim = itemView.findViewById(R.id.buttonDelet);
        }

        void bindData(final ListElement_Citas item){
            fecha.setText(item.getFecha());
            hora.setText(item.getHora());
            servicio.setText(item.getServicio());
            nombre.setText(item.getNombre());
            email.setText(item.getEmail());
            telefono.setText(item.getnTelf());

            btElim.setContentDescription(item.getIdCita());

            btElim.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dbr.child("Reservas").child(btElim.getContentDescription().toString()).removeValue();
                    Toast.makeText(v.getContext(), "La cita se ha eliminado correctamente.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(v.getContext(), Citas.class);
                    v.getContext().startActivity(intent);
                }
            });

            btMod.setContentDescription(item.getIdCita());
            btMod.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), Modificar_Citas.class);
                    intent.putExtra("boton",btMod.getContentDescription());
                    v.getContext().startActivity(intent);
                }
            });
        }
    }
}
