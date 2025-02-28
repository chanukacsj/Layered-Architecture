package com.example.layeredarchitecture.dao;

import com.example.layeredarchitecture.db.DBConnection;
import com.example.layeredarchitecture.model.ItemDTO;

import java.sql.*;
import java.util.ArrayList;

public class ItemDAOImpl implements ItemDAO{

  public ArrayList<ItemDTO> getAllItem() throws SQLException, ClassNotFoundException {
      Connection connection = DBConnection.getDbConnection().getConnection();
      Statement stm = connection.createStatement();
      ResultSet rst = stm.executeQuery("SELECT * FROM Item");

      ArrayList<ItemDTO> itemDTOS = new ArrayList<>();

      while (rst.next()){
          itemDTOS.add(new ItemDTO(
                  rst.getString("code"),
                  rst.getString("description"),
                  rst.getBigDecimal("unitPrice"),
                  rst.getInt("qtyOnHand")
          ));
      }
      return itemDTOS;
  }

  public void ItemSave(ItemDTO itemDTO) throws SQLException, ClassNotFoundException {
      Connection connection = DBConnection.getDbConnection().getConnection();
      PreparedStatement pstm = connection.prepareStatement("INSERT INTO Item (code, description, unitPrice, qtyOnHand) VALUES (?,?,?,?)");
      pstm.setString(1,itemDTO.getCode());
      pstm.setString(2,itemDTO.getDescription());
      pstm.setBigDecimal(3,itemDTO.getUnitPrice());
      pstm.setInt(4,itemDTO.getQtyOnHand());
      pstm.executeUpdate();
  }
  public boolean updateItem (ItemDTO itemDTO) throws SQLException, ClassNotFoundException {
      Connection connection = DBConnection.getDbConnection().getConnection();
      PreparedStatement pstm = connection.prepareStatement("UPDATE Item SET description=?, unitPrice=?, qtyOnHand=? WHERE code=?");
      pstm.setString(1,itemDTO.getDescription());
      pstm.setBigDecimal(2,itemDTO.getUnitPrice());
      pstm.setInt(3, itemDTO.getQtyOnHand());
      pstm.setString(4, itemDTO.getCode());
      return pstm.executeUpdate()> 0;


  }
  public boolean existItem(String code) throws SQLException, ClassNotFoundException {
      Connection connection = DBConnection.getDbConnection().getConnection();
      PreparedStatement pstm = connection.prepareStatement("SELECT code FROM Item WHERE code=?");
      pstm.setString(1, code);
      return pstm.executeQuery().next();
  }
  public void DeleteItem (String code) throws SQLException, ClassNotFoundException {
      Connection connection = DBConnection.getDbConnection().getConnection();
      PreparedStatement pstm = connection.prepareStatement("DELETE FROM Item WHERE code=?");
      pstm.setString(1, code);
      pstm.executeUpdate();
  }
  public ResultSet GenerateId() throws SQLException, ClassNotFoundException {
      Connection connection = DBConnection.getDbConnection().getConnection();
      ResultSet rst = connection.createStatement().executeQuery("SELECT code FROM Item ORDER BY code DESC LIMIT 1;");

      return rst;
  }
  public ItemDTO FindItem (String newItemCode) throws SQLException, ClassNotFoundException {
      Connection connection = DBConnection.getDbConnection().getConnection();
      PreparedStatement pstm = connection.prepareStatement("SELECT * FROM Item WHERE code=?");
      pstm.setString(1, newItemCode + "");
      ResultSet rst = pstm.executeQuery();
      rst.next();
      ItemDTO item = new ItemDTO(newItemCode + "", rst.getString("description"), rst.getBigDecimal("unitPrice"), rst.getInt("qtyOnHand"));

      return item;
  }



}
