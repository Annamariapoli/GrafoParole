package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import bean.Parola;

public class Dao {

	public List<Parola> getParoleDiQuestaLunghezza(int lunghezza){                 //funziona
		Connection conn = DBConnect.getConnection();
		String query = "select * from parola where length(nome) = ? ;";
		List<Parola> parolediquestalunghezza = new LinkedList<Parola>();
		try{
			PreparedStatement st = conn.prepareStatement(query);
			st.setInt(1, lunghezza);
			ResultSet res = st.executeQuery();
			while(res.next()){
				Parola p = new Parola(res.getInt("id"), res.getString("nome"));
				parolediquestalunghezza.add(p);
			}
			conn.close();
			return parolediquestalunghezza;
		}catch(SQLException e ){
			e.printStackTrace();
			return null;
		}
	}
}
