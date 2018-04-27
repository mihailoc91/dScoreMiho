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


public class Delegat extends AsyncTask<String, Void, Delegat> implements Serializable {

    private String email;
    private String name;
    private List<Integer> ids=new ArrayList<>();
    private List <String>leagues= new ArrayList<>();

    @Override
    protected Delegat doInBackground(String... strings) {
        Connection conn;
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection("jdbc:jtds:sqlserver://mssql11.orion.rs/miho","mihomadjija","miho011!!!");

            PreparedStatement preparedStatement = conn.prepareStatement("SELECT Refree.ID,Refree.Email, Refree.Name, League.Name FROM Refree JOIN League ON Refree.LeagueID=League.ID WHERE Refree.Email=? and Refree.Type='Delegat'");
            preparedStatement.setString(1,strings[0]);
            ResultSet resultSet = preparedStatement.executeQuery();
            Delegat delegat= new Delegat();

            while(resultSet.next()) {
                delegat.ids.add(resultSet.getInt(1));
                delegat.email=resultSet.getString(2);
                delegat.name=resultSet.getString(3);
                delegat.leagues.add(resultSet.getString(4));
            }

            return delegat;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return null;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }

    public List<String> getLeagues() {
        return leagues;
    }

    public void setLeagues(List<String> leagues) {
        this.leagues = leagues;
    }
}