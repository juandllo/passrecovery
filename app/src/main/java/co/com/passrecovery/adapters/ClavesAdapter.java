package co.com.passrecovery.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;

import co.com.passrecovery.R;
import co.com.passrecovery.model.Claves;

public class ClavesAdapter extends RecyclerView.Adapter<ClavesAdapter.MyViewHolder> {
    private Context mContext;
    private List<Claves> clavesList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView nombreSistema;
        public TextView nombreUsuario;

        public MyViewHolder(View view) {
            super(view);
            nombreSistema = (TextView) view.findViewById(R.id.card_nombre_sistema);
            nombreUsuario = (TextView) view.findViewById(R.id.card_nombre_usuario);
        }
    }

    public ClavesAdapter(Context mContext, List<Claves> clavesList) {
        this.mContext = mContext;
        this.clavesList = clavesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_claves_main, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Claves clave = clavesList.get(position);
        holder.nombreSistema.setText(clave.getNombreServicio());
        holder.nombreUsuario.setText(clave.getNombreUsuario());
    }

    @Override
    public int getItemCount() {
        return clavesList.size();
    }

}
