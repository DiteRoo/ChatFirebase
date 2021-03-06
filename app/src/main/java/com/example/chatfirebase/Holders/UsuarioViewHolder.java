package com.example.chatfirebase.Holders;


import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.chatfirebase.R;

import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class UsuarioViewHolder extends RecyclerView.ViewHolder {

    private CircleImageView civFotoPerfil;
    private TextView txtNombreUsuario;
    private LinearLayout layoutPrincipal;

    public UsuarioViewHolder(View itemView) {
        super(itemView);
        civFotoPerfil = itemView.findViewById(R.id.civFotoPerfil);
        txtNombreUsuario =itemView.findViewById(R.id.txtNombreUsuario);
        layoutPrincipal = itemView.findViewById(R.id.layoutPrincipal);
    }

    public CircleImageView getCivFotoPerfil() {
        return civFotoPerfil;
    }

    public void setCivFotoPerfil(CircleImageView civFotoPerfil) {
        this.civFotoPerfil = civFotoPerfil;
    }

    public TextView getTxtNombreUsuario() {
        return txtNombreUsuario;
    }

    public void setTxtNombreUsuario(TextView txtNombreUsuario) {
        this.txtNombreUsuario = txtNombreUsuario;
    }

    public LinearLayout getLayoutPrincipal() {
        return layoutPrincipal;
    }

    public void setLayoutPrincipal(LinearLayout layoutPrincipal) {
        this.layoutPrincipal = layoutPrincipal;
    }
}
