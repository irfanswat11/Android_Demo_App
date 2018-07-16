package com.irfanulhaq.restaurantreservation.mvp.models;


import java.util.ArrayList;
import java.util.List;

public class TableModel extends BaseMvpPModel {
    public int id;
    final public static String TABlES_KEY = "customerKey";
    public boolean isReserved;

    public TableModel() {}

    public TableModel(int id, boolean isReserved) {
        this.id = id;
        this.isReserved = isReserved;
    }
    public List<TableModel> getTablz(boolean[] tblz){
        List<TableModel> tableModelList = new ArrayList<>();
        for(int i = 0; i < tblz.length; i++){
            tableModelList.add(new TableModel(i,tblz[i]));
        }
        return tableModelList;
    }
}
