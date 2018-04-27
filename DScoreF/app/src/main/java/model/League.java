package model;

import android.os.AsyncTask;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class League extends AsyncTask<String, Void, Object> implements Serializable {

    /**
     * No-arguments constructor.
     */
    public League(){}



    public League(int id, String name){
        this.id=id;
        this.name=name;
    }


    private int id;
    private String name;
    private String accountID;
    private int isPublic;

    @Override
    protected Object doInBackground(String... strings) {
        Connection conn=null;
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection("jdbc:jtds:sqlserver://mssql11.orion.rs/miho", "mihomadjija", "miho011!!!");

            PreparedStatement preparedStatement = conn.prepareStatement("SELECT League.ID, League.Name FROM League JOIN Refree ON League.ID=Refree.LeagueID WHERE Email=? and Type='Delegat'");
            preparedStatement.setString(1,strings[0]);
            ResultSet resultSet = preparedStatement.executeQuery();
            List <League> list = new ArrayList<>();
            while(resultSet.next()){
                League league = new League(resultSet.getInt(1),resultSet.getString(2));
                list.add(league);
            }
            return list;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            try {
                if(!conn.isClosed()){
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccountID() {
        return accountID;
    }

    public void setAccountID(String accountID) {
        this.accountID = accountID;
    }

    /**
     *
     * @return int isPublic = 1 if it's public,
     * =0 if it's not.
     */
    public int getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(int isPublic) {
        this.isPublic = isPublic;
    }

    @Override
    public String toString(){
        return name;
    }
}
