package com.cqrify.followme.model;

/**
 * Created by Jocke Ådén on 20/10/2017.
 */

public class Contact {

    private String id;
    private String lookupKey;
    private String name;
    private String number;
    private String thumbNailUri;


    public Contact(String id, String lookupKey, String name, String number, String thumbNailUri){
        this.id = id;
        this.lookupKey = lookupKey;
        this.name = name;
        this.number = number;
        this.thumbNailUri = thumbNailUri;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLookupKey() {
        return lookupKey;
    }

    public void setLookupKey(String lookupKey) {
        this.lookupKey = lookupKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getThumbNailUri() {
        return thumbNailUri;
    }

    public void setThumbNailUri(String thumbNailUri) {
        this.thumbNailUri = thumbNailUri;
    }

    @Override
    public String toString(){
        return  "Id=" + id +
                "\nlookupKey=" + lookupKey +
                "\nname="+name+
                "\nnumber="+number+
                "\nThumbnailUri="+thumbNailUri;
    }

}
