package com.pyrojoke;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class WorkWithDB {

    public static Integer takeNumberOfCurrentPerson(Connection connection){
        ArrayList<Integer> listOfUsers = new ArrayList<>();
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(
                    "select login from person where login like 'testUserT%'"
            );
            while (rs.next())
            {
                listOfUsers.add(Integer.parseInt(rs.getString(1).split("T")[1]));
            }
            rs.close();
            st.close();

        }
        catch (SQLException e){
            e.printStackTrace();
        }
        Collections.sort(listOfUsers);
        return listOfUsers.get(listOfUsers.size()-1);
    }

    public static Integer takeTelephoneNumberOfCurrentPerson(Connection connection){
        ArrayList<Integer> listOfPhones = new ArrayList<>();
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(
                    "select phone from person where phone like '+7666%'"
            );
            while (rs.next())
            {
                listOfPhones.add(Integer.parseInt(rs.getString(1).replace("+","").split("7666")[1]));
            }
            rs.close();
            st.close();

        }
        catch (SQLException e){
            e.printStackTrace();
        }
        Collections.sort(listOfPhones);
        return listOfPhones.get(listOfPhones.size()-1);
    }

    public static String takeGKNumber(Connection connection, String orderNumber){
        String result="";
        int countAttempt=0;
            try {
                while (countAttempt<100000) {
                    Statement st = connection.createStatement();
                    ResultSet rs = st.executeQuery(
                            "select erp_number from order_entity where order_number = " + orderNumber + ""
                    );

                    while (rs.next()) {
                        result = rs.getString("erp_number");
                    }
                    if (result!=null)
                        break;
                    countAttempt++;
                    rs.close();
                    st.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return result;
    }

    public static void deleteOfferAccept(Connection connection){
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(
                    "delete from counterparty_offer_acceptance where counterparty_id = 588"
            );
            rs.close();
            st.close();

        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static Map<String, String> getFinanceOptions(Connection connection){
        Map<String, String> resultMap = new HashMap<>();
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(
                    "select sum(counterparty_finance.prepayment) as prepayment,\n" +
                            "sum(counterparty_finance.total_debt) as total_debt, \n" +
                            "sum(counterparty_finance.delinquent_debt) as delinquent_debt,\n" +
                            "counterparty_credit_conditions.credit_sum as credit_sum,\n" +
                            "counterparty_credit_conditions.credit_depth  \n" +
                            "from counterparty_finance \n" +
                            "inner join counterparty_credit_conditions \n" +
                            "on counterparty_finance.counterparty_id=counterparty_credit_conditions.counterparty_id\n" +
                            "where counterparty_finance.counterparty_id=588\n" +
                            "group by counterparty_credit_conditions.credit_sum, counterparty_credit_conditions.credit_depth "
            );
            while (rs.next())
            {
                resultMap.put("prepayment", rs.getString("prepayment"));
                resultMap.put("total_debt", rs.getString("total_debt"));
                resultMap.put("delinquent_debt", rs.getString("delinquent_debt"));
                resultMap.put("credit_sum", rs.getString("credit_sum"));
                resultMap.put("credit_depth", rs.getString("credit_depth"));
            }
            rs.close();
            st.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return resultMap;
    }

    public static Set<String> getCounterpartyContractsName(Connection connection){
        Set<String> contractsSet = new HashSet<>();
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(
                    "select name from counterparty_contract where counterparty_id=588"
            );
            while (rs.next())
            {
                contractsSet.add(rs.getString("name").toLowerCase());
            }
            rs.close();
            st.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return contractsSet;
    }

    public static Set<String> getSubsidiariesCounterpartyName(Connection connection){
        Set<String> subsidiariesSet = new HashSet<>();
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(
                    "select name from counterparty where parent_id=588"
            );
            while (rs.next())
            {
                subsidiariesSet.add(rs.getString("name").toLowerCase());
            }
            rs.close();
            st.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return subsidiariesSet;
    }

    public static Map<String, String> getQuantityFromWarehouse(Connection connection, int Warehouse){
        Map<String, String> quantityOfProducts = new HashMap<>();
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(
                    "select p.external_id, w.quantity from warehouse_balance w\n" +
                            "left join product p on w.product_id=p.id\n" +
                            "where w.warehouse_id="+Warehouse+""
            );
            while (rs.next())
            {
                quantityOfProducts.put(rs.getString("external_id").toLowerCase(), rs.getString("quantity"));
            }
            rs.close();
            st.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return quantityOfProducts;
    }

}
