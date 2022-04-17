package presentation;

import connection.ConnectionFactory;

import javax.swing.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Table extends JFrame {

    JPanel mainPanel = new JPanel();

    Table(String tabelName)
    {
        Connection con = ConnectionFactory.getConnection();

        try{
            Connection connection = ConnectionFactory.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM" + " schooldb." + tabelName);
            ResultSetMetaData metadata = rs.getMetaData();

            int columnCount = metadata.getColumnCount();

            String data[][] = new String[40][columnCount];

            List< String > columnsNames = new ArrayList<>();
            String column[] = new String[columnCount];

            for(int i = 1; i <= columnCount; i++)
                column[i - 1] = metadata.getColumnName(i);

            int j = 0;
            while(rs.next()){

                for(int i = 1; i <= columnCount; i++)
                    data[j][i - 1] = rs.getString(i);

                j++;
            }

            JTable jt = new JTable(data,column);
            jt.setBounds(30,40,200,300);

            jt.setShowGrid(true);
            jt.setShowVerticalLines(true);

            mainPanel.add(jt);

            JScrollPane sp = new JScrollPane(jt);
            add(sp);
            setSize(500,500);
            setVisible(true);
            setTitle(tabelName.substring(0, 1).toUpperCase() + tabelName.substring(1, tabelName.length()) + " Table");
        }

        catch(Exception e){
            System.out.println(e.getMessage());
        }

    }


}
