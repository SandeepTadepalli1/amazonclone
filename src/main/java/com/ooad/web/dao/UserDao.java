package com.ooad.web.dao;

import com.ooad.web.model.*;
import com.ooad.web.utils.Constants;
import com.ooad.web.utils.Database;
import com.ooad.web.utils.TokenAuth;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.ws.rs.core.Response.Status;
import javax.xml.crypto.Data;
import java.sql.*;

public class UserDao {

    public JSONObject validateLogin(final String email, String password) {
        try {
            final JSONObject status = new JSONObject();
            final Connection con = Database.getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT  * FROM  Users WHERE emailId=?");
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                if (rs.getString("password").equals(password)) {
                    final User user = getUser(email);
                    status.put("status", Status.OK.getStatusCode());
                    status.put("user", user.toJSON());
                    status.put("token", TokenAuth.generateToken(user));
                    status.put("errors", "");
                } else {
                    status.put("status", Status.BAD_REQUEST.getStatusCode());
                    final JSONObject errors = new JSONObject();
                    errors.put("psword", Constants.ERROR_WRONG_PASSWD);
                    status.put("errors", errors);
                }
            } else {
                status.put("status", Status.BAD_REQUEST.getStatusCode());
                final JSONObject errors = new JSONObject();
                errors.put("email", Constants.ERROR_NO_USER);
                status.put("errors", errors);
            }
            con.close();
            return status;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JSONObject();
    }

    public User getUser(String email) {
        try {
            Connection con = Database.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM Users WHERE emailId=?");
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            User u = null;
            if (rs.next()) {
                u = new User(rs.getInt("id"),
                        rs.getString("userName"),
                        rs.getString("emailId"),
                        rs.getString("password"),
                        rs.getBoolean("isEnabled"),
                        rs.getInt("defaultAddressId"));
            }
            con.close();
            return u;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public User getUser(int userId) {
        try {
            Connection con = Database.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM Users WHERE id=?");
            ps.setString(1, String.valueOf(userId));
            ResultSet rs = ps.executeQuery();
            User u = null;
            if (rs.next()) {
                u=new User(rs.getInt("id"),
                        rs.getString("userName"),
                        rs.getString("emailId"),
                        rs.getString("password"),
                        rs.getBoolean("isEnabled"),
                        rs.getInt("defaultAddressId"));
            }
            con.close();
            return u;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public JSONObject validateRegister(final String userName, final String email, final String password) {
        JSONObject status = new JSONObject();
        try {
            Connection con = Database.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM  Users WHERE emailId = ?");
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                JSONArray errors = new JSONArray();
                errors.put(new JSONObject().put("email", Constants.ERROR_USER_EXIST));
                status.put("status", Status.BAD_REQUEST.getStatusCode());
                status.put("errors", errors);
            } else {
                final User user = createUser(userName, email, password);
                final JSONObject userJsonObject = user.toJSON();
                status.put("status", Status.CREATED.getStatusCode());
                status.put("user", userJsonObject);
                status.put("token", TokenAuth.generateToken(user));
            }
            con.close();
            return status;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public User createUser(final String userName, final String email, final String password) {
        try {
            Connection con = Database.getConnection();
            PreparedStatement ps = con
                    .prepareStatement("INSERT INTO Users(userName,emailId,password) VALUES (?,?,?)");//add user to database
            ps.setString(1, userName);
            ps.setString(2, email);
            ps.setString(3, password);
            ps.executeUpdate();
            con.close();
            return getUser(email);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void createDummyAccount(int userId, String userName, String accountNumber) {
        try {
            Connection con = Database.getConnection();
            PreparedStatement ps = con.prepareStatement("INSERT INTO Accounts(userId, name, number, amount) VALUES (?,?,?,?)");
            ps.setInt(1,userId);
            ps.setString(2,userName);
            ps.setInt(3, Integer.parseInt(accountNumber));
            ps.setInt(4,77777);
            ps.executeUpdate();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public boolean save(User user) {
        try {
            Connection con = Database.getConnection();
            PreparedStatement ps = con.prepareStatement("UPDATE User SET defaultAddrId = ? WHERE id = ?");
            ps.setInt(1,user.getDefaultAddressId() );
            ps.setInt(2,user.getId() );
            ps.executeUpdate();
            con.close();
            return true;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public UserAccount getUserAccountFromId(int userId){
        try {
            Connection con = Database.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM Accounts WHERE userId=?");
            ps.setInt(1,userId);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                UserAccount ua = new UserAccount(rs.getInt("id"),rs.getString("name"),rs.getInt("number"),rs.getInt("amount"));
                con.close();
                return ua;
            }
            con.close();
            return null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean save(UserAccount userAccount) {
        try {
            Connection con = Database.getConnection();
            PreparedStatement ps = con.prepareStatement("UPDATE Accounts SET amount = ? WHERE id = ?");
            ps.setInt(1,userAccount.getAmount() );
            ps.setInt(2,userAccount.getId());
            ps.executeUpdate();
            con.close();
            return true;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
