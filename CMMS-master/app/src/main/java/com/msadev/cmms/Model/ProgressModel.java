package com.msadev.cmms.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class ProgressModel implements Parcelable {
    String idprogress, perbaikan, engginer, tanggal, shift, idmasalah, idpengguna, jam, masalah, idmesin, nomesin, site;

    public ProgressModel(String idprogress, String perbaikan, String engginer, String tanggal, String shift, String idmasalah, String idpengguna, String jam, String masalah, String idmesin, String nomesin, String site) {
        this.idprogress = idprogress;
        this.perbaikan = perbaikan;
        this.engginer = engginer;
        this.tanggal = tanggal;
        this.shift = shift;
        this.idmasalah = idmasalah;
        this.idpengguna = idpengguna;
        this.jam = jam;
        this.masalah = masalah;
        this.idmesin = idmesin;
        this.nomesin = nomesin;
        this.site = site;
    }

    public String getAll(){
        return nomesin+site;
    }

    public String getIdprogress() {
        return idprogress;
    }

    public String getPerbaikan() {
        return perbaikan;
    }

    public String getEngginer() {
        return engginer;
    }

    public String getTanggal() {
        return tanggal;
    }

    public String getShift() {
        return shift;
    }

    public String getIdmasalah() {
        return idmasalah;
    }

    public String getIdpengguna() {
        return idpengguna;
    }

    public String getJam() {
        return jam;
    }

    public String getMasalah() {
        return masalah;
    }

    public String getIdmesin() {
        return idmesin;
    }

    public String getNomesin() {
        return nomesin;
    }

    public String getSite() {
        return site;
    }

    protected ProgressModel(Parcel in) {
        idprogress = in.readString();
        perbaikan = in.readString();
        engginer = in.readString();
        tanggal = in.readString();
        shift = in.readString();
        idmasalah = in.readString();
        idpengguna = in.readString();
        jam = in.readString();
        masalah = in.readString();
        idmesin = in.readString();
        nomesin = in.readString();
        site = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(idprogress);
        dest.writeString(perbaikan);
        dest.writeString(engginer);
        dest.writeString(tanggal);
        dest.writeString(shift);
        dest.writeString(idmasalah);
        dest.writeString(idpengguna);
        dest.writeString(jam);
        dest.writeString(masalah);
        dest.writeString(idmesin);
        dest.writeString(nomesin);
        dest.writeString(site);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ProgressModel> CREATOR = new Creator<ProgressModel>() {
        @Override
        public ProgressModel createFromParcel(Parcel in) {
            return new ProgressModel(in);
        }

        @Override
        public ProgressModel[] newArray(int size) {
            return new ProgressModel[size];
        }
    };
}
