package com.msadev.cmms.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class MasalahModel implements Parcelable {

    String idmasalah, jam, tanggal, masalah, shift, idmesin, idpengguna, nomesin, site;

    public MasalahModel(String idmasalah, String jam, String tanggal, String masalah, String shift, String idmesin, String idpengguna, String nomesin, String site) {
        this.idmasalah = idmasalah;
        this.jam = jam;
        this.tanggal = tanggal;
        this.masalah = masalah;
        this.shift = shift;
        this.idmesin = idmesin;
        this.idpengguna = idpengguna;
        this.nomesin = nomesin;
        this.site = site;
    }

    protected MasalahModel(Parcel in) {
        idmasalah = in.readString();
        jam = in.readString();
        tanggal = in.readString();
        masalah = in.readString();
        shift = in.readString();
        idmesin = in.readString();
        idpengguna = in.readString();
        nomesin = in.readString();
        site = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(idmasalah);
        dest.writeString(jam);
        dest.writeString(tanggal);
        dest.writeString(masalah);
        dest.writeString(shift);
        dest.writeString(idmesin);
        dest.writeString(idpengguna);
        dest.writeString(nomesin);
        dest.writeString(site);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MasalahModel> CREATOR = new Creator<MasalahModel>() {
        @Override
        public MasalahModel createFromParcel(Parcel in) {
            return new MasalahModel(in);
        }

        @Override
        public MasalahModel[] newArray(int size) {
            return new MasalahModel[size];
        }
    };

    public String getAll(){
        return nomesin+site;
    }

    public String getIdmasalah() {
        return idmasalah;
    }

    public String getJam() {
        return jam;
    }

    public String getTanggal() {
        return tanggal;
    }

    public String getMasalah() {
        return masalah;
    }

    public String getShift() {
        return shift;
    }

    public String getIdmesin() {
        return idmesin;
    }

    public String getIdpengguna() {
        return idpengguna;
    }

    public String getNomesin() {
        return nomesin;
    }

    public String getSite() {
        return site;
    }
}
