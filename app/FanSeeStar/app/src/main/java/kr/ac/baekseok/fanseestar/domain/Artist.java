package kr.ac.baekseok.fanseestar.domain;

import java.io.Serializable;

public class Artist implements Serializable {
    private String agency;
    private String artistName;
    private String image;

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
