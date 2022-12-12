package com.dex.fd.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dex.fd.dao.jdbc.GenericDAOImpl;
import com.dex.fd.model.Dependente;
import com.dex.fd.service.exceprion.DBException;

public class DependenteDAO extends GenericDAOImpl<Dependente>{
	
	private static final String TABLE = "DEPENDENTE";
	private static final String[] FIELDS = {"IDFUNCIONARIO", "GRAU"};
	
	@Override
	public String getTableName() {
		return TABLE;
	}
	
	/**
	 * @param parteNome
	 * @return
	 */
	public List<Dependente> getByParteNome(String parteNome) throws DBException {
		List<Dependente> list = new ArrayList<>();
		String sql = "SELECT * FROM " + TABLE + " WHERE NOME LIKE ? AND DELETADA = false";
		
		try {
			PreparedStatement ps = super.getPreparedStatement(sql);
			ps.setString(1, "%"+parteNome+"%");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				list.add(this.popular(rs));
			}
		} catch (SQLException ex) {
			throw new DBException(ex);
		}
		
		return list;
	}
	
	@Override
	public Dependente popular(ResultSet rs) throws SQLException {
		Dependente dependente = new Dependente ();
		
		dependente.setId(rs.getInt("ID"));
		
		Integer idfunc = rs.getInt("FKIDFUNCIONARIO");		
		dependente.setFuncionario(new FuncionarioDAO().getById(idfunc));
		
		dependente.setGrau(rs.getString("GRAU"));
		
		return dependente;
	}


	@Override
	public PreparedStatement preparePersistStatement(Dependente entidade, Boolean isUpdate) throws SQLException {
		PreparedStatement ps = super.getPreparedStatement(
				super.getInsertClause(FIELDS.length));
		
		ps.setInt(1, entidade.getIdFuncionario());
		ps.setString(2, entidade.getGrau());
		
		return ps;
	}

}
