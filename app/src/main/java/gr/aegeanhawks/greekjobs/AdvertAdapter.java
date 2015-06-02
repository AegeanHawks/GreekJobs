/*
 * Copyright (C) 2014 Francesco Azzola
 *  Surviving with Android (http://www.survivingwithandroid.com)
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package gr.aegeanhawks.greekjobs;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class AdvertAdapter extends RecyclerView.Adapter<AdvertAdapter.AdvertViewHolder> {

    private List<Ads> AdsList;

    public AdvertAdapter(ArrayList<Ads> AdsList) {
        this.AdsList = AdsList;
    }


    @Override
    public int getItemCount() {
        return AdsList.size();
    }

    @Override
    public void onBindViewHolder(AdvertViewHolder contactViewHolder, int i) {
        Ads ad = AdsList.get(i);

        contactViewHolder.vName.setText(ad.getTitle());
        contactViewHolder.vSurname.setText(ad.getCompany());
        contactViewHolder.vEmail.setText(ad.getArea());
        contactViewHolder.vTitle.setText(ad.getContact() + " " + ad.getSpecialty());
        contactViewHolder.advertId.setText(String.valueOf(ad.getID()));

        contactViewHolder.cView.setClickable(true);
        contactViewHolder.cView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent i = new Intent(v.getContext(), Advert.class);
                TextView txtadvertID = (TextView) v.findViewById(R.id.advertId);
                int advertId = Integer.parseInt(txtadvertID.getText().toString());
                i.putExtra("id", advertId);

                // Closing all the Activities
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                // Add new Flag to start new Activity
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                v.getContext().startActivity(i);
            }
        });
    }

    @Override
    public AdvertViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.card_layout, viewGroup, false);

        return new AdvertViewHolder(itemView);
    }

    public static class AdvertViewHolder extends RecyclerView.ViewHolder {

        protected TextView vName;
        protected TextView vSurname;
        protected TextView vEmail;
        protected TextView vTitle;
        protected TextView advertId;
        protected CardView cView;

        public AdvertViewHolder(View v) {
            super(v);

            cView = (CardView) v.findViewById(R.id.card_view);
            vName = (TextView) v.findViewById(R.id.txtName);
            vSurname = (TextView) v.findViewById(R.id.txtSurname);
            vEmail = (TextView) v.findViewById(R.id.txtEmail);
            vTitle = (TextView) v.findViewById(R.id.title);
            advertId = (TextView) v.findViewById(R.id.advertId);
        }
    }
}
