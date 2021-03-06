package dao;

import entity.Config;
import util.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConfigDAO {
    public int getTotal(){
        int total = 0;
        try(Connection c = DBUtil.getConnection(); Statement s = c.createStatement()) {
            ResultSet rs = s.executeQuery("SELECT COUNT(*) FROM config");
            while(rs.next()){
                total = rs.getInt(1);
            }
            System.out.println("total:"+total);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }

    public void add(Config config) {
        String sql = "INSERT INTO config(id,key_,value) VALUES(null,?,?)";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1,config.getKey());
            ps.setString(2,config.getValue());
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()){
                int id =rs.getInt(1);
                config.id = id;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void update(Config config){
        String sql ="UPDATE config SET key_ =? , value = ? where id =?";
        try(Connection c = DBUtil.getConnection();PreparedStatement ps = c.prepareStatement(sql)){
            ps.setString(2,config.getValue());
            ps.setString(1,config.getKey());
            ps.setInt(3,config.getId());
            ps.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void delete(int id){
        String sql ="DELETE FROM config WHERE id =?";
        try(Connection c = DBUtil.getConnection();PreparedStatement ps = c.prepareStatement(sql)){
            ps.setInt(1,id);
            ps.execute();
        }catch (SQLException e){

        }
    }

    public Config get(int id){
        Config config = null;
        String sql ="SELECT * FROM config WHERE id = ?";
        try(Connection c = DBUtil.getConnection();PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                config.setKey(rs.getString("key_"));
                config.setValue(rs.getString("value"));
            }
            config.setId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return config;
    }

    public List<Config> list(){
        List<Config> configs;
        configs = list(0,Short.MAX_VALUE);
        return configs;
    }

    public List<Config> list(int start, int end){
        List<Config> configs = new ArrayList();
        String sql = "SELECT * FROM  config ORDER BY DESC LIMIT ?,?";
        try(Connection c = DBUtil.getConnection();PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1,start);
            ps.setInt(2,end);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Config config = new Config();
                config.setKey(rs.getString("key"));
                config.setValue(rs.getString("value"));
                config.setId(rs.getInt("id"));
                configs.add(config);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return configs;
    }

    public Config getByKey(String key) {
        Config config = null;
        String sql = "select * from config where key_ = ?" ;
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
        ) {

            ps.setString(1, key);
            ResultSet rs =ps.executeQuery();

            if (rs.next()) {
                config = new Config();
                int id = rs.getInt("id");
                String value = rs.getString("value");
                config.key = key;
                config.value = value;
                config.id = id;
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }
        return config;
    }

}
